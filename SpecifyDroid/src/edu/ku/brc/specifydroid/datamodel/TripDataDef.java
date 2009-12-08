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
public class TripDataDef extends BaseDataObj<TripDataDef>
{
    public enum TripDataDefType {intType, doubleType, floatType, strType, dateType}
    
    protected Integer id          = null;
    protected String  name        = "";
    protected String  title       = "";
    protected Short   dataType    = null;
    protected Integer columnIndex = null;
    protected Integer tripID      = null;


    /**
     * @param tableName
     */
    public TripDataDef()
    {
        super("tripdatadef");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specifydroid.BaseDataObj#getDataFromCursor(android.database.Cursor)
     */
    @Override
    public void getDataFromCursor(final Cursor cursor) throws Exception
    {
        id          = cursor.getInt(cursor.getColumnIndex("_id"));
        name        = cursor.getString(cursor.getColumnIndex("Name"));
        title       = cursor.getString(cursor.getColumnIndex("Title"));
        dataType    = cursor.getShort(cursor.getColumnIndex("DataType"));
        columnIndex = cursor.getInt(cursor.getColumnIndex("ColumnIndex"));
        tripID      = cursor.getInt(cursor.getColumnIndex("TripID"));
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specifydroid.BaseDataObj#putContentValues(android.content.ContentValues)
     */
    @Override
    protected void putContentValues(final ContentValues cv)
    {
        cv.put("Name", name);
        cv.put("Title", title);
        cv.put("DataType", dataType);
        cv.put("ColumnIndex", columnIndex);
        cv.put("TripID", tripID);
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

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    public Short getDataType()
    {
        return dataType;
    }
    public void setDataType(Short dataType)
    {
        this.dataType = dataType;
    }

    public Integer getColumnIndex()
    {
        return columnIndex;
    }
    public void setColumnIndex(Integer columnIndex)
    {
        this.columnIndex = columnIndex;
    }

    public Integer getTripID()
    {
        return tripID;
    }
    public void setTripID(Integer tripID)
    {
        this.tripID = tripID;
    }


    /**
     * @param id
     * @param tripId
     * @param db
     * @return
     */
    public static TripDataDef getById(final String id, 
                                      final String tripId,
                                      final SQLiteDatabase db)
    {
        String[] args = { id, tripId };
        
        Cursor c = null;
        try
        {
            c = db.rawQuery("SELECT * FROM tripdatadef WHERE _id=? AND TripId=?", args);
            c.moveToFirst();
    
            return new TripDataDef().loadFrom(c);
            
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