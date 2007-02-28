/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.exporters;

import edu.ku.brc.specify.datamodel.RecordSet;

/**
 * @author jstewart
 * @code_status Alpha
 */
public interface RecordSetExporter
{
    public String getName();
    public String getIconName();
    public Class<?>[] getHandledClasses();
	public void exportRecordSet(RecordSet data);
}
