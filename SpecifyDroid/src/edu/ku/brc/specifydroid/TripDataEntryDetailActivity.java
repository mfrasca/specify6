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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import edu.ku.brc.specifydroid.datamodel.TripDataCell;
import edu.ku.brc.specifydroid.datamodel.TripDataDef;

/**
 * @author rods
 *
 * @code_status Beta
 *
 * Nov 11, 2009
 *
 */
public class TripDataEntryDetailActivity extends SpBaseActivity
{
    public final static String ID_EXTRA    = "edu.ku.brc.specifydroid._TripDataDefID";
    public final static String ID_ISCREATE = "edu.ku.brc.specifydroid._ISNEW";
    public final static String LAT_VAL     = "edu.ku.brc.specifydroid._LAT_VAL";
    public final static String LON_VAL     = "edu.ku.brc.specifydroid._LON_VAL";
    public final static String TITLE       = "edu.ku.brc.specifydroid.TITLE";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private AtomicBoolean              isActive = new AtomicBoolean(true);
    private String                     tripId      = null;
    private boolean                    isNewRec    = false;
    private boolean                    isCreateRec = false;
    private boolean                    isChanged   = false;
    private int                        numDataColumns = 0;
    
    private TripDataCell               tripDataCell = new TripDataCell();
    
    private ImageButton                posFirstBtn;
    private ImageButton                posLastBtn;
    private ImageButton                posPrevBtn;
    private ImageButton                posNextBtn;
    private ImageButton                addBtn;
    private Button                     saveBtn;
    private TextView                   recLabel;
    
    private Integer                    rowIndex    = null;
    private Integer                    numRows     = null;
    
    private HashMap<View, Integer>     recNumHash  = new HashMap<View, Integer>();
    private HashMap<Integer, String>   valueHash   = new HashMap<Integer, String>();
    private HashMap<View, Boolean>     changedHash = new HashMap<View, Boolean>();
    private HashMap<View, Integer>     viewToTTDId = new HashMap<View, Integer>();
    private HashMap<String, View>      compHash    = new HashMap<String, View>();
    private HashMap<String, Integer >  compTDD     = new HashMap<String, Integer>();
    private ArrayList<View>            comps       = new ArrayList<View>();
    
    /**
     * 
     */
    public TripDataEntryDetailActivity()
    {
        super();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tdc_detail_form);
        
        if (savedInstanceState != null)
        {
            tripId      = savedInstanceState.getString(TripListActivity.ID_EXTRA);
            isCreateRec = savedInstanceState.getBoolean(ID_ISCREATE, false);
        } else
        {
            tripId      = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
            isCreateRec = getIntent().getBooleanExtra(ID_ISCREATE, false);
        }
        
        
        isNewRec    = isCreateRec;
        
        String sql = String.format("SELECT TripRowIndex AS count FROM tripdatacell WHERE TripID = %s ORDER BY TripRowIndex DESC LIMIT 1", tripId);
        numRows = SQLUtils.getCount(getDB(), sql);
        numRows++;
        
        saveBtn = (Button) findViewById(R.id.tdcsave);
        saveBtn.setOnClickListener(onSave);
        saveBtn.setEnabled(false);

        addBtn = (ImageButton) findViewById(R.id.tdcadd);
        addBtn.setOnClickListener(onAdd);
        
