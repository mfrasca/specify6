/* Copyright (C) 2009, University of Kansas Center for Research
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
package edu.ku.brc.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import edu.ku.brc.specifydroid.R;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Jan 21, 2011
 *
 */
public class ErrorDlgHelper
{
    private static String packageName = "edu.ku.brc.specifydroid";
    
    
    /**
     * @return the packageName
     */
    public static String getPackageName()
    {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public static void setPackageName(String packageName)
    {
        ErrorDlgHelper.packageName = packageName;
    }

    /**
     * @param activity
     * @param id
     */
    public static void showErrorDlg(final Activity activity, final int id)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(id).setCancelable(false).setPositiveButton(activity.getString(R.string.close), null);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final AlertDialog alert = builder.create();
                alert.show();
            }});
    }

    /**
     * @param activity
     * @param format
     * @param args
     */
    public static void showErrorDlg(final Activity activity, final String format, Object...args)
    {
        String str = String.format(format, args);
        showErrorDlg(activity, str);
    }
    
    /**
     * @param activity
     * @param resId
     * @param args
     */
    public static void showErrorDlg(final Activity activity, final int resId, Object...args)
    {
        String str = activity.getString(resId, args);
        showErrorDlg(activity, str);
    }
    
    /**
     * @param activity
     * @param strResName
     * @return
     */
    public static String getStringResourceByName(final Activity activity, final String strResName)
    {
      int resId = activity.getResources().getIdentifier(strResName, "string", packageName);
      return resId == 0 ? strResName : activity.getString(resId);
    }
    
    /**
     * @param activity
     * @param str
     */
    public static void showErrorDlg(final Activity activity, final String str)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(str).setCancelable(false).setPositiveButton(activity.getString(R.string.close), null);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final AlertDialog alert = builder.create();
                alert.show();
            }});
    }
}
