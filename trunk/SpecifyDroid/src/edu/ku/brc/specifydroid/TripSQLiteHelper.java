package edu.ku.brc.specifydroid;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import edu.ku.brc.specifydroid.datamodel.Trip;
import edu.ku.brc.utils.ZipFileHelper;

class TripSQLiteHelper extends SQLiteOpenHelper
{
    private static final String  DATABASE_NAME  = "trip.db";
    private static final int     SCHEMA_VERSION = 1;
    
    private static boolean firstTime      = false;
    
    private Context appContext;

    /**
     * @param context
     */
    public TripSQLiteHelper(final Context appContext)
    {
        super(appContext, DATABASE_NAME, null, SCHEMA_VERSION);
        this.appContext = appContext;
    }
    
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onOpen(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onOpen(final SQLiteDatabase db)
    {
        super.onOpen(db);
        
        dropAndBuild(db, firstTime);
        
        firstTime = false;
    }
    
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(final SQLiteDatabase db)
    {
        dropAndBuild(db, true);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion)
    {
        android.util.Log.w("Trip", "Upgrading database, which will destroy all old data");
        //db.execSQL("DROP TABLE IF EXISTS trip");
        //onCreate(db);
    }
    /**
     * @param text
     * @param repl
     * @param with
     * @param max
     * @return
     */
    public static String replace(final String text, final String repl, final String with, int max)
    {
        if (text == null || (repl != null && repl.length() == 0) || with == null || max == 0) { return text; }

        StringBuffer buf = new StringBuffer(text.length());
        int start = 0, end = 0;
        while ((end = text.indexOf(repl, start)) != -1)
        {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0)
            {
                break;
            }
        }
        buf.append(text.substring(start));
        return buf.toString();
    }
    
    /**
     * @param db
     * @param doDrop
     */
    private void dropAndBuild(final SQLiteDatabase db, 
                              final boolean doDrop)
    {
        if (doDrop)
        {
            db.execSQL("DROP TABLE IF EXISTS trip");
            db.execSQL("DROP TABLE IF EXISTS tripdatadef");
            db.execSQL("DROP TABLE IF EXISTS tripdatacell");
        }
        
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='trip'", null);
        try
        {
            //if (true)
            if (c.getCount() == 0)
            {
                Resources    res      = appContext.getResources();
                AssetManager assetMgr = res.getAssets();
                for (String assetName : assetMgr.list(""))
                {
                    android.util.Log.w("Trip", assetName);
                    if (assetName.equals("build.sql"))
                    {
                        byte[]        bytes  = new byte[2048];
                        StringBuilder sb     = new StringBuilder();
                        InputStream   inStrm = assetMgr.open(assetName);
                        while (inStrm.available() > 0)
                        {
                            int numRead = inStrm.read(bytes);
                            if (numRead > 0)
                            {
                                String line = (new String(bytes, 0, numRead)).trim();
                                line = replace(line, "\n", " ", -1);
                                sb.append(line);
                            } else
                            {
                                break;
                            }
                        }
                        
                        String[] lines = sb.toString().split(";");
                        for (String sql : lines)
                        {
                            android.util.Log.i("Trip", "["+sql+"]");
                            db.execSQL(sql);
                        }
                        inStrm.close();
                    }
                }
            } 
           
        } catch (IOException ex)
        {
            ex.printStackTrace();
            android.util.Log.e("Trip", "Error building DBs", ex);
            
        } finally
        {
            if (c != null)
            {
                c.close();
            }
        }
    }
    
    /**
     * @param db
     */
    public void export(final Activity activity, final SQLiteDatabase db, final String tripId)
    {
        PrintWriter pw = null;
        try
        {
            final File root = Environment.getExternalStorageDirectory();
            if (root.canWrite())
            {
                pw = new PrintWriter(new File(root, "test.xml"));
                
                final PrintWriter pwf = pw;
                final ProgressDialog prgDlg = ProgressDialog.show(activity, null, "Exporting...", true); // I18N
                prgDlg.show();
                
                new Thread() 
                {
                    public void run() 
                    {
                         try
                         {
                             Trip trip = Trip.getById(db, tripId);
                             
                             trip.toXML(db, pwf);

                             Thread.sleep(2000);
                             
                         } catch (Exception e) {  }
                         // Dismiss the Dialog
                         prgDlg.dismiss();
                    }
               }.start();
                
                /*trip.writeCVSValues(pw);
                pw.println("---");
                
                Cursor c = db.execSQL("SELECT ");
                
                pw = new PrintWriter(new File("/sddata/test.csv"));
                
                Trip trip = Trip.getById(db, tripId);
                trip.writeCVSHeader(pw);
                trip.writeCVSValues(pw);
                pw.println("---");
                
                Cursor c = db.execSQL("SELECT )*/
            } else
            {
                Log.d("TripSQL", "Can't write to sdcard");
            }
                    
        } catch (IOException ex)
        {
            Log.e("", "Error export to CSV", ex);
        } finally
        {
            if (pw != null)
            {
                pw.close();
            }
        }
    }
    
