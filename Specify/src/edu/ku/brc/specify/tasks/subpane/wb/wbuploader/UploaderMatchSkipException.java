/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
package edu.ku.brc.specify.tasks.subpane.wb.wbuploader;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.util.List;
import java.util.Vector;

import edu.ku.brc.specify.datamodel.DataModelObjBase;

/**
 * @author timbo
 *
 * @code_status Alpha
 * 
 * Exception that is thrown when an upload to a table is skipped due to the existence
 * of a matching record in the database.
 *
 */
public class UploaderMatchSkipException extends UploaderException
{
    /**
     * The ids of the matching records
     */
    protected Vector<Object> matchIds;
    
    /**
     * The uploadTable that found the matches
     */
    protected UploadTable uploadTable;

    /**
     * The index of the row in the uploaded dataset that generated the matches.
     */
    protected int row;
    
    /**
     * @return the matchIds
     */
    public Vector<Object> getMatchIds()
    {
        return matchIds;
    }
    
    public UploaderMatchSkipException(final String msg, final List<DataModelObjBase> matches, int row, UploadTable uploadTable)
    {
        super(msg, UploaderException.ABORT_ROW);
        matchIds = new Vector<Object>();
        for (DataModelObjBase match : matches)
        {
            matchIds.add(match.getId());
        }
        this.row = row;
        this.uploadTable = uploadTable;
   }

    /**
     * @return the row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * @return the uploadTable
     */
    public UploadTable getUploadTable()
    {
        return uploadTable;
    }

    /**
     * @param matchIds the matchIds to set
     */
    public void setMatchIds(Vector<Object> matchIds)
    {
        this.matchIds = matchIds;
    }
    
    /**
     * @param cellVals
     * @param valsToShow
     * @param row
     * @return a hopefully human-readable description of the Exception
     */
    public static String makeMsg(final Vector<UploadTable.MatchRestriction> cellVals, int valsToShow, int row)
    {
        int maxValsToShow = 2;
        StringBuilder msg = new StringBuilder();
        msg.append(getResourceString("WB_ROW"));
        msg.append(" ");
        msg.append(String.valueOf(row));
        msg.append(": ");
        msg.append(getResourceString("WB_MULTIPLE_MATCH_SKIP"));
        msg.append(" (");
        for (int f = 0; f < maxValsToShow && f < valsToShow;)
        {
            msg.append(cellVals.get(f).getFieldName());
            msg.append(" = ");
            msg.append(cellVals.get(f++).getRestriction());
            if (f < cellVals.size() && f < valsToShow)
            {
                msg.append(", ");
            }
            else if (f < cellVals.size())
            {
                msg.append("...");
            }
        }
        msg.append(")");
        return msg.toString();
    }
}
