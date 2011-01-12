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

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.ku.brc.specifydroid.datamodel.TripDataDef;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Nov 10, 2009
 *
 */
public class TripDataDefActivity extends Activity
{
    public final static String ID_EXTRA = "edu.ku.brc.specifydroid._TripDataDefID";
    
    private AtomicBoolean              isActive = new AtomicBoolean(true);
    private Cursor                     cursorModel = null;
    private ListView                   list        = null;
    private String                     tripId      = null;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        if (savedInstanceState != null)
        {
            tripId = savedInstanceState.getString(TripListActivity.ID_EXTRA);
        } else
        {
            tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        }

        list = (ListView) findViewById(R.id.trips); // use the same layout
        list.setOnItemClickListener(onListClick);
        initList();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        
        outState.putString(tripId, TripListActivity.ID_EXTRA);
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
    public void onStop()
    {
        super.onStop();
        
        if (cursorModel != null)
        {
            cursorModel.close();
            cursorModel = null;
        }
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

        cursorModel = TripDataDef.getAll(getDB(), "tripdatadef", "");
        
        if (cursorModel != null)
        {
            startManagingCursor(cursorModel);
    
            list.setAdapter(new DataAdapterWithBinder(new TripDataDefDataViewBinder(),
                                                      this, 
                                                      R.layout.row, 
                                                      cursorModel, 
                                                      new String[] {"Name", "Title", "DataType"}, 
                                                      new int[] {R.id.tddname, R.id.tddtitle, R.id.tdddatatype}));
        }
    }

    //------------------------------------------------------------------------------
    private AdapterView.OnItemClickListener onListClick  = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(final AdapterView<?> parent,
                                final View           view,
                                final int            position,
                                final long           id)
        {
            Intent i = new Intent(TripDataDefActivity.this, TripDataDefDetailActivity.class);
            i.putExtra(TripDataDefActivity.ID_EXTRA, String.valueOf(id));
            i.putExtra(TripListActivity.ID_EXTRA, String.valueOf(tripId));
            startActivity(i);
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

