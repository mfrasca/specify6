/* Copyright (C) 2009, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute,
 * 1345 Jayhawk Boulevard, Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package edu.ku.brc.specifydroid;

import java.io.File;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Oct 27, 2009
 *
 */
public class SpecifyActivity extends SpBaseActivity
{
    public static final String   TAXA_FILE_PREF = "TAXA_FILE_PREF";
    
    /**
     * 
     */
    public SpecifyActivity()
    {
        super();
    }
    
    /* (non-Javadoc)
     * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        setContentView(R.layout.anzatrek);

        GridView gridview = (GridView)findViewById(R.id.anzagridview);
        gridview.setBackgroundColor(Color.WHITE);
        gridview.setAdapter(new SpecifyMainPanelAdapter(this));
        
        File root = Environment.getExternalStorageDirectory();
        final File taxaFile = new File(root, "fish_taxa.csv");
        long  fileTime      = taxaFile.lastModified();
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        long prefFileTime = prefs.getLong(TAXA_FILE_PREF, -1);
        
        if (prefFileTime == fileTime)
        {
            return;
        }

        if (taxaFile.exists())
        {
            final ProgressDialog prgDlg = new ProgressDialog( this );
            prgDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            prgDlg.setMessage("Loading...");
            prgDlg.setCancelable(false);

            prgDlg.show();
            
            prgDlg.setMax((int)taxaFile.length());
            prgDlg.setProgress(0); 
            
            SQLiteDatabase database = getDB();
            if (TaxonLoadThread.getInstance() == null)
            {
                TaxonLoadThread tlt = new TaxonLoadThread(database, taxaFile);
                tlt.set(this, database, prgDlg);
                tlt.start();
                TaxonLoadThread.setInstance(tlt);
                
            } else
            {
                TaxonLoadThread.getInstance().set(this, database, prgDlg);
            }
            closeDB();
        }
    }
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        Log.d("DBG", "onConfigurationChanged "+newConfig);
        super.onConfigurationChanged(newConfig);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause()
    {
        Log.d("DBG", "onPause");
        super.onPause();
        
        //database.close();
        
        if (TaxonLoadThread.getInstance() != null && 
            TaxonLoadThread.getInstance().getPrgDlg() != null)
        {
            TaxonLoadThread.getInstance().getPrgDlg().dismiss();
            TaxonLoadThread.getInstance().set(null, null, null);
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPostCreate(android.os.Bundle)
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        Log.d("DBG", "onPostCreate");
        super.onPostCreate(savedInstanceState);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPostResume()
     */
    @Override
    protected void onPostResume()
    {
        Log.d("DBG", "onPostResume");
        super.onPostResume();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onRestart()
     */
    @Override
    protected void onRestart()
    {
        Log.d("DBG", "onRestart");
       super.onRestart();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        Log.d("DBG", "onResume");
        super.onResume();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStart()
     */
    @Override
    protected void onStart()
    {
        Log.d("DBG", "onStart");
       super.onStart();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop()
    {
        Log.d("DBG", "onStop");
        super.onStop();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy()
    {
        Log.d("DBG", "onDestroy");
        super.onDestroy();
    }
    
    /*private void checkPath()
    {
        try
        {
            ContentValues values = new ContentValues();
            values.put(Media.TITLE, "MyImage");
            values.put(Media.DESCRIPTION, "Image capture by camera");

            Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
            
            Log.d("ZZZ", uri.getPath());
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(getClass().getSimpleName(), ex.getMessage(), ex);
        }
    }
*/
}