    /**
     * @param db
     */
    public void loadTestData(final SQLiteDatabase db)
    {
        int tripId = 1;
        int bInx   = 1; // base index
        
        String[] values= {
                "Little Pigeon River", "-83.53372824519164", "35.69161799381586", "Catostomus commersoni",
                "Little Pigeon River", "-83.5334314327779",  "35.69182845399097",  "Hypentelium nigricans",
                "Little Pigeon River", "-83.53709797155", "35.69043458326836", "Moxostoma carinatum",
                "Small Falls",         "-83.49230859919675", "35.68309906312557", "Moxostoma duquesnei",
        };

        for (int i=3;i<values.length;i+=4)
        {
            Cursor c = db.rawQuery("SELECT Name FROM taxon WHERE Name='"+values[i]+"'", null);
            if (!c.moveToFirst())
            {
                String sql = "INSERT INTO taxon (ParentID, Name, RankID) VALUES(0,'"+values[i]+"', 220)";
                db.execSQL(sql);
            }
            c.close();
        }
        
        int rowIndex = SQLUtils.getCount(db, "SELECT TripRowIndex AS count FROM tripdatacell WHERE TripID = " +tripId+" ORDER BY TripRowIndex DESC LIMIT 1");
        
        String insertStr = "INSERT INTO tripdatacell (TripDataDefID, TripID, TripRowIndex, Data) VALUES(%d, %d, %d, '%s')";
        
        // Do Locality Name
        int row = rowIndex + 1;
        for (int i=0;i<values.length;i+=4)
        {
            String sql = String.format(insertStr, bInx, tripId, row, values[i]);
            db.execSQL(sql);
            row++;
        }

        // Do Lat
        row = rowIndex + 1;
        for (int i=1;i<values.length;i+=4)
        {
            String sql = String.format(insertStr, bInx+3, tripId, row, values[i]);
            db.execSQL(sql);
            row++;
        }
        
        // Do Lon
        row = rowIndex + 1;
        for (int i=2;i<values.length;i+=4)
        {
            String sql = String.format(insertStr, bInx+2, tripId, row, values[i]);
            db.execSQL(sql);
            row++;
        }
        
        // Do Lon
        row = rowIndex + 1;
        for (int i=3;i<values.length;i+=4)
        {
            String sql = String.format(insertStr, bInx+4, tripId, row, values[i]);
            db.execSQL(sql);
            row++;
        }
    }
    
    /**
     * @param db
     * @param taxaFile
     * @return
     */
    public static boolean loadTaxa(final Activity       activity,
                                   final SQLiteDatabase db, 
                                   final File           taxaFile,
                                   final ProgressDialog prgDlg)
    {
        boolean doCreateTable = false;
        boolean doAddRecs     = false;
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='taxon'", null);
        if (c.getCount() == 0)
        {
            doCreateTable = true;
        }
        c.close();
        
        boolean doDrop = false;
        
        if (!doCreateTable)
        {
            int cnt = SQLUtils.getCount(db, "SELECT COUNT(*) AS count FROM taxon");
            if (cnt < 1)
            {
                doAddRecs = true;
            } else
            {
                doDrop = true;
            }
        } 

        if (doDrop)
        {
            db.execSQL("DROP TABLE taxon");
            doCreateTable = true;
            doAddRecs     = true;
        }
        
        if (doCreateTable || doAddRecs)
        {
            String taxaFileName = taxaFile.getName();
            File   inFile       = null;
            if (taxaFileName.endsWith(".zip"))
            {
                String outName = taxaFileName.substring(0, taxaFileName.length()-4);
                inFile = ZipFileHelper.getInstance().unzipToSingleFile(taxaFile, new File(outName));
                if (inFile == null)
                {
                    return false;
                }
            } else
            {
                inFile = taxaFile;
            }
            
            if (doCreateTable)
            {
                String tblStr = "CREATE TABLE `taxon` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT, `ParentID` INTEGER, `Name` VAR CHAR(128),  `RankID` SHORT)";
                db.execSQL(tblStr);
            }
            
            final double maxSize = inFile.length();
            
            Log.d("LOAD", "Size: "+maxSize);
            
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            
            BufferedInputStream bis    = null;
            DataInputStream     dis    = null;

            try
            {
                // Count lines in taxon File
                InputStream inStrm = new FileInputStream(inFile);
                bis = new BufferedInputStream(inStrm);
                dis = new DataInputStream(bis);
                
                int    cnt   = 1;
                double total = 0.0;
                while (dis.available() != 0)
                {
                    String   line     = dis.readLine();
                    String[] dataCols = line.split(",");
                    total += line.length() + 1;
                    
                    final String taxonName = dataCols[1]+' '+dataCols[2];
                    String sql = "INSERT INTO taxon (ParentID, Name, RankID) VALUES(0,'"+taxonName+"', 220)";
                    db.execSQL(sql);
                    
                    if (cnt % 100 == 0)
                    {
                        Log.d("load", "Cnt: "+cnt);
                        
                        final double tot   = total;
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                prgDlg.setMessage("Loaded: \n"+taxonName);
                                prgDlg.setProgress((int)tot);
                                //prgDlg.incrementProgressBy(1);
                            }
                        });
                    }
                    cnt++;
                    
                    // Temp stop after a 1,000
                    if (cnt > 300)
                    {
                        break;
                    }
                }

                bis.close();
                dis.close();
                inStrm.close();
                
                inFile.delete();
                
                return true;
                
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

}