package edu.ku.brc.specifydroid.datamodel;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import edu.ku.brc.specifydroid.BaseDataObj;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Oct 28, 2009
 *
 */
public class Trip extends BaseDataObj<Trip>
{
    protected Integer   id   = null;
    protected String    name = "";
    protected String    type = "";
    protected String    notes = "";
    protected Date      tripDate = null;
    protected String    firstName1 = "";
    protected String    lastName1 = "";
    protected String    firstName2 = "";
    protected String    lastName2 = "";
    protected String    firstName3 = "";
    protected String    lastName3 = "";
    protected Timestamp timestampCreated = null;
    protected Timestamp timestampModified = null;

    // transient
    protected Vector<TripDataDef> defs    = new Vector<TripDataDef>();
    protected Vector<TripDataDef> newDefs = new Vector<TripDataDef>();
    protected Vector<TripDataDef> updDefs = new Vector<TripDataDef>();
    protected Vector<TripDataDef> delDefs = new Vector<TripDataDef>();
    
    /**
     * @param tableName
     */
    public Trip()
    {
        super("trip");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specifydroid.BaseDataObj#getDataFromCursor(android.database.Cursor)
     */
    @Override
    public void getDataFromCursor(Cursor cursor) throws Exception
    {
        id         = cursor.getInt(cursor.getColumnIndex("_id"));
        name       = cursor.getString(cursor.getColumnIndex("Name"));
        type       = cursor.getString(cursor.getColumnIndex("Type"));
        notes      = cursor.getString(cursor.getColumnIndex("Notes"));
        tripDate   = getDate(cursor, "TripDate");
        firstName1 = cursor.getString(cursor.getColumnIndex("FirstName1"));
        lastName1  = cursor.getString(cursor.getColumnIndex("LastName1"));
        firstName2 = cursor.getString(cursor.getColumnIndex("FirstName2"));
        lastName2  = cursor.getString(cursor.getColumnIndex("LastName2"));
        firstName3 = cursor.getString(cursor.getColumnIndex("FirstName3"));
        lastName3  = cursor.getString(cursor.getColumnIndex("LastName3"));
        timestampCreated = getTimestamp(cursor, "TimestampCreated");
        timestampModified = getTimestamp(cursor, "TimestampModified");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specifydroid.BaseDataObj#putContentValues(android.content.ContentValues)
     */
    @Override
    protected void putContentValues(ContentValues cv)
    {
        cv.put("Name", name);
        cv.put("Type", type);
        cv.put("Notes", notes);
        setDate(cv, tripDate, "TripDate");
        cv.put("FirstName1", firstName1);
        cv.put("LastName1", lastName1);
        cv.put("FirstName2", firstName2);
        cv.put("LastName2", lastName2);
        cv.put("FirstName3", firstName3);
        cv.put("LastName3", lastName3);
        setTimestamp(cv, timestampCreated, "TimestampCreated");
        setTimestamp(cv, timestampModified, "TimestampModified");
    }

    /**
     * @param db
     */
    public void renumberColumnIndexes(final SQLiteDatabase db)
    {
        Vector<Integer> ids    = new Vector<Integer>();
        Cursor          cursor = db.rawQuery("SELECT _id FROM trapdatadef WHERE TripID = " + id, null);
        if (cursor.moveToFirst())
        {
            int inx = cursor.getColumnIndex("_id");
            do
            {
                ids.add(cursor.getInt(inx));
                
            } while (cursor.moveToNext());
            cursor.close();
        }
        
        int inx = 0;
        for (Integer id : ids)
        {
            db.rawQuery("UPDATE tripdatadef SET ColumnIndex=" + inx + " WHERE _id=" + id, null);
            inx++;
        }   
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the newDefs
     */
    public Vector<TripDataDef> getNewDefs()
    {
        return newDefs;
    }

    /**
     * @return the delDefs
     */
    public Vector<TripDataDef> getDelDefs()
    {
        return delDefs;
    }

    /**
     * @return the defs
     */
    public Vector<TripDataDef> getDefs()
    {
        return defs;
    }

    /**
     * @return the updDefs
     */
    public Vector<TripDataDef> getUpdDefs()
    {
        return updDefs;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getNotes()
    {
        return notes;
    }
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public Date getTripDate()
    {
        return tripDate;
    }
    public void setTripDate(Date tripDate)
    {
        this.tripDate = tripDate;
    }

    public String getFirstName1()
    {
        return firstName1;
    }
    public void setFirstName1(String firstName1)
    {
        this.firstName1 = firstName1;
    }

    public String getLastName1()
    {
        return lastName1;
    }
    public void setLastName1(String lastName1)
    {
        this.lastName1 = lastName1;
    }

    public String getFirstName2()
    {
        return firstName2;
    }
    public void setFirstName2(String firstName2)
    {
        this.firstName2 = firstName2;
    }

    public String getLastName2()
    {
        return lastName2;
    }
    public void setLastName2(String lastName2)
    {
        this.lastName2 = lastName2;
    }

    public String getFirstName3()
    {
        return firstName3;
    }
    public void setFirstName3(String firstName3)
    {
        this.firstName3 = firstName3;
    }

    public String getLastName3()
    {
        return lastName3;
    }
    public void setLastName3(String lastName3)
    {
        this.lastName3 = lastName3;
    }

    public Timestamp getTimestampCreated()
    {
        return timestampCreated;
    }
    public void setTimestampCreated(Timestamp timestampCreated)
    {
        this.timestampCreated = timestampCreated;
    }

    public Timestamp getTimestampModified()
    {
        return timestampModified;
    }
    public void setTimestampModified(Timestamp timestampModified)
    {
        this.timestampModified = timestampModified;
    }
    
    /**
     * @param db
     */
    public void loadTripDataDefs(final SQLiteDatabase db)
    {
        defs.clear();
        
        if (id != null)
        {
            String where = "WHERE TripId = " + id;
            Cursor cursor = TripDataDef.getAll(db, "tripdatadef", where, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    TripDataDef tdd = new TripDataDef();
                    tdd.loadFrom(cursor);
                    defs.add(tdd);
                    
                } while (cursor.moveToNext());
            }
        }
    }


    /**
     * @param id
     * @param db
     * @return
     */
    public static Trip getById(final SQLiteDatabase db, final String id)
    {
        String[] args = { id };
        
        Cursor c = null;
        try
        {
            c = db.rawQuery("SELECT * FROM trip WHERE _id=?", args);
            c.moveToFirst();
    
            return new Trip().loadFrom(c);
            
        } catch (Exception ex)
        {
            Log.e("Trip", "Error getting id: "+id, ex);
        } finally
        {
            if (c != null) c.close();
        }

        return null;
    }
    
}