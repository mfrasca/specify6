/**
 * 
 */
package edu.ku.brc.specify.utilapps.morphbank;

import static edu.ku.brc.ui.UIRegistry.clearSimpleGlassPaneMsg;
import static edu.ku.brc.ui.UIRegistry.displayConfirmLocalized;
import static edu.ku.brc.ui.UIRegistry.getAppDataDir;
import static edu.ku.brc.ui.UIRegistry.getAppName;
import static edu.ku.brc.ui.UIRegistry.getDefaultEmbeddedDBPath;
import static edu.ku.brc.ui.UIRegistry.getMostRecentWindow;
import static edu.ku.brc.ui.UIRegistry.getResourceString;
import static edu.ku.brc.ui.UIRegistry.getTopWindow;
import static edu.ku.brc.ui.UIRegistry.setAppName;
import static edu.ku.brc.ui.UIRegistry.setBaseAppDataDir;
import static edu.ku.brc.ui.UIRegistry.setDefaultWorkingPath;
import static edu.ku.brc.ui.UIRegistry.setEmbedded;
import static edu.ku.brc.ui.UIRegistry.setEmbeddedDBPath;
import static edu.ku.brc.ui.UIRegistry.setMobile;
import static edu.ku.brc.ui.UIRegistry.setRelease;
import static edu.ku.brc.ui.UIRegistry.setResourceLocale;
import static edu.ku.brc.ui.UIRegistry.writeSimpleGlassPaneMsg;

import java.awt.FileDialog;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;

import edu.ku.brc.af.auth.SecurityMgr;
import edu.ku.brc.af.auth.UserAndMasterPasswordMgr;
import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.GenericGUIDGeneratorFactory;
import edu.ku.brc.af.core.SchemaI18NService;
import edu.ku.brc.af.core.db.DBFieldInfo;
import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.db.DBTableInfo;
import edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.ui.db.DatabaseLoginPanel;
import edu.ku.brc.af.ui.forms.BusinessRulesIFace;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterIFace;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterMgr;
import edu.ku.brc.af.ui.weblink.WebLinkMgr;
import edu.ku.brc.dbsupport.CustomQueryFactory;
import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.dbsupport.DBMSUserMgr;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.dbsupport.SchemaUpdateService;
import edu.ku.brc.helpers.ImageMetaDataHelper;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.specify.Specify;
import edu.ku.brc.specify.conversion.TableWriter;
import edu.ku.brc.specify.datamodel.Accession;
import edu.ku.brc.specify.datamodel.AccessionAttachment;
import edu.ku.brc.specify.datamodel.Attachment;
import edu.ku.brc.specify.datamodel.AttachmentOwnerIFace;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.CollectingEventAttachment;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.CollectionObjectAttachment;
import edu.ku.brc.specify.datamodel.DataModelObjBase;
import edu.ku.brc.specify.datamodel.Locality;
import edu.ku.brc.specify.datamodel.LocalityAttachment;
import edu.ku.brc.specify.datamodel.ObjectAttachmentIFace;
import edu.ku.brc.specify.datamodel.Taxon;
import edu.ku.brc.specify.datamodel.TaxonAttachment;
import edu.ku.brc.specify.tools.export.ExportPanel;
import edu.ku.brc.specify.tools.ireportspecify.MainFrameSpecify;
import edu.ku.brc.ui.ChooseFromListDlg;
import edu.ku.brc.ui.CustomDialog;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.dnd.SimpleGlassPane;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.Pair;

/**
 * @author timo
 *
 * Attaches files in a directory to specimens (or other objects) in a Specify database
 */
public class BatchAttachFiles
{
    protected static final String PROGRESS = "PROGRESS";
    
    protected static final Logger log = Logger.getLogger(BatchAttachFiles.class);
    protected static final int CN_ERROR_NONE      = 0;
    protected static final int CN_ERROR_INVALID   = 0;
    protected static final int CN_ERROR_NOT_IN_DB = 0;
    
	protected static final String[] exts = {"TIF", "JPG", "PNG", "jpg", "png", "tif", "TIFF", "tiff"};
	
	protected Class<?>                   attachOwnerClass;
	protected Class<?>                   attachJoinClass;
	protected FileNameParserIFace        fnParser;
	protected File                       directory;
	protected List<File>                 files;
	protected String                     keyName;
	protected List<Pair<String, String>> errors     = new Vector<Pair<String, String>>();
	protected DataProviderSessionIFace   session;
	protected String                     errLogName = "errors";
	
    protected ArrayList<String> errFiles = new ArrayList<String>();
    
    protected HashMap<String, ArrayList<String>> mapFileNameToCatNum = null;
    
    // Data Members for converting FileNames to Cat Nums
    protected Pattern           regExNumericCatNumPattern = Pattern.compile("(?<=[0-9])-(?=[0-9a-zA-Z])|(?<=\\d)(?=\\p{L})|(?<=\\p{L})(?=\\d)");
    protected ArrayList<String> fileNamesInErrList        = new ArrayList<String>();
    protected PreparedStatement pStmtRE                   = null;
    protected String            catNumLookupSQL           = "SELECT COUNT(*) FROM ccollecitonobject WHERE CollectionID=?, CatalogNumber=?";
	protected int               collectionId              = -1;
	protected int               catNumErrStatus           = CN_ERROR_NONE;
	
	/**
	 * @param tblClass
	 * @param fnParser
	 * @param directory
	 * @throws Exception
	 */
	public BatchAttachFiles(final Class<?> tblClass, 
	                        final FileNameParserIFace fnParser, 
	                        final File directory) throws Exception
	{
		super();
		this.attachOwnerClass = tblClass;
		this.fnParser = fnParser;
		this.directory = directory;
		attachJoinClass = determineAttachmentClass();
		
		if (directory.isDirectory())
		{
			files = bldFilesFromDir(directory, exts);
		} else
		{
			bldFilesFromList();
		}
	}
	
