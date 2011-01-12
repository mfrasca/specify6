package edu.ku.brc.specifydroid;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import edu.ku.brc.specifydroid.datamodel.Trip;
import edu.ku.brc.specifydroid.datamodel.TripDataDef;

public class TripDetailActivity extends SpBaseActivity implements DatePickerDialog.OnDateSetListener
{
    
    public final static String ID_EXTRA = "edu.ku.brc.specifydroid._ID";
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private int[]          txtEdtIds   = {R.id.name,    R.id.notes};
    private String[]       txtEdtNames = {"name",       "notes"};


    private ListView       tripDataDefList  = null;
    private TextView       dateLbl  = null;
    private RadioGroup     types    = null;
    private Trip           current  = null;
    private String         tripId   = null;
    private Calendar       tripDate = Calendar.getInstance();
    
    private ImageView      addBtn;
    private ImageView      delBtn;
    private Button         saveBtn;
    
    private boolean        hasChanged  = false;
    
    private HashMap<Integer, EditText> editTexts = new HashMap<Integer, EditText>();

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public TripDetailActivity()
    {
        super();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        if (savedInstanceState != null)
        {
            tripId = savedInstanceState.getString(TripListActivity.ID_EXTRA);
        } else
        {
            tripId = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        }
        
        setContentView(R.layout.detail_form);
        
        for (Integer id : txtEdtIds)
        {
            editTexts.put(id, (EditText) findViewById(id));
        }

        types           = (RadioGroup) findViewById(R.id.types);
        dateLbl         = (TextView)findViewById(R.id.date);
        tripDataDefList = (ListView)findViewById(R.id.tripdatadeflist);
        
        tripDataDefList.setOnItemClickListener(onListClick);

        saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(onSave);

        if (tripId == null)
        {
            current = new Trip();
            current.setTripDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        }
        
        load();
        
        initTripDataDefList();

        if (savedInstanceState != null)
        {
            int i = 0;
            for (Integer id : txtEdtIds)
            {
                editTexts.get(id).setText(savedInstanceState.getString(txtEdtNames[i]));
                i++;
            }
            types.check(savedInstanceState.getInt("type"));
            char[] str = savedInstanceState.getCharArray("date");
            if (str != null && str.length > 0)
            {
                dateLbl.setText(str, 0, str.length);
            }
        }
        
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                hasChanged = true;
                updateUIState();
            }
        };
        
        editTexts.get(R.id.name).addTextChangedListener(textWatcher);
        editTexts.get(R.id.notes).addTextChangedListener(textWatcher);
        
        View.OnClickListener rbListener = new View.OnClickListener() {
            public void onClick(View v) {
                hasChanged = true;
                updateUIState();
            }
        };
        
        RadioButton rb = (RadioButton)findViewById(R.id.collecting);
        rb.setOnClickListener(rbListener);
        
        rb = (RadioButton)findViewById(R.id.observing);
        rb.setOnClickListener(rbListener);
        
        ImageView calBtn = (ImageView) findViewById(R.id.calendarbtn);
        calBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(0);
            }
        });
        
        addBtn = (ImageView) findViewById(R.id.addttd);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                doSave();
                addTripDataDef();
            }
        });
        addBtn.setVisibility(tripId != null ? View.INVISIBLE : View.VISIBLE);
        
        delBtn = (ImageView) findViewById(R.id.deltrip);
        delBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                doDelTrip();
            }
        });
        delBtn.setVisibility(View.GONE);
        
        updateUIState();
    }

    /**
     * 
     */
    private void doDelTrip()
    {
        if (tripId == null)
        {
            finish(); // cancel
            
        } else
        {
            // delete
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
    private void initTripDataDefList()
    {
        if (cursorModel != null)
        {
            stopManagingCursor(cursorModel);
            cursorModel.close();
            cursorModel = null;
        }

        String where = "WHERE TripId = " + tripId;
        cursorModel = TripDataDef.getAll(getDB(), "tripdatadef", where, null);
        
        if (cursorModel != null)
        {
            startManagingCursor(cursorModel);
    
            tripDataDefList.setAdapter(new DataAdapterWithBinder(new TripDataDefDataViewBinder(),
                                              this, 
                                              R.layout.tdd_row, 
                                              cursorModel, 
                                              new String[] {"Name", "Title", "DataType"}, 
                                              new int[] {R.id.tddrwname, R.id.tddrwtitle, R.id.tddrwdatatype}));
        }
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) 
    {
        return new DatePickerDialog(this,
                    this,
                    tripDate.get(Calendar.YEAR), 
                    tripDate.get(Calendar.MONTH), 
                    tripDate.get(Calendar.DAY_OF_MONTH));
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop()
    {
        super.onStop();
        
        if (cursorModel != null)
        {
            cursorModel.close();
            cursorModel = null;
        }
        
        closeDB();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("type", types.getCheckedRadioButtonId());
        
        int i = 0;
        for (Integer id : txtEdtIds)
        {
            savedInstanceState.putString(txtEdtNames[i], editTexts.get(id).getText().toString());
            i++;
        }
        types.check(savedInstanceState.getInt("type"));
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        new MenuInflater(getApplication()).inflate(R.menu.tripdetailmenus, menu);

        return (super.onCreateOptionsMenu(menu));
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        if (item.getItemId() == R.id.tripconfig)
        {
            Intent intent = new Intent(this, TripDataDefActivity.class);
            intent.putExtra(ID_EXTRA, String.valueOf(tripId));
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * 
     */
    private void updateUIState()
    {
        boolean enabled = editTexts.get(R.id.name).length() > 0;
        if (addBtn != null)
        {
            addBtn.setEnabled(enabled);
            saveBtn.setEnabled(enabled);
        }
        
        saveBtn.setEnabled(hasChanged);
    }

    /**
     * 
     */
    private void load()
    {
        if (tripId != null)
        {   
            current = Trip.getById(getDB(), tripId);
        }

        editTexts.get(R.id.name).setText(current.getName());
        editTexts.get(R.id.notes).setText(current.getNotes());
        
        /*editTexts.get(R.id.firstName1).setText(current.getFirstName1());
        editTexts.get(R.id.lastName1).setText(current.getLastName1());
        editTexts.get(R.id.firstName2).setText(current.getFirstName2());
        editTexts.get(R.id.lastName2).setText(current.getLastName2());
        editTexts.get(R.id.firstName3).setText(current.getFirstName3());
        editTexts.get(R.id.lastName3).setText(current.getLastName3());*/
        
        if (current.getType().equals("observing"))
        {
            types.check(R.id.observing);
        } else
        {
            types.check(R.id.collecting);
        }
        
        if (tripDate == null) tripDate = Calendar.getInstance();
        
        if (current.getTripDate() == null)
        {
            current.setTripDate(new java.sql.Date(tripDate.getTime().getTime()));
        }
        
        tripDate.setTime(current.getTripDate());
        
        char[] str = (new String(sdf.format(tripDate.getTime()))).toCharArray();
        dateLbl.setText(str, 0, str.length);

    }

    /* (non-Javadoc)
     * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
     */
    @Override
    public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth)
    {
        tripDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tripDate.set(Calendar.MONTH, monthOfYear);
        tripDate.set(Calendar.YEAR, year);
        
        char[] str = (new String(sdf.format(tripDate.getTime()))).toCharArray();
        dateLbl.setText(str, 0, str.length);
        
        hasChanged = true;
        updateUIState();
    }
    
    /**
     * 
     */
    private void doSave()
    {
        current.setName(editTexts.get(R.id.name).getText().toString());
        current.setNotes(editTexts.get(R.id.notes).getText().toString());
        
        editTexts.get(R.id.name).setNextFocusDownId(R.id.notes);
        
        /*current.setFirstName1(editTexts.get(R.id.firstName1).getText().toString());
        current.setLastName1(editTexts.get(R.id.lastName1).getText().toString());
        current.setFirstName2(editTexts.get(R.id.firstName2).getText().toString());
        current.setLastName2(editTexts.get(R.id.lastName2).getText().toString());
        current.setFirstName3(editTexts.get(R.id.firstName3).getText().toString());
        current.setLastName3(editTexts.get(R.id.lastName3).getText().toString());
        */
        
        current.setTripDate(new java.sql.Date(tripDate.getTime().getTime()));
        
        if (current.getTimestampCreated() == null)
        {
            current.setTimestampCreated(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        current.setTimestampModified(new Timestamp(Calendar.getInstance().getTime().getTime()));
        
        switch (types.getCheckedRadioButtonId())
        {
            case R.id.collecting:
                current.setType("collecting");
                break;

            case R.id.observing:
                current.setType("observing");
                break;
        }

        if (tripId == null)
        {
            Long id = current.insert(getDB());
            tripId = id.toString();
            
        } else
        {
            current.update(tripId, getDB());
        }
        
        hasChanged = false;
        updateUIState();
    }

    //----------------------------------------------------------------
    private View.OnClickListener onSave = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            doSave();

            finish();
        }
    };
    
  //------------------------------------------------------------------------------
    private AdapterView.OnItemClickListener onListClick  = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(final AdapterView<?> parent,
                                final View           view,
                                final int            position,
                                final long           id)
        {
            Intent i = new Intent(TripDetailActivity.this, TripDataDefDetailActivity.class);
            i.putExtra(TripDataDefActivity.ID_EXTRA, String.valueOf(id));
            i.putExtra(TripListActivity.ID_EXTRA, String.valueOf(tripId));
            startActivity(i);
        }
    };
}