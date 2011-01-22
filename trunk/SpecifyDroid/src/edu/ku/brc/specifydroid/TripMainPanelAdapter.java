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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ku.brc.specifydroid.datamodel.Trip;
import edu.ku.brc.utils.DialogHelper;

/**
 * @author rods
 *
 * @code_status Beta
 *
 * Oct 27, 2009
 *
 */
public class TripMainPanelAdapter extends BaseAdapter
{
    private static final Integer[] iconIds = 
    { 
        R.drawable.mylocation, R.drawable.browsedb,
        R.drawable.camera,     R.drawable.exportdataset,
        R.drawable.googlemaps,
        R.drawable.config,     R.drawable.delete,     
    };
    
    private static final Integer[] titleIds = 
    {
        R.string.tmgmyloc, R.string.tmgbrowsedb,
        R.string.tmgcamera, R.string.tmgexportdataset,
        R.string.tmggooglemaps,
        R.string.tmgconfig, R.string.tmgdeltrip,    
    };
    
    // R.drawable.email,      
    // R.string.tmgemail,  
    
    // Data Members
    private TripMainActivity tripMainActivity;
    private String           tripId;


    /**
     * @param activity the TripMainActivity
     * @param tripId the id of the current trip
     */
    public TripMainPanelAdapter(final TripMainActivity activity,
                                final String tripId)
    {
        this.tripMainActivity = activity;
        this.tripId           = tripId;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {
        // NOTE: Tried ImageButton but disabled state doesn't make image look disabled.
        LinearLayout llCell    = new LinearLayout(tripMainActivity);
        ImageView    imageView = null; 
        TextView     titleView = null;
        if (convertView == null)
        { 
            llCell.setOrientation(LinearLayout.VERTICAL);
            
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(tripMainActivity);
            imageView.setImageResource(iconIds[position]);
            imageView.setFocusable(true);
            imageView.setPadding(8, 8, 8, 8);
            
            //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //imageView.setLayoutParams(new GridView.LayoutParams(48, 48));

            titleView = new TextView(tripMainActivity);
            titleView.setText(titleIds[position]);
            titleView.setGravity(Gravity.CENTER_HORIZONTAL);
            
            llCell.addView(imageView);
            llCell.addView(titleView);
            
        } else
        {
            llCell = (LinearLayout)convertView;
            imageView = (ImageView)llCell.getChildAt(0);
        }
        
        final int id = titleIds[position];
        try
        {
            imageView.setOnClickListener(new View.OnClickListener() 
            {
                @Override
                public void onClick(View view) 
                {
                  switch (id)
                  {
                      case R.string.tmgmyloc: // My Location Lat/Lon
                      {
                          addLatLon();
                          break;
                      } 
                      
                      case R.string.tmgbrowsedb: // Browse
                      {
                          Intent intent = new Intent(tripMainActivity, TripDataEntryDetailActivity.class);
                          intent.putExtra(TripListActivity.ID_EXTRA, tripId);
                          intent.putExtra(TripListActivity.TRIP_TYPE, TripListActivity.COLL_TRIP);
                          intent.putExtra(TripListActivity.DETAIL_CLASS, TripDataEntryDetailActivity.class.getName());
                          tripMainActivity.startActivity(intent);
                          break;
                      } 
     
                      case R.string.tmgcamera: // Camera
                      {
                          //Intent intent = new Intent(tripMainActivity, TripListActivity.class);
                          //intent.putExtra(TripListActivity.TRIP_TYPE, TripListActivity.CONFIG_TRIP);
                          //tripMainActivity.startActivity(intent);
                          
                          //TripSQLiteHelper dbHelper = new TripSQLiteHelper(tripMainActivity);
                          //dbHelper.loadTestData(getDB());
                          DialogHelper.showDialog(tripMainActivity, R.string.notimpl);
                          break;
                      }
                      
                      case R.string.tmgexportdataset: // Export as CSV
                      {
                          TripSQLiteHelper dbHelper = new TripSQLiteHelper(tripMainActivity);
                          dbHelper.exportToCSV(tripMainActivity, getDB(), tripId);
                          break;
                      }
                      
                      case R.string.tmgemail: // Email exported file.
                      {
                          //TripSQLiteHelper dbHelper = new TripSQLiteHelper(tripMainActivity);
                          //dbHelper.export(tripMainActivity, getDB(), tripId);
                          tripMainActivity.doEmailExport();
                          break;
                      }
                      
                      case R.string.tmggooglemaps: // Maps
                      {
                          Intent intent = new Intent(tripMainActivity, TripMapLocationActivity.class);
                          intent.putExtra(TripListActivity.ID_EXTRA, tripId);
                          tripMainActivity.startActivity(intent);
                          break;
                      }
                      
                      case R.string.tmgconfig: // Config
                      {
                          Intent intent = new Intent(tripMainActivity, TripDetailActivity.class);
                          intent.putExtra(TripListActivity.ID_EXTRA, tripId);
                          tripMainActivity.startActivity(intent);
                          break;
                      }
                          
                      case R.string.tmgdeltrip: // Delete Trip
                          doDeleteTrip();
                          break;
                  }
                }
              });
        } catch (Exception ex) {}
        
        /*imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                Log.d("onClick","long position ["+position+"]");
                return false;
            }
        });*/

        return llCell;
    }
    
    /**
     * 
     */
    @SuppressWarnings("unused")
    private void doEmailExport()
    {
        try
        {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "rods@ku.edu"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Your Export WB");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Your Export");
    
            tripMainActivity.startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."), 0);
            
        } catch (Exception ex)
        {
            Log.e(getClass().getName(), "Mail failed.", ex);
        }
    }
    
