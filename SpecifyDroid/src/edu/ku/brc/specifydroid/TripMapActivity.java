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

import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ZoomButtonsController;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Nov 16, 2009
 *
 */
public class TripMapActivity extends MapActivity
{
    //private LinearLayout zoomLayout;
    private MapView      mapView;
    
    private List<Overlay> mapOverlays;
    private Drawable drawable;
    private TripItemizedOverlay itemizedOverlay;
    
    private String tripId;
    

    /* (non-Javadoc)
     * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapview);

        // Get the map view from resource file
        //zoomLayout = (LinearLayout) findViewById(R.id.zoom_view);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true); 
        mapView.displayZoomControls(true);
        ZoomButtonsController zbc = mapView.getZoomButtonsController();
        zbc.setAutoDismissed(false);
        zbc.getZoomControls().setVisibility(View.VISIBLE);
        zbc.setZoomInEnabled(true);
        zbc.setZoomOutEnabled(true);
        
        /*zoomControls = (ZoomControls) findViewById(R.id.zoomcontrols);
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getController().zoomIn();
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getController().zoomOut();
            }
        });*/

        //mapView.setBuiltInZoomControls(true);
        //mZoom        = (ZoomControls) 
        //zoomLayout.addView(mZoom);
        //mZoom.displ
        
        //View zoomView = mapView.getZoomControls(); 
        //zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 
        //zoomView.displayZoomControls(true);

        
        mapOverlays     = mapView.getOverlays();
        drawable        = this.getResources().getDrawable(R.drawable.marker);
        itemizedOverlay = new TripItemizedOverlay(drawable);
        
        tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        //String sql = String.format()"SELECT Data FROM tripdatacell WHERE TripID = %s ORDER BY " +
        
        GeoPoint firstPnt = null;

        String sql = String.format("SELECT tc._id, tc.Data, tc.TripRowIndex, td.Name FROM tripdatacell tc INNER JOIN tripdatadef td ON tc.TripDataDefID = td._id WHERE tc.TripID = %s ORDER BY tc.TripRowIndex, td.ColumnIndex", tripId);
        Cursor  cursor = SpecifyActivity.getDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            HashMap<String, String> dataHash = new HashMap<String, String>();
            
            Integer rowIndex = null;
            //int idInx = cursor.getColumnIndex("_id");
            int dInx  = cursor.getColumnIndex("Data");
            int rInx  = cursor.getColumnIndex("TripRowIndex");
            int nInx  = cursor.getColumnIndex("Name");
            do
            {
                int rowNum = cursor.getInt(rInx);
                if (rowIndex == null)
                {
                    rowIndex = rowNum;
                    
                } 
                    
                if (rowIndex != rowNum)
                {
                    String name = dataHash.get("LocalityName");
                    int    lat  = (int)(Double.parseDouble(dataHash.get("Latitude")) * 1000000.0);
                    int    lon  = (int)(Double.parseDouble(dataHash.get("Longitude")) * 1000000.0);
                    
                    Log.d("debug", lat+ " = "+lon);
                    GeoPoint    point       = new GeoPoint(lat,lon);
                    OverlayItem overlayitem = new OverlayItem(point, name, name);
                    itemizedOverlay.addOverlay(overlayitem); 
                    dataHash.clear();
                    rowIndex = rowNum;
                    
                    if (firstPnt == null)
                    {
                        firstPnt = point;
                    }
                } 
                
                Log.d("debug", cursor.getString(nInx)+ " = "+cursor.getString(dInx));
                
                dataHash.put(cursor.getString(nInx), cursor.getString(dInx));
                
                
            } while (cursor.moveToNext());
            cursor.close();
        }
        
        mapOverlays.add(itemizedOverlay);
        
        if (firstPnt != null)
        {
            MapController mc = mapView.getController();
            mc.animateTo(firstPnt);
            mc.setZoom(12); 
        }
    }

    /* (non-Javadoc)
     * @see com.google.android.maps.MapActivity#isRouteDisplayed()
     */
    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }
}