        if (!isCreateRec)
        {
            posFirstBtn = (ImageButton)findViewById(R.id.posfirst);
            posLastBtn  = (ImageButton)findViewById(R.id.poslast);
            posNextBtn  = (ImageButton)findViewById(R.id.posnext);
            posPrevBtn  = (ImageButton)findViewById(R.id.posprev);
            recLabel    = (TextView)findViewById(R.id.reclabel);
            
            posFirstBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    adjustIndex(Integer.MIN_VALUE);
                }
            });
    
            posPrevBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    adjustIndex(-1);
                }
            });
    
            posNextBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    adjustIndex(1);
                }
            });
    
            posLastBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    adjustIndex(Integer.MAX_VALUE);
                }
            });
            
        } else
        {
            LinearLayout recController = (LinearLayout)findViewById(R.id.reccontroller);
            recController.setVisibility(View.INVISIBLE);
            addBtn.setVisibility(View.INVISIBLE);
            
            saveBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (isChanged)
                    {
                        doSave();
                    }
                    finish();
                }
            });
        }

        buildUI();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        
        outState.putString(tripId, TripListActivity.ID_EXTRA);
        outState.putBoolean(ID_ISCREATE, isCreateRec);
    }
    
    /**
     * @param delta
     */
    private void adjustIndex(final int delta)
    {
        if (rowIndex == null)
        {
            clearForm();
            
        } else if (delta == 1)
        {
            rowIndex++;
            if (rowIndex == numRows)
            {
                adjustIndex(-1);
            }
            
        } else if (delta == -1)
        {
            rowIndex--;
            if (rowIndex == 0)
            {
                cursorModel.moveToFirst();
                
            } else
            {
                moveToPrevious();
            }
        } else if (delta == Integer.MIN_VALUE)
        {
            rowIndex = 0;
            cursorModel.moveToFirst();
            
        } else if (delta == Integer.MAX_VALUE)
        {
            rowIndex = numRows-1;
            cursorModel.moveToLast();
        }
        
        fillForm();
        
        updateUIState();
               
        updateRecDisplay();
        
        isChanged = false;
        changedHash.clear();
    }
    
    /**
     * 
     */
    private void moveToPrevious()
    {
        int rowInx = cursorModel.getColumnIndex("TripRowIndex");
        int rowNum = cursorModel.getInt(rowInx);
        
        Log.d("moveToPrevious", "Pos: "+cursorModel.getPosition()+", rowNum: "+rowNum+", rowIndex: "+rowIndex);
        
        while (rowNum >= rowIndex && !cursorModel.isBeforeFirst())
        {
            if (!cursorModel.moveToPrevious())
            {
                break;
            }
            rowNum = cursorModel.getInt(rowInx);
            Log.d("moveToPrevious", "Pos: "+cursorModel.getPosition()+", rowNum: "+rowNum+", rowIndex: "+rowIndex);
        }
        cursorModel.moveToNext();
        //fillForm();
    }
    
    /**
     * 
     */
    private void updateRecDisplay()
    {
        if (!isCreateRec)
        {
            if (cursorModel != null)
                Log.d("rec", "Pos: "+cursorModel.getPosition()+", Cnt: "+cursorModel.getCount());
            
            String lblStr = String.format("%3d of %3d", (rowIndex != null ? rowIndex : 0)+1, numRows);
            recLabel.setText(lblStr);
        }
    }
    
    /**
     * 
     */
    private void doSave()
    {
        Integer trpId = Integer.parseInt(tripId);
        
        if (isNewRec)
        {
            if (rowIndex == null)
            {
                int count = SQLUtils.getCount(getDB(), "SELECT COUNT(*) AS count FROM tripdatacell WHERE TripID = " + tripId);
                if (count == 0)
                {
                    rowIndex = 0;
                } else
                {
                    rowIndex = SQLUtils.getCount(getDB(), "SELECT TripRowIndex AS count FROM tripdatacell WHERE TripID = " +tripId+" ORDER BY TripRowIndex DESC LIMIT 1");
                    rowIndex++;
                }
            } else
            {
                rowIndex++;
            }
            
            numRows++;
            
            for (View view : viewToTTDId.keySet())
            {
                String value = getValue(view);
                
                tripDataCell.setTripDataDefID(viewToTTDId.get(view));
                tripDataCell.setTripID(trpId);
                tripDataCell.setTripRowIndex(rowIndex);
                tripDataCell.setData(value);
                tripDataCell.insert(getDB());
                
                Log.d("DBG", "New Row: V["+value+"] tripDataDefID["+tripDataCell.getTripDataDefID()+"] rowIndex["+tripDataCell.getTripRowIndex()+"] data["+tripDataCell.getData()+"] TripID["+tripDataCell.getTripID()+"]");
            }
            isNewRec = false;
            if (cursorModel != null)
            {
                cursorModel.requery();
                rowIndex = null;
            }
            
        } else
        {
            //int idInx = cursorModel.getColumnIndex("_id");
            for (View view : changedHash.keySet())
            {
                Boolean changed = changedHash.get(view);
                if (changed != null && changed)
                {
                    String value = getValue(view);
                    tripDataCell.setData(value);
                    tripDataCell.setTripDataDefID(viewToTTDId.get(view));
                    tripDataCell.setTripID(trpId);
                    tripDataCell.setTripRowIndex(rowIndex);
                    
                    //Log.d("DBG", "Update Row: ID["+cursorModel.getString(idInx)+"] tripDataDefID["+tripDataCell.getTripDataDefID()+"] rowIndex["+tripDataCell.getTripRowIndex()+"] data["+tripDataCell.getData()+"] TripID["+tripDataCell.getTripID()+"]");
                    
                    Integer recNo = recNumHash.get(view);
                    tripDataCell.update(recNo.toString(), getDB());          
                    cursorModel.requery();
                }
            }
        }
        isChanged = false;
        changedHash.clear();
    }
    
    /**
     * @param fieldView
     * @return
     */
    private String getValue(final View fieldView)
    {
        if (fieldView instanceof EditText)
        {
            EditText edtTxt = (EditText)fieldView;
            return edtTxt.getText().toString();
        }
        
        TextView txtView = (TextView)fieldView;
        return txtView.getText().toString();   
    }
    
    /**
     * 
     */
    private void updateUIState()
    {
        if (!isCreateRec)
        {
            boolean isLastOrPast    = rowIndex == null ? true : rowIndex == numRows-1;
            boolean isFirstOrBefore = rowIndex == null ? true : rowIndex == 0;
            
            if (cursorModel != null)
                Log.d("updateUIState ", "Pos: "+cursorModel.getPosition());
            
            posFirstBtn.setEnabled(!isFirstOrBefore);
            posPrevBtn.setEnabled(!isFirstOrBefore);
            
            posNextBtn.setEnabled(!isLastOrPast);
            posLastBtn.setEnabled(!isLastOrPast);
            
            saveBtn.setEnabled(isChanged);
        }
    }
    
    /**
     * 
     */
    protected void buildUI()
    {
        ProgressDialog prgDlg = ProgressDialog.show(this, null, getString(R.string.loading), true);
        
        TableLayout tableLayout = (TableLayout)findViewById(R.id.uicontainer);
        Cursor      cursor      = TripDataDef.getAll(getDB(), "tripdatadef", "WHERE TripId = " + tripId, " ColumnIndex ASC");
        if (cursor.moveToFirst())
        {
            int nmInx = cursor.getColumnIndex("Name");
            int ttInx = cursor.getColumnIndex("Title");
            int dtInx = cursor.getColumnIndex("DataType");
            TripDataDef.TripDataDefType[] types = TripDataDef.TripDataDefType.values();
            int id = 0;
            do
            {
                String   cellName  = cursor.getString(nmInx);
                String   cellTitle = cursor.getString(ttInx);
                short    type      = cursor.getShort(dtInx);
                
                TableRow tblRow    = new TableRow(this);
                tblRow.setOrientation(TableRow.HORIZONTAL); 
                
                TextView label  = new TextView(this);
                label.setText(cellTitle + ":");
                label.setGravity(Gravity.RIGHT);
                
                EditText edtTxt = null;
                View     view   = null;
                switch (types[type])
                {
                    case intType:
                    case floatType:
                    case doubleType:
                    {
                        edtTxt = new EditText(this);
                        view   = edtTxt;
                    } break;
                    
                    case dateType:
                    {
                        /*ImageView calBtn = (ImageView) findViewById(R.id.calendarbtn);
                        calBtn.setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View v)
                            {
                                showDialog(0);
                            }
                        });
                        TextView     txtView   = new TextView(this);
                        LinearLayout container = new LinearLayout(this);
                        container.setOrientation(LinearLayout.HORIZONTAL);
                        container.addView(txtView);
                        container.addView(calBtn);
                        */
                        TextView txtView = new TextView(this);
                        view = txtView;
                        txtView.setPadding(4, 6, 0, 6);
                        //txtView.setHeight(defLabelHeight);
                        
                    } break;
                    
                    case strType:
                    {
                        if (cellName.equals("GenusSpecies"))
                        {
                            AutoCompleteTextView actv = new AutoCompleteTextView(this);
                            hookupAutoCompTextField(this, actv);
                            edtTxt = actv;
                            view   = edtTxt;
                        } else
                        {
                            edtTxt = new EditText(this);
                            view   = edtTxt;
                        }
                    } break;
                }
                
                if (edtTxt != null)
                {
                    edtTxt.addTextChangedListener(createTextWatcher(edtTxt));
                    edtTxt.setId(id);
                    edtTxt.setSingleLine();
                }
               
                tblRow.addView(label, 0);
                tblRow.addView(view, 1);
                
                int rows = tableLayout.getChildCount();
                tableLayout.addView(tblRow, rows-2);
                
                int ttdId = cursor.getInt(cursor.getColumnIndex("_id"));
                
                Log.d("BUILDUI", "ttdId: " + ttdId +"  colIndex["+cursor.getInt(cursor.getColumnIndex("ColumnIndex"))+"] Name: "+ cellName + "  Type: "+type);
                
                
                viewToTTDId.put(view, ttdId);
                compHash.put(cellName, view);
                compTDD.put(cellName, ttdId);
                comps.add(view);
                id++;
                
            } while (cursor.moveToNext());
            
            numDataColumns = id;
        }
        cursor.close();
        
        closeCursor();

        rowIndex = null;
        
        boolean doFill = true;
        if (!isCreateRec)
        {
            int count = SQLUtils.getCount(getDB(), "SELECT COUNT(*) as count FROM tripdatacell WHERE TripID = " + tripId);
            if (count == 0)
            {
                isNewRec = true;
                clearForm();
                
            } else
            {
                String sql = String.format("SELECT tc._id, tc.Data, tc.TripRowIndex, td.ColumnIndex FROM tripdatacell tc INNER JOIN tripdatadef td ON tc.TripDataDefID = td._id WHERE tc.TripID = %s ORDER BY tc.TripRowIndex, td.ColumnIndex", tripId);
                		
                cursorModel = getDB().rawQuery(sql, null);
                
                if (cursorModel.moveToFirst())
                {
                    rowIndex = 0;
                    fillForm();
                }
                
                doFill = false;
            }
        }
            
        updateUIState();
        updateRecDisplay();

        isChanged = false;
        changedHash.clear();
        
        if (doFill)  
        {
            fillInTransientValues();
        }
        
        prgDlg.dismiss();
        
        if (cursorModel != null)
        {
            startManagingCursor(cursorModel);
        }
    }
    
    /**
     * 
     */
    private void fillInTransientValues()
    {
        double lat = getIntent().getDoubleExtra(LAT_VAL, -1000.0);
        if (lat != -1000.0)
        {
            EditText latEdtTxt = (EditText)compHash.get("Latitude");
            if (latEdtTxt != null)
            {
                latEdtTxt.setText(Double.toString(lat));
            }
            
            EditText lonEdtTxt = (EditText)compHash.get("Longitude");
            double   lon       = getIntent().getDoubleExtra(LON_VAL, -1000.0);
            if (latEdtTxt != null && lon != 1000.0)
            {
                lonEdtTxt.setText(Double.toString(lon));
            }
            
            TextView dateTF = (TextView)compHash.get("Date");
            if (dateTF != null)
            {
                dateTF.setText(sdf.format(Calendar.getInstance().getTime()));
            } else
            {
                Log.e("DatEntry", "Couldn't find Date Component.");
            }
        }
    }
    
    /**
     * @param editText
     * @return
     */
    private TextWatcher createTextWatcher(final EditText editText)
    {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                changedHash.put(editText, Boolean.TRUE);
                isChanged = true;
                saveBtn.setEnabled(true);
            }
        };
    }
    
    /**
     * Assume it is already positioned at the beginning of a row
     */
    private void fillForm()
    {
        Log.d("fillForm", "isBefore: "+cursorModel.isBeforeFirst());
        Log.d("fillForm", "isFirst:  "+cursorModel.isFirst());
        Log.d("fillForm", "Position: "+cursorModel.getPosition());
        
        if (cursorModel.isBeforeFirst())
        {
            cursorModel.moveToFirst();
        }
        
        valueHash.clear();
        
        int recNumInx  = cursorModel.getColumnIndex("_id");
        int dataInx    = cursorModel.getColumnIndex("Data");
        int dataColInx = cursorModel.getColumnIndex("ColumnIndex");
        int dataRowInx = cursorModel.getColumnIndex("TripRowIndex");
        
        for (int i=0;i<numDataColumns;i++)
        {
            int rowInx = cursorModel.getInt(dataRowInx);
            Log.d("DEBUG", "id: " + cursorModel.getInt(cursorModel.getColumnIndex("_id")) +"  rowInx: "+rowInx +"  rowIndex: "+rowIndex);

            if (rowInx != rowIndex)
            {
                //cursorModel.moveToPrevious();
                Log.d("DEBUG", "id: " + cursorModel.getInt(cursorModel.getColumnIndex("_id")) +"  rowInx: "+rowInx +"  rowIndex: "+rowIndex);
                break;
            }
            
            int    recNum = cursorModel.getInt(recNumInx);
            int    column = cursorModel.getInt(dataColInx);
            String data   = cursorModel.getString(dataInx);
            
            recNumHash.put(comps.get(i), recNum);
            
            Log.d("DEBUG", "id: " + cursorModel.getInt(cursorModel.getColumnIndex("_id")) +"  data["+data+"] Col: "+ column + "  Row: "+rowInx);
            
            valueHash.put(column, data);
            
            if (!cursorModel.isLast())
            {
                if (!cursorModel.moveToNext())
                {
                    break;
                }
            }
        }
        
        for (int i=0;i<numDataColumns;i++)
        {
            String data = valueHash.get(i);
            setValue(comps.get(i), data != null ? data : "");
        }
        
        saveBtn.setEnabled(false);
        isChanged = false;
    }
    
    /**
     * @param view
     * @param data
     */
    private void setValue(final View view, final String data)
    {
        if (view instanceof EditText)
        {
            ((EditText)view).setText(data);
        } else if (view instanceof AutoCompleteTextView)
        {
            ((AutoCompleteTextView)view).setText(data);
        } else
        {
            //Log.d("xxx", view.getClass().getName());
            ((TextView)view).setText(data);
        }
    }

    /**
     * 
     */
    private void clearForm()
    {
        String data = "";
        for (int i=0;i<numDataColumns;i++)
        {
            setValue(comps.get(i), data);
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause()
    {
        super.onPause();
        isActive.set(false);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();

        isActive.set(true);
    }
    
    //----------------------------------------------------------------
    private View.OnClickListener onSave = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            doSave();
        }
    };
    
    //----------------------------------------------------------------
    private View.OnClickListener onAdd = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            isNewRec = true;
            clearForm();
        }
    };
    
    /**
     * @param activity
     * @param autoCompTextField
     */
    public void hookupAutoCompTextField(final Activity activity, 
                                        final AutoCompleteTextView autoCompTextField)
    {
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(activity,
                android.R.layout.simple_dropdown_item_1line, null, new String[] { "Name" },
                new int[] { android.R.id.text1 });
        
        adapter2.setCursorToStringConverter(new HistoryCursorConverter());
        
        if (SQLUtils.getCount(getDB(), "SELECT COUNT(*) FROM taxon") > 0)
        {
            adapter2.setFilterQueryProvider(new FilterQueryProvider()
            {
                @Override
                public Cursor runQuery(final CharSequence constraint)
                {
                    //Log.d("debug", "Cnt: " + );
                    if (constraint != null && constraint.length() > 0)
                    {
                        String sql = "SELECT _id, Name FROM taxon WHERE Name LIKE '"+constraint+"%'";
                        Log.d("T", sql);
                        return getDB().rawQuery(sql, null);
                    }
                    return null;
                }
            });
            autoCompTextField.setAdapter(adapter2);
        }
    }

    //--------------------------------------------------------------------
    public class HistoryCursorConverter implements CursorToStringConverter
    {
        public CharSequence convertToString(final Cursor theCursor)
        {
            // if (debug) Log.d(TAG,"convertToString()");
            // Return the first column of the database cursor
            String aColumnString = theCursor.getString(1);
            return aColumnString;
        }
    } 
}
