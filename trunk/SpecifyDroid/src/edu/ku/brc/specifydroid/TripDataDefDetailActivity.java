package edu.ku.brc.specifydroid;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.ku.brc.specifydroid.datamodel.TripDataDef;

public class TripDataDefDetailActivity extends Activity implements AdapterView.OnItemSelectedListener
{
    public static final String[] dataTypeItems = new String[] {"Integer", "Double", "Float", "String", "Date"};
    
    private int[]          txtEdtIds   = {R.id.tddname, R.id.tddtitle,};
    private String[]       txtEdtNames = {"name",       "title"};

    private Spinner        dataTypeSP    = null;
    private TripDataDef    current       = null;
    private SQLiteDatabase db            = null;
    private String         tripId        = null;
    private String         tripDataDefId = null;
    
    private HashMap<Integer, EditText> editTexts = new HashMap<Integer, EditText>();

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public TripDataDefDetailActivity()
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
        
        setContentView(R.layout.tdd_detail_form);
        
        db = SpecifyActivity.getDatabase();
        
        for (Integer id : txtEdtIds)
        {
            editTexts.put(id, (EditText) findViewById(id));
        }

        ArrayAdapter<String> dataTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataTypeItems);
        
        dataTypeSP = (Spinner)findViewById(R.id.tdddatatype);
        dataTypeSP.setOnItemSelectedListener(this);
        dataTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        dataTypeSP.setAdapter(dataTypeAdapter);
        
        Button save = (Button) findViewById(R.id.ttdsave);
        save.setOnClickListener(onSave);

        tripId        = getIntent().getStringExtra(TripListActivity.ID_EXTRA);
        tripDataDefId = getIntent().getStringExtra(TripDataDefActivity.ID_EXTRA);

        if (tripDataDefId == null)
        {
            current = new TripDataDef();
        } else
        {
            load();
        }

        if (savedInstanceState != null)
        {
            int i = 0;
            for (Integer id : txtEdtIds)
            {
                editTexts.get(id).setText(savedInstanceState.getString(txtEdtNames[i]));
                i++;
            }
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putShort("datatype", ((Integer)dataTypeSP.getSelectedItemPosition()).shortValue());
        
        int i = 0;
        for (Integer id : txtEdtIds)
        {
            savedInstanceState.putString(txtEdtNames[i], editTexts.get(id).getText().toString());
            i++;
        }
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
            startActivity(new Intent(this, TripDataDefDetailActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemSelected(AdapterView<?> adptView, View view, int position, long id)
    {
        
    }

    /* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
    }

    /**
     * 
     */
    private void load()
    {
        current = TripDataDef.getById(tripDataDefId, tripId, db);
        editTexts.get(R.id.tddname).setText(current.getName());
        editTexts.get(R.id.tddtitle).setText(current.getTitle());
        
        short inx = current.getDataType();
        dataTypeSP.setSelection(inx);
    }
    
    //----------------------------------------------------------------
    private View.OnClickListener onSave = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int count = SQLUtils.getCount(db, "SELECT COUNT(*) AS count FROM tripdatadef WHERE TripID = " + tripId);
            if (count > -1)
            {
                current.setName(editTexts.get(R.id.tddname).getText().toString());
                current.setTitle(editTexts.get(R.id.tddtitle).getText().toString());
                current.setDataType(((Integer)dataTypeSP.getSelectedItemPosition()).shortValue());
                
                if (tripDataDefId == null)
                {
                    current.setTripID(Integer.parseInt(tripId));
                    current.setColumnIndex(count);
                    current.insert(db);
                } else
                {
                    current.update(tripDataDefId, db);
                }
            }

            finish();
        }
    };
}