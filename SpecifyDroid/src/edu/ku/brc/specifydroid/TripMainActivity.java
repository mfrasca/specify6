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

import edu.ku.brc.specifydroid.datamodel.Trip;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Nov 15, 2009
 *
 */
public class TripMainActivity extends Activity
{
    private String    tripId;
    private TextView  titleView;
    private String    baseTitle;
    /**
     * 
     */
    public TripMainActivity()
    {
        super();
    }

    /* (non-Javadoc)
     * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            tripId = savedInstanceState.getString(TripListActivity.ID_EXTRA);
        } else
        {
            tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        }

        setContentView(R.layout.tripmain);

        GridView gridview = (GridView)findViewById(R.id.tripgridview);
        gridview.setAdapter(new TripMainPanelAdapter(this, tripId));
        
        
        titleView = (TextView)findViewById(R.id.tripmaintitle);
        if (tripId != null)
        {   
            Trip trip = Trip.getById(getDB(), tripId);
            if (trip != null)
            {
                baseTitle = trip.getName();
                titleView.setText(baseTitle);
            }
        }
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
     * @see android.app.Activity#onPostResume()
     */
    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        updateTitle();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onRestart()
     */
    @Override
    protected void onRestart()
    {
        super.onRestart();
        updateTitle();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        updateTitle();
    }
    
    /**
     * 
     */
    public void updateTitle()
    {
        if (tripId != null)
        {
            String sql = String.format("select COUNT(*) AS count FROM (select TripRowIndex from tripdatacell where TripID = %s GROUP BY TripRowIndex)", tripId);
            int itemCount = SQLUtils.getCount(getDB(), sql);
            titleView.setText(String.format("%s - %d items.", baseTitle, itemCount));
        }
    }

    
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
