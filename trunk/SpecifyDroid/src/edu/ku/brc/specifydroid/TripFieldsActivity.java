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

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import edu.ku.brc.specifydroid.datamodel.TripDataDef;

/**
 * @author rods
 *
 * @code_status Beta
 *
 * Jan 15, 2011
 *
 */
public class TripFieldsActivity extends SpBaseActivity
{
    public final static String ID_EXTRA = "edu.ku.brc.specifydroid._ID";
    
    private ListView       list            = null;
    private String         tripId          = null;
    
    private ImageView      addBtn;
    private Button         closeBtn;
    //private boolean        hasChanged      = false;
    
    /**
     * 
     */
    public TripFieldsActivity()
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
            tripId = savedInstanceState.getString(TripDetailActivity.ID_EXTRA);
        } else
        {
            tripId = getIntent().getStringExtra(TripDetailActivity.ID_EXTRA);
        }
        
        setContentView(R.layout.fields_form);
        
        list = (ListView)findViewById(R.id.tripdatadeflist);
        list.setOnItemClickListener(onListClick);
        
        OnCreateContextMenuListener listener = new OnCreateContextMenuListener()
        {
            @Override
            public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenuInfo menuInfo)
            {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
                menu.setHeaderTitle(getNameFromCursor(list.getItemAtPosition(info.position)));

                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.fieldscontextmenu, menu);
            }
        };
        list.setOnCreateContextMenuListener(listener);
        
        addBtn = (ImageView) findViewById(R.id.addttd);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                addTripDataDef();
            }
        });

        closeBtn = (Button) findViewById(R.id.closefields);
        closeBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });

        initList();

        updateUIState();
    }
    
    /**
     * @param cursor
     * @return
     */
    private String getNameFromCursor(final Object obj)
    {
        String tripName = "Unknown";
        if (obj instanceof Cursor)
        {
            tripName = ((Cursor)obj).getString(2);
        }
        return tripName;
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onContextItemSelected(final MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;
        
        if (item.getItemId() == R.id.delfielditem)
        {
            doDeleteField(index);
            
        } else if (item.getItemId() == R.id.editttditem)
        {
            
        }
        return true;
    }
    
    /**
     * @param index
     */
    private void doDeleteField(final int index)
    {
        String trpDefId = ((Cursor)list.getItemAtPosition(index)).getString(0); 
        closeCursor();
        
        TripDataDef.doDelete(getDB(), tripId, trpDefId);
        
        initList();
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onStart()
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        
        if (cursorModel == null)
        {
            initList();
        }
    }
    
    
    /**
     * 
     */
    private void initList()
    {
        closeCursor();

        String where = "WHERE TripId = " + tripId;
        cursorModel = TripDataDef.getAll(getDB(), "tripdatadef", where, "ColumnIndex");
        
        if (cursorModel != null)
        {
            startManagingCursor(cursorModel);
    
            list.setAdapter(new DataAdapterWithBinder(new TripDataDefDataViewBinder(),
                                          this, 
                                          R.layout.tdd_row, 
                                          cursorModel, 
                                          new String[] {"Name", "Title", "DataType"}, 
                                          new int[] {R.id.tddrwname, R.id.tddrwtitle, R.id.tddrwdatatype}));
        }
    }

    /**
     * 
     */
    private void addTripDataDef()
    {
        Intent intent = new Intent(this, TripDataDefDetailActivity.class);
        intent.putExtra(ID_EXTRA, String.valueOf(tripId));
        startActivity(intent);
    }
    
    /**
     * 
     */
    private void updateUIState()
    {
        //closeBtn.setEnabled(hasChanged);
    }
    
    //------------------------------------------------------------------------------
    private AdapterView.OnItemClickListener onListClick  = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(final AdapterView<?> parent,
                                final View           view,
                                final int            position,
                                final long           id)
        {
            Intent i = new Intent(TripFieldsActivity.this, TripDataDefDetailActivity.class);
            i.putExtra(TripDataDefActivity.ID_EXTRA, String.valueOf(id));
            i.putExtra(TripListActivity.ID_EXTRA,    String.valueOf(tripId));
            startActivity(i);
        }
    };
}
