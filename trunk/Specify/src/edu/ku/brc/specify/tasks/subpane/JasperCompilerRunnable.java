/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane;

import java.io.File;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.AppResourceIFace;

/**
 * @author timo
 *
 */
public class JasperCompilerRunnable implements Runnable
{
    protected static final Logger    log = Logger.getLogger(JasperCompilerRunnable.class);
    
    protected Thread                    	thread;
    protected final JasperCompileListener  listener;
    protected final File                   cachePath;
    protected final String     				mainReportName;
    protected Vector<ReportCompileInfo> 	files = null;
    protected boolean compileRequired     	= true;

    /**
     * Constructs a an object to execute an SQL staement and then notify the listener
     * @param listener the listener
     * @param reportFile the file that contains the report
     * @param compiledFile the file that will contain the compiled report
     */
    public JasperCompilerRunnable(final JasperCompileListener listener, 
                                  final Vector<ReportCompileInfo> files)
    {
        this.listener     = listener;
        this.files   = files;
        this.cachePath = JasperReportsCache.checkAndCreateReportsCache();
        this.mainReportName = null;        //this.paramsArg = null;
    }

    public JasperCompilerRunnable(final JasperCompileListener listener, final String mainReportName)
    {
    	this.listener = listener;
    	this.mainReportName = mainReportName;
        this.cachePath = JasperReportsCache.checkAndCreateReportsCache();
        this.files = null;
    }
    /**
     * Starts the thread to make the SQL call
     *
     */
    public void start()
    {
        thread = new Thread(this);
        thread.start();
    }

    public void get()
    {
    	start();
    	try
    	{
    		thread.join();
    	}
    	catch (InterruptedException ex)
    	{
        edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
        edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(JasperCompilerRunnable.class, ex);
    		throw new RuntimeException(ex);
    	}
    }
    
    public boolean findFiles()
    {
        JasperReportsCache.refreshCacheFromDatabase();
        
        Vector<File>   reportFiles = new Vector<File>();
        AppResourceIFace appRes    = AppContextMgr.getInstance().getResource(mainReportName); 
        if (appRes != null)
        {
            String subReportsStr = appRes.getMetaData("subreports");
            
            
            if (StringUtils.isNotEmpty(subReportsStr))
            {
                String[] subReportNames = subReportsStr.split(",");
                for (String subReportName : subReportNames)
                {
                    AppResourceIFace subReportAppRes = AppContextMgr.getInstance().getResource(subReportName); 
                    if (subReportAppRes != null)
                    {
                        File subReportPath = new File(cachePath.getAbsoluteFile() + File.separator + subReportName);
                        if (subReportPath.exists())
                        {
                            reportFiles.add(subReportPath);
                            
                        } else
                        {
                            throw new RuntimeException("Subreport doesn't exist on disk ["+subReportPath.getAbsolutePath()+"]");
                        }
                        
                    } else
                    {
                        throw new RuntimeException("Couldn't load subreport [" + subReportName + "]");
                    }
                }
            }
            
            File reportPath = new File(cachePath.getAbsoluteFile() + File.separator + mainReportName);
            if (reportPath.exists())
            {
                reportFiles.add(reportPath);
                
            } else
            {
                throw new RuntimeException("Subreport doesn't exist on disk ["+reportPath.getAbsolutePath()+"]");
            }
            
        } else
        {
            throw new RuntimeException("Couldn't load report/label ["+mainReportName+"]");
        }


        boolean allAreCompiled = true;
        files = new Vector<ReportCompileInfo>();
        for (File file : reportFiles)
        {
            ReportCompileInfo info = JasperReportsCache.checkReport(file);
            files.add(info);
            if (!info.isCompiled())
            {
                allAreCompiled = false;
            }
        }
    	compileRequired = !allAreCompiled;
        return !allAreCompiled;
    }
    /**
     * Stops the thread making the call
     *
     */
    public synchronized void stop()
    {
        if (thread != null)
        {
            thread.interrupt();
        }
        thread = null;
        notifyAll();
    }

    /**
     * Creates a connection, makes the call and returns the results
     */
    public void run()
    {
        try
        {
            if (files == null)
            {
            	findFiles();
            }
        	for (ReportCompileInfo info : files)
            {
                if (!info.isCompiled())
                {
                    JasperCompileManager.compileReportToFile(info.getReportFile().getAbsolutePath(), info.getCompiledFile().getAbsolutePath());
                }
            }
    		compileRequired = false;
        	if (listener != null)
        	{
        		listener.compileComplete(files.get(files.size()-1).getCompiledFile());
        	}
        } catch (Exception ex)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(JasperCompilerRunnable.class, ex);
            log.error(ex);
            ex.printStackTrace();
            if (listener != null)
            {
                listener.compileComplete(null);
            }
        }
    }

	/**
	 * @return the files that require compilation.
	 */
	public Vector<ReportCompileInfo> getFiles() 
	{
		return files;
	}
    
	
    /**
	 * @return the compileRequired
	 */
	public boolean isCompileRequired() {
		return compileRequired;
	}

	public File getCompiledFile()
    {
    	if (files != null && files.size() > 0)
    	{
    		return files.get(files.size()-1).getCompiledFile();
    	}
    	return null;
    }
}
