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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import edu.ku.brc.specifydroid.datamodel.Trip;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Nov 15, 2009
 *
 */
public class TripMainActivity extends SpBaseActivity
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
            closeDB();
        }
    }
    

    /**
     * 
     */
    public void doEmailExport()
    {
        try
        {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "rods@ku.edu"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Your Export WB");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Your Export");
    
            Log.d("XXX", "About to send email");
            startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."), 0);
            
        } catch (Exception ex)
        {
            Log.e(getClass().getName(), "Mail failed.", ex);
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("XXX", "About to send email");
        
        super.onActivityResult(requestCode, resultCode, data);
        
        Log.d("XXX", "CODE: "+requestCode);
        
        if (requestCode == 0)
        {
            Log.d("XXX", "CODE: "+requestCode);
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
    
    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause()
    {
        Log.d("XXX", "onPause");
        super.onPause();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        Log.d("XXX", "onDestroy");
        super.onDestroy();
        closeDB();
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
            if (titleView != null)
            {
                titleView.setText(String.format("%s - %d items.", baseTitle, itemCount));
            }
            closeDB();
        }
    }
}
