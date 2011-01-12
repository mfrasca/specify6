/*
 * Copyright (C) 2009, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute, 1345 Jayhawk Boulevard,
 * Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */
package edu.ku.brc.specifydroid;

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.ku.brc.specifydroid.datamodel.Trip;

/**
 * @author rods
 * 
 * @code_status Alpha
 * 
 * Oct 27, 2009
 * 
 */
public class TripListActivity extends Activity
{
    public final static int CONFIG_TRIP = 0; // Config
    public final static int COLL_TRIP   = 1; // Config
    public final static int OBS_TRIP    = 2; // Config
    
    public final static String ID_EXTRA     = "edu.ku.brc.specifydroid._ID";
    public final static String TRIP_TYPE    = "edu.ku.brc.specifydroid.TRIP_TYPE";
    public final static String DETAIL_CLASS = "edu.ku.brc.specifydroid.DETAIL_CLASS";
    
    private AtomicBoolean              isActive    = new AtomicBoolean(true);
    private Cursor                     cursorModel = null;
    private SharedPreferences          prefs       = null;
    private ListView                   list        = null;
    
    private Integer                    tripType      = CONFIG_TRIP; // Default Trip type
    private Class<?>                   detailedClass = TripMainActivity.class;
    
    /**
     * 
     */
    public TripListActivity()
    {
        super();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        String detailedClsName = null;
        if (savedInstanceState != null)
        {
            tripType        = savedInstanceState.getInt(TRIP_TYPE, CONFIG_TRIP);
            detailedClsName = savedInstanceState.getString(DETAIL_CLASS);
        } else
        {
            tripType        = getIntent().getIntExtra(TRIP_TYPE, CONFIG_TRIP);
            detailedClsName = getIntent().getStringExtra(DETAIL_CLASS);
        }
        
        if (detailedClsName != null)
        {
            try
            {
                detailedClass = Class.forName(detailedClsName);
            } catch (ClassNotFoundException ex) 
            {
                detailedClass = TripMainActivity.class;
            }
        }
        
        setContentView(R.layout.main);
        
        list  = (ListView) findViewById(R.id.trips);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        list.setOnItemClickListener(createItemClickListener());
        
        initList();

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause()
    {
        super.onPause();

        isActive.set(false);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();

        isActive.set(true);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (cursorModel != null)
        {
            cursorModel.close();
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(TRIP_TYPE, tripType);
        savedInstanceState.putString(DETAIL_CLASS, detailedClass.getName());
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        new MenuInflater(getApplication()).inflate(R.menu.tripmenus, menu);

        return (super.onCreateOptionsMenu(menu));
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.add)
        {
            startActivity(new Intent(this, TripDetailActivity.class));
            return true;
            
        } else if (item.getItemId() == R.id.prefs)
        {
            startActivity(new Intent(this, EditPreferences.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 
     */
    private void initList()
    {
        if (cursorModel != null)
        {
            stopManagingCursor(cursorModel);
            cursorModel.close();
        }

        /*final ProgressDialog prgDlg = new ProgressDialog(this);
        prgDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prgDlg.show();
        */
        
        final ProgressDialog prgDlg = ProgressDialog.show(this, null, "Loading...", true);
        prgDlg.show();
        
        new Thread() 
        {
            public void run() 
            {
                 try
                 {
                     String where = null;
                     if (tripType > 0)
                     {
                         String type;
                         if (tripType == TripListActivity.OBS_TRIP)
                         {
                             type = "observing";
                         } else
                         {
                             type = "collecting"; // defaults to Collecting Trip
                         }
                         where = String.format("WHERE Type = '%s'", type);
                     }
                     
                     
                     cursorModel = Trip.getAll(getDB(), "trip", where, prefs.getString("sort_order", ""));
                     
                     runOnUiThread(new Runnable()
                     {
                         public void run()
                         {
                             if (cursorModel != null)
                             {
                                 startManagingCursor(cursorModel);
    
                                 list.setAdapter(new DataAdapterWithBinder(new TripDataViewBinder(),
                                                                           TripListActivity.this, 
                                                                           R.layout.row, 
                                                                           cursorModel, 
                                                                           new String[] {"Name", "TripDate", "Type"}, 
                                                                           new int[] {R.id.name, R.id.date, R.id.icon}));
                             }
                         }
                     });
                     
                 } catch (Exception e) {  }
                 // Dismiss the Dialog
                 prgDlg.dismiss();
            }
       }.start(); 

    }

    /**
     * @return the class of the Detailed Activity to be launched when an item in the list is clicked
     */
    protected Class<?> getDetailActivityClass()
    {
        return detailedClass;
    }
    
    /**
     * @return
     */
    protected AdapterView.OnItemClickListener createItemClickListener()
    {
        return new AdapterView.OnItemClickListener()
        {
            public void onItemClick(final AdapterView<?> parent,
                                    final View view,
                                    final int position,
                                    final long id)
            {
                Intent i = new Intent(TripListActivity.this, getDetailActivityClass());
                i.putExtra(ID_EXTRA, String.valueOf(id));
                startActivity(i);
            }
        };
    }

    //------------------------------------------------------------------------------
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener()
    {
        public void onSharedPreferenceChanged(SharedPreferences sharedPrefs,
                                              String key)
        {
            if (key.equals("sort_order"))
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        initList();
                    }
                });
            }
        }
    };

    
    //------------------------------------------------------------------------
    //-- Database Access
    //------------------------------------------------------------------------
    private TripSQLiteHelper  tripDBHelper = null;
    private SQLiteDatabase getDB()
    {
        if (tripDBHelper == null)
        {
            tripDBHelper = new TripSQLiteHelper(this.getApplicationContext());
        }
        return tripDBHelper.getWritableDatabase();
    }
    
}
