package edu.ku.brc.specifydroid;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.Window;

import com.google.android.maps.MapActivity;
import com.pocketjourney.view.TransparentPanel;

public class TripMapLocationActivity extends MapActivity {

    //private String tripId;

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
        
        //tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        
        Paint innerPaint = new Paint();
        innerPaint.setARGB(0, 255, 255, 255); //gray
        innerPaint.setAntiAlias(true);

        TransparentPanel transPanel = (TransparentPanel)findViewById(R.id.transparent_panel);
        transPanel.setInnerPaint(innerPaint);
        transPanel.setBorderPaint(innerPaint);
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
