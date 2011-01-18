package edu.ku.brc.specifydroid;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/**
 * 
 */

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Jan 12, 2011
 *
 */
public class SpBaseActivity extends Activity
{
    protected TripSQLiteHelper  tripDBHelper = null;
    protected Cursor            cursorModel  = null;

    /**
     * 
     */
    public SpBaseActivity()
    {
        super();
    }
    
    /**
     * Gets a resource string by name.
     * @param strResName the name of the string
     * @return the localized string
     */
    protected String getStringResourceByName(String strResName)
    {
      String packageName = "edu.ku.brc.specifydroid";
      int resId = getResources().getIdentifier(strResName, "string", packageName);
      return resId == 0 ? strResName : getString(resId);
    }
    
    //------------------------------------------------------------------------
    //-- Database Access
    //------------------------------------------------------------------------

    /**
     * 
     */
    protected void closeCursor()
    {
        if (cursorModel != null)
        {
            Log.d(getClass().getName(), "closeCursor()");

            stopManagingCursor(cursorModel);
            cursorModel.close();
            cursorModel = null;
        }
    }
    
    /**
     * @return
     */
    protected SQLiteDatabase getDB()
    {
        if (tripDBHelper == null)
        {
            Log.d(getClass().getName(), "getDB()");

            tripDBHelper = new TripSQLiteHelper(this.getApplicationContext());
        }
        return tripDBHelper.getWritableDatabase();
    }
    
    /**
     * 
     */
    protected void closeDB()
    {
        if (tripDBHelper != null)
        {
            Log.d(getClass().getName(), "closeDB()");

            tripDBHelper.close();
            tripDBHelper = null;
        }
        
    }

}