    /**
     * @param tblClass
     * @param keyName
     * @param directory
     * @throws Exception
     */
    public BatchAttachFiles(final Class<?> tblClass,
                            final String keyName,
                            final File directory) throws Exception
    {
        super();
        this.attachOwnerClass   = tblClass;
        this.keyName    = keyName;
        this.fnParser   = null;
        this.directory  = directory;
        attachJoinClass = determineAttachmentClass();
        
        if (directory != null && directory.isDirectory())
        {
            files = bldFilesFromDir(directory, exts);
        }
    }
    
    /**
     * @param tblClass
     * @param keyName
     * @throws Exception
     */
    public BatchAttachFiles(final Class<?> tblClass,
                            final String keyName) throws Exception
    {
        super();
        this.attachOwnerClass   = tblClass;
        this.keyName    = keyName;
        this.fnParser   = null;
        this.directory  = null;
        this.attachJoinClass = determineAttachmentClass();
    }
    
    /**
     * 
     */
    public BatchAttachFiles()
    {
        super();
        
        this.attachOwnerClass        = null;
        this.keyName         = null;
        this.fnParser        = null;
        this.directory       = null;
        this.attachJoinClass = null;
        this.files           = null;
    }

    /**
     * @param indexFile
     * @return
     */
    public boolean attachFileFromIndexFile(final File indexFile)
    {
        if (files != null) files = null;
        
        mapFileNameToCatNum = new HashMap<String, ArrayList<String>>();
        try
        {
            ArrayList<File>   dirFiles = new ArrayList<File>();
            List<?> records = (List<?>)FileUtils.readLines(indexFile);
            for (Object lineObj : records)
            {
                String[] cols = StringUtils.split(lineObj.toString(), '\t');
                if (cols.length == 2)
                {
                    String fileName = cols[1];
                    fileName = StringUtils.replaceChars(fileName, ' ', '_');
                    fileName = StringUtils.replaceChars(fileName, ",", "");
                    
                    File upFile = new File(directory.getAbsolutePath() + File.separator + fileName);
                    if (upFile.exists())
                    {
                        ArrayList<String> catNumList = mapFileNameToCatNum.get(fileName);
                        if (catNumList == null)
                        {
                            catNumList = new ArrayList<String>();
                            mapFileNameToCatNum.put(fileName, catNumList);    
                        }
                        catNumList.add(cols[0]);
                        //System.out.println(String.format("%s %s", cols[0], fileName));
                        dirFiles.add(upFile);
                    } else
                    {
                        errFiles.add(fileName);
                    }
                }
            }
            files = dirFiles;
            attachFilesByFieldName();
            
            return true;
            
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
    /**
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean getDestinationClassAndField()
    {
        final List<FileNameParserIFace>             items = FileNameParserFactory.getList();
        String title = getResourceString("BatchAttachFiles.CHOOSE_DEST");
        String msg   = getResourceString("BatchAttachFiles.CHOOSE_DESTMSG");
        ChooseFromListDlg<FileNameParserIFace> dlg = new ChooseFromListDlg<FileNameParserIFace>((Frame)getMostRecentWindow(), title, msg, ChooseFromListDlg.OKCANCELHELP, items);
        dlg.setHelpContext("");
        
        UIHelper.centerAndShow(dlg);
        if (dlg.isNotCancelled())
        {
            this.fnParser         = (FileNameParserIFace)dlg.getSelectedObject();
            this.attachOwnerClass = fnParser.getAttachmentOwnerClass();
            this.attachJoinClass  = fnParser.getAttachmentJoinClass();
            this.keyName          = fnParser.getFieldName();
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     */
    public void attachFileFromIndexFile()
    {
        if (getDestinationClassAndField())
        {
            JFileChooser chooser = new JFileChooser("BatchAttachFiles.CH_FILE_MSG");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
            File indexFile      = null;
            int    returnVal = chooser.showOpenDialog(getTopWindow());
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                indexFile = chooser.getSelectedFile();
                try
                {
                    String           path      = FilenameUtils.getFullPath(indexFile.getAbsolutePath());
                    BatchAttachFiles batchFile = new BatchAttachFiles(this.attachOwnerClass, this.keyName, new File(path));
                    batchFile.attachFileFromIndexFile(indexFile);
                    
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Ask for what table and field are the destination of the attachments, and then
     * asks for the directory or files.
     */
    public void uploadImagesByFileName()
    {
        directory = null;
        files     = null;
        
        if (getDestinationClassAndField())
        {
            int rv = askForDirOrFiles();
            if (rv != JOptionPane.CANCEL_OPTION)
            {
                if (rv == JOptionPane.YES_OPTION)
                {
                    JFileChooser chooser = new JFileChooser("BatchAttachFiles.CH_FILE_MSG");
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
                    File indexFile      = null;
                    int    returnVal = chooser.showOpenDialog(getTopWindow());
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        indexFile = chooser.getSelectedFile();
                        try
                        {
                            String path = FilenameUtils.getFullPath(indexFile.getAbsolutePath());
                            BatchAttachFiles batchFile = new BatchAttachFiles(this.attachOwnerClass, this.keyName, new File(path));
                            batchFile.attachFilesByFieldName();
                            
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                } else
                {
                    String title = "title";
                    FileDialog dialog = new FileDialog((Frame)null, title, FileDialog.LOAD);
                    //dialog.setMultipleMode(true);
                    
                    // FILE FILTER!!!!!!!!!
                    UIHelper.centerAndShow(dialog);
                    File[] selectedFiles = null;//dialog.getFiles();
                    if (selectedFiles == null || selectedFiles.length == 0)
                    {
                        return;
                    }
                    files = new ArrayList<File>();
                    Collections.addAll(files, selectedFiles);
                    attachFilesByFieldName(files);
                    return;
                }
            }
        }
        
//        if (directory != null && directory.isDirectory())
//        {
//            files = bldFilesFromDir(directory, exts);
//        }
    }

    /**
     * 
     */
    private static int askForDirOrFiles()
    {
        return displayConfirmLocalized("Choose", "BatchAttachFiles.ASK_DIRORFILES_MSG", 
                "BatchAttachFiles.DO_DIRS", "BatchAttachFiles.DO_FILES", "Cancel", JOptionPane.QUESTION_MESSAGE);
    }

    
    /**
     * @return
     */
    public void attachFilesByFieldName(final List<File> fileList)
    {
        if (fileList == null || fnParser == null)
        {
            return;
        }
        files = fileList;
        
        SwingWorker<Integer, Integer> backupWorker = new SwingWorker<Integer, Integer>()
        {
            @Override
            protected Integer doInBackground() throws Exception
            {
                try
                {
                    double percentThreshold = 10.0;
                    int    totNumFiles = files.size();
                    int    cnt         = 0;
                    int    incr        = (int)((double)totNumFiles / percentThreshold);
                    
                    String path = getAppDataDir() + File.separator + "fileupload.html";
                    TableWriter tw = new TableWriter(path, "File upload issues");
                    tw.startTable();
                    tw.logHdr("File", "Reason");
                    
                    for (String fileName : errFiles)
                    {
                        tw.logErrors(fileName, " This file name was referenced in the mapping file, did not exist.");
                    }
                    
                    String fieldTitle = "";//fi.getTitle();
                    for (File file : files)
                    {
                        String fieldValue = FilenameUtils.getBaseName(file.getName()); 
                        if (fnParser.isNameValid(fieldValue))
                        {
                            Integer id = fnParser.getRecordId(fieldValue);
                            if (id != null)
                            {
                                //System.out.println(String.format("%s -> id: %d", value.toString(), id));
                                if (!attachFileTo(file, id))
                                {
                                    tw.logErrors(file.getName(), String.format("There was error saving the Attachment %s["+fieldValue.toString()+"] File["+file.getName()+"]", fieldTitle));
                                }
                            } else
                            {
                                tw.logErrors(file.getName(), String.format("The %s ["+fieldValue.toString()+"] was not in the database. File["+file.getName()+"]", fieldTitle));
                            }
                        } else
                        {
                            tw.logErrors(file.getName(), String.format("The %s ["+fieldValue.toString()+"] was not in the database. File["+file.getName()+"]", fieldTitle));
                        }
                        
                        cnt++;
                        if (totNumFiles < 21)
                        {
                            firePropertyChange(PROGRESS, totNumFiles, cnt*5); 
                        } else if (cnt % incr == 0)
                        {
                             int percent = (int)(((double)cnt / totNumFiles) * 100.0);
                             firePropertyChange(PROGRESS, 100, percent);
                        }
                    }
                    tw.close();
                    
                    if (tw.hasLines())
                    {
                        File twFile = new File(path);
                        AttachmentUtils.openFile(twFile);
                    }
                    
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                    
                }
                
                return null;
            }

            @Override
            protected void done()
            {
                super.done();
                
                if (BatchAttachFiles.this.fnParser instanceof BaseFileNameParser)
                {
                    ((BaseFileNameParser)BatchAttachFiles.this.fnParser).cleanup();
                }
                clearSimpleGlassPaneMsg();
            }
        };
        
        final SimpleGlassPane glassPane = writeSimpleGlassPaneMsg(getResourceString("BatchAttachFiles.UPLOADING"), 24);
        glassPane.setProgress(0);
        
        backupWorker.addPropertyChangeListener(
                new PropertyChangeListener() {
                    public  void propertyChange(final PropertyChangeEvent evt) {
                        if (PROGRESS.equals(evt.getPropertyName())) 
                        {
                            //System.out.println("Progress: "+evt.getNewValue());
                            glassPane.setProgress((Integer)evt.getNewValue());
                        }
                    }
                });
        backupWorker.execute();

    }

    
    /**
     * @return
     */
    public boolean attachFilesByFieldName()
    {
        if (files == null)
        {
            return false;
        }
        
        SwingWorker<Integer, Integer> backupWorker = new SwingWorker<Integer, Integer>()
        {
            @Override
            protected Integer doInBackground() throws Exception
            {
                /*for (File file : files)
                {
                    String catNum = mapFileNameToCatNum.get(file.getName());
                    if (catNum != null) System.out.println("catNum["+catNum+"]  ["+file.getName()+"]");
                }*/
                
                DBTableInfo tblInfo = DBTableIdMgr.getInstance().getByShortClassName(attachOwnerClass.getSimpleName());
                if (tblInfo != null)
                {
                    DBFieldInfo           fi    = tblInfo.getFieldByColumnName(keyName);
                    UIFieldFormatterIFace fmt   = fi.getFormatter();
                    PreparedStatement     pStmt = null;
                    try
                    {
                        double percentThreshold = 10.0;
                        int    totNumFiles = files.size();
                        int    cnt         = 0;
                        int    incr        = (int)((double)totNumFiles / percentThreshold);
                        
                        String specCols = QueryAdjusterForDomain.getInstance().getSpecialColumns(tblInfo, false);
                        String sql      = String.format("SELECT %s FROM %s WHERE %s AND %s = ?", tblInfo.getPrimaryKeyName(), tblInfo.getName(), specCols, keyName);
                        //System.out.println(sql);
                        
                        String path = getAppDataDir() + File.separator + "fileupload.html";
                        TableWriter tw = new TableWriter(path, "File upload issues");
                        tw.startTable();
                        tw.logHdr("File", "Reason");
                        
                        for (String fileName : errFiles)
                        {
                            tw.logErrors(fileName, "This file name was referenced in the mapping file, did not exist.");
                        }
                        
                        ArrayList<String> catNumList = new ArrayList<String>();
                        
                        pStmt = DBConnection.getInstance().getConnection().prepareStatement(sql);
                        for (File file : files)
                        {
                            if (mapFileNameToCatNum != null)
                            {
                                //System.out.println("file.getName()["+file.getName()+"]");
                                catNumList = mapFileNameToCatNum.get(file.getName());
                                if (catNumList == null || catNumList.size() == 0)
                                {
                                    tw.logErrors(file.getName(), String.format("There was no %s mapped for this file.", fi.getTitle()));
                                    continue;
                                }
                            } else
                            {
                                String catNum = FilenameUtils.getBaseName(file.getName()); 
                                catNumList.clear();
                                catNumList.add(catNum);
                            }
                            
                            for (String catNum : catNumList)
                            {
                                Object value = fmt != null ? fmt.formatFromUI(catNum) : catNum;  
                                if (value instanceof String)
                                {
                                    pStmt.setString(1, value.toString());
                                    ResultSet rs = pStmt.executeQuery();
                                    if (rs.next())
                                    {
                                        int id = rs.getInt(1);
                                        if (!rs.wasNull())
                                        {
                                            //System.out.println(String.format("%s -> id: %d", value.toString(), id));
                                            if (!attachFileTo(file, id))
                                            {
                                                tw.logErrors(file.getName(), String.format("There was error saving the Attachment %s["+value.toString()+"] File["+file.getName()+"]", fi.getTitle()));
                                            }
                                        } else
                                        {
                                            tw.logErrors(file.getName(), String.format("The %s ["+value.toString()+"] was not in the database. File["+file.getName()+"]", fi.getTitle()));
                                        }
                                    }
                                    rs.close();
                                } else if (value == null)
                                {
                                    String msg = mapFileNameToCatNum != null ? String.format("There was no %s mapped for this file.", fi.getTitle()) :
                                                                               String.format("The file could not be converted to a valid %s.", fi.getTitle());
                                    tw.logErrors(file.getName(), msg);
                                }
                            }
                            
                            cnt++;
                            if (totNumFiles < 21)
                            {
                                firePropertyChange(PROGRESS, totNumFiles, cnt*5); 
                            } else if (cnt % incr == 0)
                            {
                                 int percent = (int)(((double)cnt / totNumFiles) * 100.0);
                                 firePropertyChange(PROGRESS, 100, percent);
                            }
                            //Thread.currentThread().sleep(2000);
                            //if (cnt == 2) break;
                        }
                        tw.close();
                        
                        if (tw.hasLines())
                        {
                            File twFile = new File(path);
                            AttachmentUtils.openFile(twFile);
                        }
                        
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                        
                    } finally
                    {
                        if (pStmt != null)
                        {
                            try
                            {
                                pStmt.close();
                            } catch (SQLException ex) {}
                        }
                    }
                }
                
                return null;
            }

            @Override
            protected void done()
            {
                super.done();
                
                clearSimpleGlassPaneMsg();
            }
        };
        
        final SimpleGlassPane glassPane = writeSimpleGlassPaneMsg(getResourceString("BatchAttachFiles.UPLOADING"), 24);
        glassPane.setProgress(0);
        
        backupWorker.addPropertyChangeListener(
                new PropertyChangeListener() {
                    public  void propertyChange(final PropertyChangeEvent evt) {
                        if (PROGRESS.equals(evt.getPropertyName())) 
                        {
                            //System.out.println("Progress: "+evt.getNewValue());
                            glassPane.setProgress((Integer)evt.getNewValue());
                        }
                    }
                });
        backupWorker.execute();
        
        return true;
    }
    
    /**
     * 
     */
//    public void attachFilesByFieldNameOld()
//    {
//        DBTableInfo tblInfo = DBTableIdMgr.getInstance().getByShortClassName(tblClass.getSimpleName());
//        if (tblInfo != null)
//        {
//            UIFieldFormatterIFace fmt = DBTableIdMgr.getFieldFormatterFor(tblClass, keyName);
//            PreparedStatement pStmt = null;
//            try
//            {
//                String sql = String.format("SELECT %s FROM %s WHERE %s = ?", tblInfo.getIdFieldName(), tblInfo.getName(), keyName);
//                pStmt = DBConnection.getInstance().getConnection().prepareStatement(sql);
//                for (File file : files)
//                {
//                    Object value = FilenameUtils.getBaseName(file.getName());
//                    if (fmt != null)
//                    {
//                        value = fmt.formatFromUI(value);
//                    }
//                    if (value instanceof String)
//                    {
//                        pStmt.setString(1, value.toString());
//                        ResultSet rs = pStmt.executeQuery();
//                        if (rs.next())
//                        {
//                            int id = rs.getInt(1);
//                            attachFileTo(file, id);
//                        }
//                        rs.close();
//                    }
//                }
//            } catch (Exception ex)
//            {
//                ex.printStackTrace();
//                
//            } finally
//            {
//                if (pStmt != null)
//                {
//                    try
//                    {
//                        pStmt.close();
//                    } catch (SQLException ex) {}
//                }
//            }
//        }
//    }
	
	/**
	 * @return attachment class for the table class
	 * @throws Exception
	 */
	protected Class<?> determineAttachmentClass() throws Exception
	{
	    if (attachOwnerClass.equals(CollectionObject.class))
        {
            return CollectionObjectAttachment.class;
        }
	    
	    if (attachOwnerClass.equals(CollectingEvent.class))
        {
            return CollectingEventAttachment.class;
        }
        throw new Exception(String.format(getResourceString("BatchAttachFiles.ClassNotSupported"), attachOwnerClass.getName()));
	}
	
	/**
	 * @return the tblClass
	 */
	public Class<?> getTblClass()
	{
		return attachOwnerClass;
	}
	/**
	 * @return the fnParser
	 */
	public FileNameParserIFace getFnParser()
	{
		return fnParser;
	}
	/**
	 * @return the directoryName
	 */
	public File getDirectory()
	{
		return directory;
	}
	
	/**
	 * @param collId
	 * @param catNum
	 * @return
	 * @throws SQLException
	 */
	private boolean isCatNumInDB(final String catNum) throws SQLException
	{
	    boolean isInDB = false;
	    
	    pStmtRE.setInt(1, collectionId);
        pStmtRE.setString(2, catNum);
        ResultSet rs = pStmtRE.executeQuery();
        if (rs.next())
        {
            isInDB = rs.getInt(1) == 1;
        }
        rs.close();
        return isInDB;
	}
	
	/**
	 * @param file
	 * @param formatter
	 * @return
	 * @throws SQLException
	 */
	public String isCatalogNumberFileNameOK(final File file, final UIFieldFormatterIFace formatter) throws SQLException
	{
	    catNumErrStatus = CN_ERROR_NONE;
	    
        String  baseName  = FilenameUtils.getBaseName(file.getName());
	    int     fmtLen    = formatter.getLength();
	    boolean isNumeric = formatter.isNumeric();
	    
	    if (isNumeric)
	    {
	        String catNum = null;
	        if (!StringUtils.isNumeric(baseName)) // has trailing letters etc.
	        {
	            String[] tokens = regExNumericCatNumPattern.split(baseName);
	            if (tokens.length > 0)
	            {
	                catNum = (String)formatter.formatFromUI(tokens[0]);
	            } else
	            {
	                catNumErrStatus = CN_ERROR_INVALID;
	                return null;
	            }
	        } else // Just Numeric
	        {
	            catNum = (String)formatter.formatFromUI(baseName);
	        }
	        
	        if (catNum != null)
	        {
                if (isCatNumInDB(catNum))
                {
                    return catNum;
                }
                catNumErrStatus = CN_ERROR_NOT_IN_DB;
                return null;
	        }
	        catNumErrStatus = CN_ERROR_INVALID;
	        
	    } else
	    {
	        String catNum = baseName.length() == fmtLen ? baseName : baseName.substring(0, fmtLen);
            if (formatter.isValid(catNum))
            {
                if (isCatNumInDB(catNum))
                {
                    return catNum;
                }
                catNumErrStatus = CN_ERROR_NOT_IN_DB;
            } else
            {
                catNumErrStatus = CN_ERROR_INVALID;
            }
	    }
	    return null;
	}
	
	/**
	 * build a list of files in directory.
	 */
	public Vector<File> bldFilesFromDir(final File directory, final String[] exts)
	{
	    DBTableInfo           colObjTI   = DBTableIdMgr.getInstance().getInfoById(1); // '1' is CollectionObject Table
	    UIFieldFormatterIFace catNumFmtr = colObjTI.getFieldByColumnName("CatalogNumber").getFormatter();
	    
	    collectionId = ((edu.ku.brc.specify.datamodel.Collection)AppContextMgr.getInstance().getClassObject(Collection.class)).getId();

	    Vector<File>  destFileList = new Vector<File>();
	    try
	    {
	        pStmtRE = DBConnection.getInstance().getConnection().prepareStatement(catNumLookupSQL);
	        
    	    fileNamesInErrList.clear();
    		
    		Collection<?> srcFileList  = FileUtils.listFiles(directory, exts, false);
    		for (Object f : srcFileList)
    		{
    		    String catNum = isCatalogNumberFileNameOK((File)f, catNumFmtr);
    		    if (catNum != null)
    		    {
    		        destFileList.add((File)f);
    		    } else
    		    {
    		        // OK, it is in Error
    		    }
    		}
    		
		} catch (Exception ex)
		{
		    ex.printStackTrace();
		} finally
		{
		    try
		    {
		        if (pStmtRE != null) pStmtRE.close();
		    } catch (Exception ex) {}
		}
	    
		return destFileList;
	}
	
	/**
	 * builds 'files' from file containing list of file names 
	 */
	protected void bldFilesFromList() throws IOException
	{
		files = new Vector<File>();
		List<?> fileNames = FileUtils.readLines(directory);
		for (Object f : fileNames)
		{
			files.add(new File((String )f));
//			if (files.size() == 10)
//			{
//				System.out.println("!!!!!!!!!Only processing first 10 files!!!!!!!!!");
//				break;
//			}
		}
	}
	/**
	 * Attach the files in directory.
	 */
	public void attachFiles() throws Exception
	{
		errors.clear();
		//attachments.clear();
		//session = DataProviderFactory.getInstance().createSession();
		try
		{
			for (File f : files)
			{
				attachFile(f);
			}
			if (errors.size() > 0)
			{
				Vector<String> errLines = new Vector<String>();
				for (Pair<String, String> error : errors)
				{
					String errLine = error.getFirst() + ": " + error.getSecond();
					System.out.println(errLine);
					errLines.add(errLine);
				}
				FileUtils.writeLines(new File(errLogName), errLines);
			} else
			{
				System.out.println("All files in the directory were attached.");
			}
		} finally
		{
			//session.close();
		}
	}
	
	/**
	 * @param f
	 * 
	 * Attach f.
	 */
	protected void attachFile(File f)
	{
		//System.out.println("Attaching " + f.getName());
		//System.out.println("attachFile Entry: " + Runtime.getRuntime().freeMemory());
		List<Integer> ids = fnParser.getRecordIds(f.getName());
		if (ids.size() == 0)
		{
			errors.add(new Pair<String, String>(f.getName(), 
					getResourceString("BatchAttachFiles.FileNameParseError")));
			return;
		} 
		
		for (Integer id : ids)
		{
			attachFileTo(f, id);
		}
		//System.out.println("attachFile Exit: " + Runtime.getRuntime().freeMemory());
	}

	
	/**
	 * @return the errLogName
	 */
	public String getErrLogName() 
	{
		return errLogName;
	}

	/**
	 * @param errLogName the errLogName to set
	 */
	public void setErrLogName(String errLogName) {
		this.errLogName = errLogName;
	}

	/**
	 * @param cls
	 * @return an initialized instance of the appropriate OjbectAttachmentIFace implementation.
	 */
	protected ObjectAttachmentIFace<? extends DataModelObjBase> getAttachmentObject(final Class<?> cls)
	{
		ObjectAttachmentIFace<? extends DataModelObjBase> result = null;
		if (cls.equals(Accession.class))
		{
			result = new AccessionAttachment();
		}
		if (cls.equals(Taxon.class))
		{
			result =  new TaxonAttachment();
		}
		if (cls.equals(Locality.class))
		{
			result =  new LocalityAttachment();
		}
		if (cls.equals(CollectingEvent.class))
		{
			result =  new CollectingEventAttachment();
		}
		if (cls.equals(CollectionObject.class))
		{
			result =  new CollectionObjectAttachment();
		}
		if (result != null)
		{
			((DataModelObjBase )result).initialize();
		}
		return result;
    }

    /**
     * @param f
     * @param attachTo
     * 
     * Attaches f to the object with key attachTo
     */
    @SuppressWarnings("unchecked")
    protected boolean attachFileTo(final File f, final Integer attachTo)
    {
        return attachFileTo(f, attachTo, null);
    }
    
    /**
     * @param metaData
     * @param cls
     * @param tagId
     * @return
     */
    private Calendar getExifFileDate(final Metadata metadata, final Class<? extends Directory> cls, final int tagId)
    {
        if (metadata != null)
        {
            Directory directory = metadata.getDirectory(cls);
            if (directory != null)
            {
                Date date = directory.getDate(tagId);
                if (date != null)
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    return cal.get(Calendar.YEAR) > 1850 ? cal : null;
                }
            }
        }
        return null;
    }

    /**
     * Attaches f to the object with key attachTo
     * 
     * @param fileToSave
     * @param attachToId
     * @param session
     */
    @SuppressWarnings("unchecked")
    protected boolean attachFileTo(final File    fileToSave, 
                                   final Integer attachToId, 
                                   final DataProviderSessionIFace session)
    {
        boolean isOK = true;
		//System.out.println("Attaching " + f.getName() + " to " + attachTo);
		//System.out.println("attachFileTo Entry: " + Runtime.getRuntime().freeMemory());
		DataProviderSessionIFace localSession = session == null ? DataProviderFactory.getInstance().createSession() : session;
		boolean tblTransactionOpen = false;
		if (localSession != null)
		{
			try
			{
				AttachmentOwnerIFace<?> rec = getAttachmentOwner(localSession, attachToId);
				//session.attach(rec);
				localSession.beginTransaction();
				tblTransactionOpen = true;
				
				Set<ObjectAttachmentIFace<?>> attachees = (Set<ObjectAttachmentIFace<?>>) rec.getAttachmentReferences();
				int        ordinal    = 0;
				Attachment attachment = new Attachment();
				attachment.initialize();
				if (fileToSave.exists())
				{
					attachment.setOrigFilename(fileToSave.getPath());
				} else
				{
					attachment.setOrigFilename(fileToSave.getName());
				}
				
				attachment.setTableId(rec.getAttachmentTableId());
				
                attachment.setFileCreatedDate(ImageMetaDataHelper.getEmbeddedDateOrFileDate(fileToSave));
				
				attachment.setTitle(fileToSave.getName());
				ObjectAttachmentIFace<DataModelObjBase> oaif = 
				    (ObjectAttachmentIFace<DataModelObjBase>) getAttachmentObject(rec.getClass());
				//CollectionObjectAttachment oaif = new CollectionObjectAttachment();
				//oaif.initialize();
				oaif.setAttachment(attachment);
				oaif.setObject((DataModelObjBase) rec);
				//oaif.setCollectionObject((CollectionObject )rec);
				oaif.setOrdinal(ordinal);
				//((CollectionObject )rec).getAttachmentReferences().add(oaif);
				
				attachees.add(oaif);

				BusinessRulesIFace busRule = DBTableIdMgr.getInstance().getBusinessRule(rec.getClass());
				if (busRule != null)
				{
					busRule.beforeSave(rec, localSession);
				}
				localSession.saveOrUpdate(rec);
				
				if (busRule != null)
				{
					if (!busRule.beforeSaveCommit(rec, localSession))
					{
						localSession.rollback();
						throw new Exception("Business rules processing failed");
					}
				}
				if (fileToSave.exists())
				{
					AttachmentUtils.getAttachmentManager().setStorageLocationIntoAttachment(oaif.getAttachment(), false);
					oaif.getAttachment().storeFile(false); // false means do not display an error dialog
				} else
				{
				    log.debug(fileToSave.getName()+" doesn't exist.");
				}

				localSession.commit();
				//System.out.println("ATTACHED " + f.getName() + " to " + attachTo);
				tblTransactionOpen = false;
				if (busRule != null)
				{
					busRule.afterSaveCommit(rec, localSession);
				}
				
				//this is necessary to prevent memory leak -- no idea why or how -- but the merge
				//prevents out-of-memory crashes that occur after about 6300 records.
				//session.merge(rec);
				
				attachees = null;
				attachment = null;
				rec = null;
				oaif = null;
				
			
			} catch (HibernateException he)
			{
			    isOK = false;
				if (tblTransactionOpen)
				{
					localSession.rollback();
				}
				errors.add(new Pair<String, String>(fileToSave.getName(), he.getLocalizedMessage()));
				
			} catch (Exception ex)
			{
                isOK = false;
				if (tblTransactionOpen)
				{
					localSession.rollback();
				}
				errors.add(new Pair<String, String>(fileToSave.getName(), ex
						.getLocalizedMessage()));
			} finally
			{
			    if (session == null)
			    {
			        localSession.close();
			    }
				//session.clear();
			}
		} else
		{
			errors.add(new Pair<String, String>(fileToSave.getName(), UIRegistry
					.getResourceString("BatchAttachFiles.UnableToAttach")));
            isOK = false;
		}
		//System.out.println("attachFileTo Exit: " + Runtime.getRuntime().freeMemory());
		return isOK;
	}
	
	/**
	 * @param session
	 * @param recId
	 * @return record with key recId
	 */
	protected AttachmentOwnerIFace<?> getAttachmentOwner(DataProviderSessionIFace sessionArg, Integer recId)
	{
		return (AttachmentOwnerIFace<?>)sessionArg.get(attachOwnerClass, recId);
	}
	
	/**
	 * @param args
	 */
	    public static void main(String[] args)
	    {
	        log.debug("********* Current ["+(new File(".").getAbsolutePath())+"]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	        // This is for Windows and Exe4J, turn the args into System Properties
	 
	        // Set App Name, MUST be done very first thing!
	        //setAppName("SchemaExporter");  //$NON-NLS-1$
	        setAppName("Specify");  //$NON-NLS-1$

	        setEmbeddedDBPath(getDefaultEmbeddedDBPath()); // on the local machine
	        
	        for (String s : args)
	        {
	            String[] pairs = s.split("="); //$NON-NLS-1$
	            if (pairs.length == 2)
	            {
	                if (pairs[0].startsWith("-D")) //$NON-NLS-1$
	                {
	                    System.setProperty(pairs[0].substring(2, pairs[0].length()), pairs[1]);
	                } 
	            } else
	            {
	                String symbol = pairs[0].substring(2, pairs[0].length());
	                System.setProperty(symbol, symbol);
	            }
	        }
	        
	        // Now check the System Properties
	        String appDir = System.getProperty("appdir");
	        if (StringUtils.isNotEmpty(appDir))
	        {
	            setDefaultWorkingPath(appDir);
	        }
	        
	        String appdatadir = System.getProperty("appdatadir");
	        if (StringUtils.isNotEmpty(appdatadir))
	        {
	            setBaseAppDataDir(appdatadir);
	        }
	        
	        // For Debugging Only 
	        //System.setProperty("mobile", "true");
	        //System.setProperty("embedded", "true");
	        
	        String mobile = System.getProperty("mobile");
	        if (StringUtils.isNotEmpty(mobile))
	        {
	            setMobile(true);
	        }
	        
	        String embeddedStr = System.getProperty("embedded");
	        if (StringUtils.isNotEmpty(embeddedStr))
	        {
	            setEmbedded(true);
	        }
	        
	        String embeddeddbdir = System.getProperty("embeddeddbdir");
	        if (StringUtils.isNotEmpty(embeddeddbdir))
	        {
	            setEmbeddedDBPath(embeddeddbdir);
	        } else
	        {
	            setEmbeddedDBPath(getDefaultEmbeddedDBPath()); // on the local machine
	        }
	        
	        // Then set this
	        IconManager.setApplicationClass(Specify.class);
	        IconManager.loadIcons(XMLHelper.getConfigDir("icons_datamodel.xml")); //$NON-NLS-1$
	        IconManager.loadIcons(XMLHelper.getConfigDir("icons_plugins.xml")); //$NON-NLS-1$
	        IconManager.loadIcons(XMLHelper.getConfigDir("icons_disciplines.xml")); //$NON-NLS-1$

	        
	        
	        System.setProperty(AppContextMgr.factoryName,                   "edu.ku.brc.specify.config.SpecifyAppContextMgr");      // Needed by AppContextMgr //$NON-NLS-1$
	        System.setProperty(AppPreferences.factoryName,                  "edu.ku.brc.specify.config.AppPrefsDBIOIImpl");         // Needed by AppReferences //$NON-NLS-1$
	        System.setProperty("edu.ku.brc.ui.ViewBasedDialogFactoryIFace", "edu.ku.brc.specify.ui.DBObjDialogFactory");            // Needed By UIRegistry //$NON-NLS-1$ //$NON-NLS-2$
	        System.setProperty("edu.ku.brc.ui.forms.DraggableRecordIdentifierFactory", "edu.ku.brc.specify.ui.SpecifyDraggableRecordIdentiferFactory"); // Needed By the Form System //$NON-NLS-1$ //$NON-NLS-2$
	        System.setProperty("edu.ku.brc.dbsupport.AuditInterceptor",     "edu.ku.brc.specify.dbsupport.AuditInterceptor");       // Needed By the Form System for updating Lucene and logging transactions //$NON-NLS-1$ //$NON-NLS-2$
	        System.setProperty("edu.ku.brc.dbsupport.DataProvider",         "edu.ku.brc.specify.dbsupport.HibernateDataProvider");  // Needed By the Form System and any Data Get/Set //$NON-NLS-1$ //$NON-NLS-2$
	        System.setProperty("edu.ku.brc.ui.db.PickListDBAdapterFactory", "edu.ku.brc.specify.ui.db.PickListDBAdapterFactory");   // Needed By the Auto Cosmplete UI //$NON-NLS-1$ //$NON-NLS-2$
	        System.setProperty(CustomQueryFactory.factoryName,              "edu.ku.brc.specify.dbsupport.SpecifyCustomQueryFactory"); //$NON-NLS-1$
	        System.setProperty(UIFieldFormatterMgr.factoryName,             "edu.ku.brc.specify.ui.SpecifyUIFieldFormatterMgr");           // Needed for CatalogNumberign //$NON-NLS-1$
	        System.setProperty(QueryAdjusterForDomain.factoryName,          "edu.ku.brc.specify.dbsupport.SpecifyQueryAdjusterForDomain"); // Needed for ExpressSearch //$NON-NLS-1$
	        System.setProperty(SchemaI18NService.factoryName,               "edu.ku.brc.specify.config.SpecifySchemaI18NService");         // Needed for Localization and Schema //$NON-NLS-1$
	        System.setProperty(WebLinkMgr.factoryName,                      "edu.ku.brc.specify.config.SpecifyWebLinkMgr");                // Needed for WebLnkButton //$NON-NLS-1$
	        System.setProperty(SecurityMgr.factoryName,                     "edu.ku.brc.af.auth.specify.SpecifySecurityMgr");              // Needed for Tree Field Names //$NON-NLS-1$
	        System.setProperty(DBMSUserMgr.factoryName,                     "edu.ku.brc.dbsupport.MySQLDMBSUserMgr");
	        System.setProperty(SchemaUpdateService.factoryName,             "edu.ku.brc.specify.dbsupport.SpecifySchemaUpdateService");   // needed for updating the schema
	        System.setProperty(GenericGUIDGeneratorFactory.factoryName,     "edu.ku.brc.specify.config.SpecifyGUIDGeneratorFactory");
	        
	        final AppPreferences localPrefs = AppPreferences.getLocalPrefs();
	        localPrefs.setDirPath(getAppDataDir());
	        adjustLocaleFromPrefs();
	    	final String iRepPrefDir = localPrefs.getDirPath(); 
	        int mark = iRepPrefDir.lastIndexOf(getAppName(), iRepPrefDir.length());
	        final String SpPrefDir = iRepPrefDir.substring(0, mark) + "Specify";
	        HibernateUtil.setListener("post-commit-update", new edu.ku.brc.specify.dbsupport.PostUpdateEventListener()); //$NON-NLS-1$
	        HibernateUtil.setListener("post-commit-insert", new edu.ku.brc.specify.dbsupport.PostInsertEventListener()); //$NON-NLS-1$
	        HibernateUtil.setListener("post-commit-delete", new edu.ku.brc.specify.dbsupport.PostDeleteEventListener()); //$NON-NLS-1$
	        
	        SwingUtilities.invokeLater(new Runnable() {
	            @SuppressWarnings("synthetic-access") //$NON-NLS-1$
	          public void run()
	            {
	                
	                try
	                {
	                    UIHelper.OSTYPE osType = UIHelper.getOSType();
	                    if (osType == UIHelper.OSTYPE.Windows )
	                    {
	                        UIManager.setLookAndFeel(new PlasticLookAndFeel());
	                        PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
	                        
	                    } else if (osType == UIHelper.OSTYPE.Linux )
	                    {
	                        UIManager.setLookAndFeel(new PlasticLookAndFeel());
	                    }
	                }
	                catch (Exception e)
	                {
	                    edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
	                    edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(ExportPanel.class, e);
	                    log.error("Can't change L&F: ", e); //$NON-NLS-1$
	                }
	                
	                DatabaseLoginPanel.MasterPasswordProviderIFace usrPwdProvider = new DatabaseLoginPanel.MasterPasswordProviderIFace()
	                {
	                    @Override
	                    public boolean hasMasterUserAndPwdInfo(final String username, final String password, final String dbName)
	                    {
	                        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password))
	                        {
	                            UserAndMasterPasswordMgr.getInstance().set(username, password, dbName);
	                            boolean result = false;
	                            try
	                            {
	                            	try
	                            	{
	                            		AppPreferences.getLocalPrefs().flush();
	                            		AppPreferences.getLocalPrefs().setDirPath(SpPrefDir);
	                            		AppPreferences.getLocalPrefs().setProperties(null);
	                            		result = UserAndMasterPasswordMgr.getInstance().hasMasterUsernameAndPassword();
	                            	}
	                            	finally
	                            	{
	                            		AppPreferences.getLocalPrefs().flush();
	                            		AppPreferences.getLocalPrefs().setDirPath(iRepPrefDir);
	                            		AppPreferences.getLocalPrefs().setProperties(null);
	                            	}
	                            } catch (Exception e)
	                            {
	                            	edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
	                            	edu.ku.brc.exceptions.ExceptionTracker.getInstance()
	    								.capture(MainFrameSpecify.class, e);
	                            	result = false;
	                            }
	                            return result;
	                        }
	                        return false;
	                    }
	                    
	                    @Override
	                    public Pair<String, String> getUserNamePassword(final String username, final String password, final String dbName)
	                    {
	                        UserAndMasterPasswordMgr.getInstance().set(username, password, dbName);
                            
	                        Pair<String, String> result = null;
	                        try
	                        {
	                        	try
	                        	{
	                        		AppPreferences.getLocalPrefs().flush();
	                        		AppPreferences.getLocalPrefs().setDirPath(SpPrefDir);
	                        		AppPreferences.getLocalPrefs().setProperties(null);
	                        		result = UserAndMasterPasswordMgr.getInstance().getUserNamePasswordForDB();
	                        	}
	                        	finally
	                        	{
	                        		AppPreferences.getLocalPrefs().flush();
	                        		AppPreferences.getLocalPrefs().setDirPath(iRepPrefDir);
	                        		AppPreferences.getLocalPrefs().setProperties(null);
	                        	}
	                        } catch (Exception e)
	                        {
	                        	edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
	                        	edu.ku.brc.exceptions.ExceptionTracker.getInstance()
									.capture(MainFrameSpecify.class, e);
	                        	result = null;
	                        }
	                        return result;
	                    }
	                    @Override
	                    public boolean editMasterInfo(final String username, final String dbName, final boolean askFroCredentials)
	                    {
	                        boolean result = false;
	                    	try
	                        {
	                        	try
	                        	{
	                        		AppPreferences.getLocalPrefs().flush();
	                        		AppPreferences.getLocalPrefs()
										.setDirPath(SpPrefDir);
	                        		AppPreferences.getLocalPrefs().setProperties(null);
	                        		result =  UserAndMasterPasswordMgr
										.getInstance()
										.editMasterInfo(username, dbName, askFroCredentials);
	                        	} finally
	                        	{
	                        		AppPreferences.getLocalPrefs().flush();
	                        		AppPreferences.getLocalPrefs().setDirPath(
										iRepPrefDir);
	                        		AppPreferences.getLocalPrefs().setProperties(null);
	                        	}
	                        } catch (Exception e)
	                        {
	                        	edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
	                        	edu.ku.brc.exceptions.ExceptionTracker.getInstance()
									.capture(MainFrameSpecify.class, e);
	                        	result = false;
	                        }
	                    	return result;
	                   }
	                };
	                String nameAndTitle = getResourceString("BatchAttachFiles.AppTitle"); // I18N
	                setRelease(true);
	                UIHelper.doLogin(usrPwdProvider, true, false, false, new BatchAttachLauncher(), Specify.getLargeIconName(), nameAndTitle, nameAndTitle, "SpecifyWhite32", "login"); // true
	                localPrefs.load();
	            }
	        });
	    }

    /**
     * 
     */
    protected static void adjustLocaleFromPrefs()
    {
        String language = AppPreferences.getLocalPrefs().get("locale.lang", null); //$NON-NLS-1$
        if (language != null)
        {
            String country  = AppPreferences.getLocalPrefs().get("locale.country", null); //$NON-NLS-1$
            String variant  = AppPreferences.getLocalPrefs().get("locale.var",     null); //$NON-NLS-1$
            
            Locale prefLocale = new Locale(language, country, variant);
            
            Locale.setDefault(prefLocale);
            setResourceLocale(prefLocale);
        }
        
        try
        {
            ResourceBundle.getBundle("resources", Locale.getDefault()); //$NON-NLS-1$
            
        } catch (MissingResourceException ex)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(MainFrameSpecify.class, ex);
            Locale.setDefault(Locale.ENGLISH);
            setResourceLocale(Locale.ENGLISH);
        }
        
    }
    
}
