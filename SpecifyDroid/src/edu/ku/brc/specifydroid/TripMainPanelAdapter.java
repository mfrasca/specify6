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

import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import edu.ku.brc.specifydroid.datamodel.TripDataDef;
import edu.ku.brc.specifydroid.datamodel.TripDataDef.TripDataDefType;

/**
 * @author rods
 *
 * @code_status Alpha
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
        R.drawable.email,      R.drawable.googlemaps,
        R.drawable.configstd,  R.drawable.config, 
        R.drawable.delete,     
    };
    
    private static final Integer[] titleIds = 
    {
        R.string.tmgmyloc, R.string.tmgbrowsedb,
        R.string.tmgcamera, R.string.tmgexportdataset,
        R.string.tmgemail,  R.string.tmggooglemaps,
        R.string.tmgconfig, R.string.tmgconfigstd,
        R.string.tmgdeltrip,    
    };
    
    // Data Members
    private TripMainActivity tripMainActivity;
    private String           tripId;


    /**
     * @param activity
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
        LinearLayout llCell    = new LinearLayout(tripMainActivity);
        ImageView    imageView = null; 
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

            
            TextView titleView = new TextView(tripMainActivity);
            titleView.setText(titleIds[position]);
            titleView.setGravity(Gravity.CENTER_HORIZONTAL);
            
            llCell.addView(imageView);
            llCell.addView(titleView);
            
        } else
        {
            llCell = (LinearLayout)convertView;
            imageView = (ImageView)llCell.getChildAt(0);
        }
        
        try
        {
            imageView.setOnClickListener(new View.OnClickListener() 
            {
                @Override
                public void onClick(View view) 
                {
                  //Log.d("onClick","position ["+position+"]");
                  
                  switch (position)
                  {
                      case 0: // My Location Lat/Lon
                      {
                          addLatLon();
                          break;
                      } 
                      
                      case 1: // Browse
                      {
                          Intent intent = new Intent(tripMainActivity, TripDataEntryDetailActivity.class);
                          intent.putExtra(TripListActivity.ID_EXTRA, tripId);
                          intent.putExtra(TripListActivity.TRIP_TYPE, TripListActivity.COLL_TRIP);
                          intent.putExtra(TripListActivity.DETAIL_CLASS, TripDataEntryDetailActivity.class.getName());
                          tripMainActivity.startActivity(intent);
                          break;
                      } 
     
                      case 2: // Camera
                      {
                          //Intent intent = new Intent(tripMainActivity, TripListActivity.class);
                          //intent.putExtra(TripListActivity.TRIP_TYPE, TripListActivity.CONFIG_TRIP);
                          //tripMainActivity.startActivity(intent);
                          TripSQLiteHelper dbHelper = new TripSQLiteHelper(tripMainActivity);
                          dbHelper.loadTestData(SpecifyActivity.getDatabase());
                          break;
                      }
                      
                      case 3: // Export as CSV
                      {
                          TripSQLiteHelper dbHelper = new TripSQLiteHelper(tripMainActivity);
                          dbHelper.export(SpecifyActivity.getDatabase(), tripId);
                          break;
                      }
                      
                      case 5: // Maps
                      {
                          Intent intent = new Intent(tripMainActivity, TripMapLocationActivity.class);
                          intent.putExtra(TripListActivity.ID_EXTRA, tripId);
                          tripMainActivity.startActivity(intent);
                          break;
                      }
                      
                      case 6: // Config
                      {
                          Intent intent = new Intent(tripMainActivity, TripDetailActivity.class);
                          intent.putExtra(TripListActivity.ID_EXTRA, tripId);
                          tripMainActivity.startActivity(intent);
                          break;
                      }
                          
                      case 7: // Config Std
                          doStdConfig();
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
    private void addLatLon()
    {
        LocationManager locMgr = (LocationManager)tripMainActivity.getSystemService(Context.LOCATION_SERVICE);
        Location        loc    = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        
        Intent intent = new Intent(tripMainActivity, TripDataEntryDetailActivity.class);
        intent.putExtra(TripListActivity.ID_EXTRA, tripId);
        
        intent.putExtra(TripDataEntryDetailActivity.ID_ISCREATE, true);
        intent.putExtra(TripDataEntryDetailActivity.LAT_VAL, loc != null ? loc.getLatitude()  : 38.9716689); // for debugging
        intent.putExtra(TripDataEntryDetailActivity.LON_VAL, loc != null ? loc.getLongitude() : -95.2352501);
        tripMainActivity.startActivity(intent);
    }
    
    /**
     * 
     */
    private void doStdConfig()
    {
        final Vector<TripDataDef> tripDataDefs = new Vector<TripDataDef>();
        if (!readStdFieldsXML(tripDataDefs))
        {
            return;
        }
        
        final CharSequence[] items  = new CharSequence[tripDataDefs.size()];
        final boolean[]      values = new boolean[tripDataDefs.size()];
        
        int i = 0;
        for (TripDataDef tdd : tripDataDefs)
        {
            items[i]  = tdd.getTitle();
            values[i] = true;
            i++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(tripMainActivity)
            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    doStdPopulate(tripDataDefs, values);
                }
                })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
                });
        builder.setTitle("Choose Fields");
        builder.setMultiChoiceItems(items, values, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dlg, int which, boolean isChecked)
            {
                values[which] = isChecked;
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 
     */
    private void doStdPopulate(final Vector<TripDataDef> tripDataDefs, final boolean[] selected)
    {
        String sql      = String.format("SELECT ColumnIndex FROM tripdatadef WHERE TripID = %s ORDER BY ColumnIndex DESC LIMIT 1", tripId);
        int    colIndex = SQLUtils.getCount(SpecifyActivity.getDatabase(), sql);
        int    trpId    = Integer.parseInt(tripId);
        
        int i = 0;
        for (TripDataDef tdd : tripDataDefs)
        {
            if (selected[i])
            {
                sql = String.format("SELECT COUNT(*) AS count FROM tripdatadef WHERE TripID = %s AND Name = '%s'", tripId, tdd.getName());
                if (SQLUtils.getCount(SpecifyActivity.getDatabase(), sql) < 1)
                {
                    colIndex++;
                    
                    tdd.setTripID(trpId);
                    tdd.setColumnIndex(colIndex);
                    
                    long _id = tdd.insert(SpecifyActivity.getDatabase());
                    
                    Log.d("POPULATE", "_id: ["+_id+"] trpId: " + tdd.getTripID() +"  colIndex["+tdd.getColumnIndex()+"] Name: "+ tdd.getName() + "  Type: "+tdd.getDataType());
                }
            }
            i++;
        }
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
    
    private boolean readStdFieldsXML(final Vector<TripDataDef> tripDataDefs)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder docBldr = dbf.newDocumentBuilder();
            InputStream     fis     = tripMainActivity.getAssets().open("stdfields.xml");
            Document        doc     = docBldr.parse(fis);

            Element topElement = doc.getDocumentElement();

            NodeList tddElements = topElement.getElementsByTagName("field");

            for (int elementIndex = 0; elementIndex < tddElements.getLength(); elementIndex++)
            {
                Element tddEle = (Element) tddElements.item(elementIndex);
                TripDataDef tdd = new TripDataDef();

                if (tddEle.hasAttributes())
                {
                    NamedNodeMap attributes = tddEle.getAttributes();
                    String title = attributes.getNamedItem("title").getNodeValue();
                    String name  = attributes.getNamedItem("name").getNodeValue();
                    String type  = attributes.getNamedItem("type").getNodeValue();
                    tdd.setTitle(title);
                    tdd.setName(name);
                    tdd.setDataType((short)TripDataDefType.valueOf(type).ordinal());
                }
                tripDataDefs.add(tdd);
            }
            
            return true;
            
        } catch (Exception e)
        {
            Log.e("xml_perf", "DOM parser failed", e);
        }
        
        return false;
    }
}

