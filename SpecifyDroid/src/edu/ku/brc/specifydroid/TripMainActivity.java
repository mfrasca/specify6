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
import java.util.concurrent.atomic.AtomicLong;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import edu.ku.brc.specifydroid.datamodel.Trip;
import edu.ku.brc.utils.DialogHelper;
import edu.ku.brc.utils.SQLUtils;

/**
 * @author rods
 *
 * @code_status Beta
 *
 * Nov 15, 2009
 *
 */
public class TripMainActivity extends SpBaseActivity
{
    private static final String ID_PNTWASCPT = "ID_PNTWASCPT";
    
    private String    tripId;
    private TextView  titleView;
    private String    baseTitle;
    private int       itemCount = 0;
    
    //private GpsStatus            gpsStatus = null;
    private LocationManager      locMgr           = null;
    private Location             loc              = null;
    private AtomicBoolean        pointWasCaptured = new AtomicBoolean(false);
    private AtomicLong           milliseconds     = new AtomicLong(0); 
    private TripMainPanelAdapter adapter ;

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
            pointWasCaptured = new AtomicBoolean(savedInstanceState.getBoolean(ID_PNTWASCPT));
        } else
        {
            tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
            pointWasCaptured = new AtomicBoolean(getIntent().getBooleanExtra(ID_PNTWASCPT, false));
        }

        setContentView(R.layout.tripmain);

        adapter = new TripMainPanelAdapter(this, tripId);
        GridView gridview = (GridView)findViewById(R.id.tripgridview);
        gridview.setAdapter(adapter);
        
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
        updateTitle();
     }
    
    /**
     * @return the itemCount
     */
    public int getItemCount()
    {
        return itemCount;
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
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        
        outState.putString(TripListActivity.ID_EXTRA, tripId);
        outState.putBoolean(ID_PNTWASCPT, pointWasCaptured.get());
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        updateTitle();
        
        resetLocationManager();
    }
    
    /**
     * 
     */
    private void resetLocationManager()
    {
        if (pointWasCaptured.get())
        {
            if (locMgr != null && onLocationChange != null)
            {
                locMgr.removeUpdates(onLocationChange);
            }
            locMgr = null;
            loc    = null;
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specifydroid.SpBaseActivity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        resetLocationManager();
    }

    /**
     * 
     */
    public void updateTitle()
    {
        if (tripId != null)
        {
            String sql = String.format("select COUNT(*) AS count FROM (select TripRowIndex from tripdatacell where TripID = %s GROUP BY TripRowIndex)", tripId);
            itemCount = SQLUtils.getCount(getDB(), sql);
            
            sql = String.format("SELECT Name FROM trip WHERE _id = %s", tripId);
            String titleStr = SQLUtils.getStringObj(getDB(), sql);
            if (titleStr != null)
            {
                baseTitle = titleStr;
            }
            
            if (titleView != null)
            {
                titleView.setText(String.format("%s %s", baseTitle, getString(R.string.tmgtitleitems, itemCount)));
            }
            
            adapter.setEnabled(itemCount != 0);
        }
    }
    
    /**
     * 
     */
    protected void addLatLon()
    {
        if (SatelliteActivity.checkForGPS(this))
        {
            if (locMgr == null)
            {
                milliseconds.set(System.currentTimeMillis());
                
                locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                
                //locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10.0f, onLocationChange);
    
                // Listener for GPS Status...
    
                final Listener onGpsStatusChange = new GpsStatus.Listener()
                {
                    public void onGpsStatusChanged(int event)
                    {
                        switch (event)
                        {
                            case GpsStatus.GPS_EVENT_STARTED:
                                Log.v("TEST", "GPS_EVENT_STARTED");
                                // Started...
                                break;
    
                            case GpsStatus.GPS_EVENT_FIRST_FIX:
                                Log.v("TEST", "GPS_EVENT_FIRST_FIX");
                                // First Fix...
                                break;
    
                            case GpsStatus.GPS_EVENT_STOPPED:
                                Log.v("TEST", "GPS_EVENT_STOPPED");
                                // Stopped...
                                break;
    
                            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                                Log.v("TEST", "GPS_EVENT_SATELLITE_STATUS");
                                doCapturePoint();
                                break;
                        }
                    }
                };
                locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10.0f, onLocationChange);
                locMgr.addGpsStatusListener(onGpsStatusChange);
            }
        }
    }
    
    /**
     * 
     */
    private void doCapturePoint()
    {
        if (loc == null)
        {
            /*gpsStatus = locMgr.getGpsStatus(gpsStatus);
            int maxSats = gpsStatus.getMaxSatellites();
            Log.i("DBG", "Max Stats: "+ maxSats);
            
            Iterable<GpsSatellite> iSatellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite> it = iSatellites.iterator();
            int cnt = 0;
            while (it.hasNext())
            {
                it.next();
                cnt++;
            }
            Log.d("DBG", "Num Stats: "+ gpsStatus.getTimeToFirstFix()+"  "+cnt);*/
            
            if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            
            if (loc != null)
            {
                resetLocationManager();
                
                Intent intent = new Intent(this, TripDataEntryDetailActivity.class);
                intent.putExtra(TripListActivity.ID_EXTRA, tripId);
                
                Log.i("DBG", String.format("%8.5f, %8.5f", loc != null ? loc.getLatitude() : 0.0f, loc != null ? loc.getLongitude() : 0.0f));
                
                intent.putExtra(TripDataEntryDetailActivity.ID_ISCREATE, true);
                intent.putExtra(TripDataEntryDetailActivity.LAT_VAL, loc != null ? loc.getLatitude()  :  38.958654); // for debugging
                intent.putExtra(TripDataEntryDetailActivity.LON_VAL, loc != null ? loc.getLongitude() : -95.243829);
                pointWasCaptured.set(true);
                startActivity(intent);
                
            } else
            {
                long delta = (System.currentTimeMillis() - milliseconds.get()) / 1000;
                if (delta > 15)
                {
                    resetLocationManager();
                    DialogHelper.showDialog(this, "Timed out.");
                }
            }
        }
    }

    LocationListener onLocationChange = new LocationListener() {
        public void onLocationChanged(Location location) 
        {
            // required for interface, not used
            Log.v("TEST", "onLocationChanged");
        }
        
        public void onProviderDisabled(String provider) 
        {
            // required for interface, not used
            Log.v("TEST", "onProviderDisabled");
        }
        
        public void onProviderEnabled(String provider) 
        {
            // required for interface, not used
            Log.v("TEST", "onProviderEnabled");
        }
        
        public void onStatusChanged(String provider, int status, Bundle extras) 
        {
            // required for interface, not used
            Log.v("TEST", "onStatusChanged");
        }
    };

}
