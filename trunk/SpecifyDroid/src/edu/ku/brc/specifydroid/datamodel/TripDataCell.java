package edu.ku.brc.specifydroid.datamodel;

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
public class TripDataCell extends BaseDataObj<TripDataCell>
{
    protected Integer id            = null;
    protected Integer tripDataDefID = null;
    protected Integer tripID        = null;
    protected Integer tripRowIndex  = null;
    protected String  data = "";


    /**
     * @param tableName
     */
    public TripDataCell()
    {
        super("tripdatacell");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specifydroid.BaseDataObj#getDataFromCursor(android.database.Cursor)
     */
    @Override
    public void getDataFromCursor(Cursor cursor) throws Exception
    {
        id            = cursor.getInt(cursor.getColumnIndex("_id"));
        tripDataDefID = cursor.getInt(cursor.getColumnIndex("TripDataDefID"));
        tripID        = cursor.getInt(cursor.getColumnIndex("TripID"));
        tripRowIndex  = cursor.getInt(cursor.getColumnIndex("TripRowIndex"));
        data          = cursor.getString(cursor.getColumnIndex("Data"));

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specifydroid.BaseDataObj#putContentValues(android.content.ContentValues)
     */
    @Override
    protected void putContentValues(ContentValues cv)
    {
        cv.put("TripDataDefID", tripDataDefID);
        cv.put("TripID",        tripID);
        cv.put("TripRowIndex",  tripRowIndex);
        cv.put("Data",          data);
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

    public Integer getTripDataDefID()
    {
        return tripDataDefID;
    }
    public void setTripDataDefID(Integer tripDataDefID)
    {
        this.tripDataDefID = tripDataDefID;
    }

    public Integer getTripID()
    {
        return tripID;
    }
    public void setTripID(Integer tripID)
    {
        this.tripID = tripID;
    }

    public Integer getTripRowIndex()
    {
        return tripRowIndex;
    }
    public void setTripRowIndex(Integer tripRowIndex)
    {
        this.tripRowIndex = tripRowIndex;
    }

    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }


    /**
     * @param id
     * @param db
     * @return
     */
    public static TripDataCell getById(final String id, final SQLiteDatabase db)
    {
        String[] args = { id };
        
        Cursor c = null;
        try
        {
            c = db.rawQuery("SELECT * FROM tripdatacell WHERE _id=?", args);
            c.moveToFirst();
    
            return new TripDataCell().loadFrom(c);
            
        } catch (Exception ex)
        {
            Log.e("TripDataCell", "Error getting id: "+id, ex);
        } finally
        {
            if (c != null) c.close();
        }

        return null;
    }
    
}