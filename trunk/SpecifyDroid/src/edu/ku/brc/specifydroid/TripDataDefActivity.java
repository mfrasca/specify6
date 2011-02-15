/* Copyright (C) 2011, University of Kansas Center for Research
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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.ku.brc.specifydroid.datamodel.TripDataDef;

/**
 * @author rods
 *
 * @code_status Beta
 *
 * Nov 10, 2009
 *
 */
public class TripDataDefActivity extends SpBaseActivity
{
    public final static String ID_EXTRA = "edu.ku.brc.specifydroid._TripDataDefID";
    
    private AtomicBoolean              isActive = new AtomicBoolean(true);
    private ListView                   list        = null;
    private String                     tripId      = null;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.trip_list_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

        if (savedInstanceState != null)
        {
            tripId = savedInstanceState.getString(TripListActivity.ID_EXTRA);
        } else
        {
            tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        }

        list = (ListView) findViewById(R.id.trips); // use the same layout
        list.setOnItemClickListener(onListClick);
        //list.getBackground().setDither(true);

        initList();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        
        outState.putString(TripListActivity.ID_EXTRA, tripId);
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

    /**
     * 
     */
    private void initList()
    {
        closeCursor();

        cursorModel = TripDataDef.getAll(getDB(), "tripdatadef", "");
        
        if (cursorModel != null)
        {
            startManagingCursor(cursorModel);
    
            list.setAdapter(new DataAdapterWithBinder(new TripDataDefDataViewBinder(list, 0, 3),
                                                      this, 
                                                      R.layout.row, 
                                                      cursorModel, 
                                                      new String[] {"Title", "Name", "DataType"}, 
                                                      new int[] {R.id.tddrwtitle, R.id.tddrwname, R.id.tddrwdatatype}));
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
}