    /**
     * 
     */
    private void addLatLon()
    {
        if (SatelliteActivity.checkForGPS(tripMainActivity))
        {
            LocationManager locMgr = (LocationManager)tripMainActivity.getSystemService(Context.LOCATION_SERVICE);
            Location        loc    = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            
            Intent intent = new Intent(tripMainActivity, TripDataEntryDetailActivity.class);
            intent.putExtra(TripListActivity.ID_EXTRA, tripId);
            
            intent.putExtra(TripDataEntryDetailActivity.ID_ISCREATE, true);
            intent.putExtra(TripDataEntryDetailActivity.LAT_VAL, loc != null ? loc.getLatitude()  :  38.958654); // for debugging
            intent.putExtra(TripDataEntryDetailActivity.LON_VAL, loc != null ? loc.getLongitude() : -95.243829);
            tripMainActivity.startActivity(intent);
        }
    }

    
    /**
     * 
     */
    private void doDeleteTrip()
    {
        String sql      = String.format("SELECT Name FROM trip WHERE _id = %s", tripId);
        String tripName = SQLUtils.getStringObj(getDB(), sql);
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(tripMainActivity)
        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (Trip.doDeleteTrip(getDB(), tripId))
                {
                    tripMainActivity.finish();   
                }
            }
            })
        .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
            });
        builder.setTitle(String.format(tripMainActivity.getString(R.string.deletetrip), tripName));
        AlertDialog alert = builder.create();
        alert.show();
        
        closeDB();
    }
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount()
    {
        return iconIds.length;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(final int position)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    
    //------------------------------------------------------------------------
    //-- Database Access
    //------------------------------------------------------------------------
    private TripSQLiteHelper  tripDBHelper = null;
    
    protected void closeDB()
    {
        if (tripDBHelper != null)
        {
            Log.d(getClass().getName(), "close()");
            tripDBHelper.close();
            tripDBHelper = null;
        }
    }
    
    private SQLiteDatabase getDB()
    {
        Log.d(getClass().getName(), "getDB");
        if (tripDBHelper == null)
        {
            tripDBHelper = new TripSQLiteHelper(tripMainActivity.getApplicationContext());
        }
        return tripDBHelper.getWritableDatabase();
    }
}

