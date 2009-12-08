package com.pocketjourney.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ZoomButtonsController;

import com.google.android.maps.MapView;

/**
 * @author Anthony (Acopernicus)
 *
 * @code_status Alpha
 *
 * Nov 20, 2009
 *
 */
public class MapLocationViewer extends LinearLayout {

	protected MapLocationOverlay overlay;
	
    //  Known latitude/longitude coordinates that we'll be using.
	protected List<MapLocation> mapLocations;
    
	protected MapView mapView;
    
	/**
	 * @param context
	 * @param attrs
	 */
	public MapLocationViewer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MapLocationViewer(Context context) {
		super(context);
		init();
	}

	/**
	 * 
	 */
	public void init() {		

		setOrientation(VERTICAL);
		setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

		mapView = new MapView(getContext(),"0JCMEdmz3F_iL-i9ECTE39BKUmtA5_GMY2kSVKQ");
		mapView.setEnabled(true);
		mapView.setClickable(true);
		addView(mapView);
		
        mapView.setBuiltInZoomControls(true); 
        mapView.displayZoomControls(true);
        ZoomButtonsController zbc = mapView.getZoomButtonsController();
        zbc.setAutoDismissed(false);

		overlay = new MapLocationOverlay(this);
		mapView.getOverlays().add(overlay);

    	mapView.getController().setZoom(14);
    	
    	if (getMapLocations().size() > 0)
    	{
    	    mapView.getController().setCenter(getMapLocations().get(0).getPoint());
    	}
	}
	
	public List<MapLocation> getMapLocations() {
		if (mapLocations == null) {
			mapLocations = new ArrayList<MapLocation>();
			//mapLocations.add(new MapLocation("North Beach", "", 37.799800872802734,-122.40699768066406));
			//mapLocations.add(new MapLocation("China Town",37.792598724365234,-122.40599822998047));
			//mapLocations.add(new MapLocation("Fisherman's Wharf",37.80910110473633,-122.41600036621094));
			//mapLocations.add(new MapLocation("Financial District",37.79410171508789,-122.4010009765625));
		}
		return mapLocations;
	}

	public MapView getMapView() {
		return mapView;
	}
}
