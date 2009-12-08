package edu.ku.brc.specifydroid;

import java.util.HashMap;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.OverlayItem;
import com.pocketjourney.view.MapLocationViewer;

public class TripMapLocationActivity extends MapActivity {

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
        
        setContentView(R.layout.maplocviewer);
        
        tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        
        GeoPoint firstPnt = null;

        String sql = String.format("SELECT tc._id, tc.Data, tc.TripRowIndex, td.Name FROM tripdatacell tc INNER JOIN tripdatadef td ON tc.TripDataDefID = td._id WHERE tc.TripID = %s ORDER BY tc.TripRowIndex, td.ColumnIndex", tripId);
        Cursor  cursor = SpecifyActivity.getDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            HashMap<String, String> dataHash = new HashMap<String, String>();
            
            Integer rowIndex = null;
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
                    //itemizedOverlay.addOverlay(overlayitem); 
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
        
        /*mapOverlays.add(itemizedOverlay);
        
        if (firstPnt != null)
        {
            MapController mc = mapView.getController();
            mc.animateTo(firstPnt);
            mc.setZoom(12); 
        }*/
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
