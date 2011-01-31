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

import java.util.HashMap;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ku.brc.utils.DialogHelper;

/**
 * @author rods
 *
 * @code_status Beta
 *
 * Oct 27, 2009
 *
 */
public class SpecifyMainPanelAdapter extends BaseAdapter
{
    // references to our images
    private static Integer[] thumbs = 
    { 
        R.drawable.satellite48, R.drawable.compass48, R.drawable.collect48,
        R.drawable.hikerbig48, R.drawable.look48,
    };
    
    private static Integer[] titleIds = 
    { 
        R.string.satellites, R.string.compass, R.string.collecting,
        R.string.trips, R.string.observations,
    };
    
    private SpecifyActivity specifyActivity;
    private HashMap<View, Integer> viewToIndex = new HashMap<View, Integer>();

    /**
     * @param activity
     */
    public SpecifyMainPanelAdapter(final SpecifyActivity activity)
    {
        this.specifyActivity = activity;
    }
    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount()
    {
        return thumbs.length;
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

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {
        ImageView    imageView;
        LinearLayout llCell;
        TextView     textView;
        
        if (convertView == null)
        { 
            llCell    = new LinearLayout(specifyActivity);
            llCell.setOrientation(LinearLayout.VERTICAL);
            
            imageView = new ImageView(specifyActivity);
            imageView.setFocusable(true);
            imageView.setPadding(8, 8, 8, 8);
            
            textView = new TextView(specifyActivity);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(R.color.black);
            
            llCell.addView(imageView);
            llCell.addView(textView);
            
        } else
        {
            llCell    = (LinearLayout)convertView;
            imageView = (ImageView)llCell.getChildAt(0);
            textView  = (TextView)llCell.getChildAt(1);
        }
        
        imageView.setOnClickListener(new ClickedViewListener(position));
        textView.setText(titleIds[position]);
        imageView.setImageResource(thumbs[position]);
        
        return llCell;
    }

    /*protected void sendEmail()
    {

        // Setup the recipient in a String array

        String[] mailto = { emailTo.getText().toString() };

        // Create a new Intent to send messages

        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        // Add attributes to the intent

        sendIntent.putExtra(Intent.EXTRA_EMAIL, mailto);

        sendIntent.putExtra(Intent.EXTRA_SUBJECT,

        emailSubject.getText().toString());

        sendIntent.putExtra(Intent.EXTRA_TEXT,

        emailBody.getText().toString());

        sendIntent.setType("text/plain");

        startActivity(Intent.createChooser(sendIntent, "MySendMail"));

    }*/
    
    class ClickedViewListener implements View.OnClickListener
    {
        private int inx;

        /**
         * @param inx
         */
        public ClickedViewListener(final int inx)
        {
            this.inx = inx;
        }

        @Override
        public void onClick(View view)
        {
            switch (inx)
            {
                case 0:
                    specifyActivity.startActivity(new Intent(specifyActivity,
                            SatelliteActivity.class));
                    break;

                case 1: // Compass
                    DialogHelper.showDialog(specifyActivity, R.string.notimpl);
                    break;

                case 2: // Collecting
                {
                    Intent intent = new Intent(specifyActivity, TripListActivity.class);
                    intent.putExtra(TripListActivity.TRIP_TYPE, TripListActivity.COLL_TRIP);
                    intent.putExtra(TripListActivity.DETAIL_CLASS,
                            TripDataEntryDetailActivity.class.getName());
                    specifyActivity.startActivity(intent);
                    break;
                }

                case 3: // Configure Trips
                {
                    Intent intent = new Intent(specifyActivity, TripListActivity.class);
                    intent.putExtra(TripListActivity.TRIP_TYPE, TripListActivity.CONFIG_TRIP);
                    specifyActivity.startActivity(intent);
                    break;
                }

                case 4: // Observations
                {
                    Intent intent = new Intent(specifyActivity, TripListActivity.class);
                    intent.putExtra(TripListActivity.TRIP_TYPE, TripListActivity.OBS_TRIP);
                    intent.putExtra(TripListActivity.DETAIL_CLASS,
                            TripDataEntryDetailActivity.class.getName());
                    specifyActivity.startActivity(intent);
                    break;
                }
            }
        }
    }
}

