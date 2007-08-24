/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane.wb.wbuploader;

import java.util.Vector;

/**
 * @author timbo
 *
 * @code_status Alpha
 *
 * Used to specify mappings from workbench columns to databases where relationships are required.
 */
public class UploadMappingDefRel extends UploadMappingDef
{
	/**
	 * The name of the table on the 'to' side of the relationship.
	 */
	protected String relatedTable = null;
	/**
	 * Info on relevant fields in the table on the 'from' side of the relationship.
	 */
	protected Vector<ImportMappingRelFld> localFields;
	/**
	 * Info on fields in the table on the 'to' side of the relationship. 
	 */
	protected Vector<ImportMappingRelFld> relatedFields;
	/**
	 * for 1-to-manys - CollectorFirstName1, CollectorFirstName2 ... 
	 */
	protected Integer sequence = null; 
	/**
	 * name of field used to store sequence - currently only applicable for order in collectors and authors...
	 */
	protected String sequenceFld = null; 
	/**
	 * @param table
	 * @param field
	 * @param relatedTable
	 */
	public UploadMappingDefRel(String table, String field, String relatedTable)
	{
		super(table, field);
		this.relatedTable = relatedTable;
		relatedFields = new Vector<ImportMappingRelFld>();
		localFields = new Vector<ImportMappingRelFld>();
	}
	/**
	 * @param table
	 * @param field
	 * @param order
	 * @param relatedTable
	 * @param relatedField
	 */
	public UploadMappingDefRel(String table, String field, String relatedTable,  Integer sequence, String sequenceFld)
	{
		super(table, field);
		this.relatedTable = relatedTable;
		this.sequence = sequence;
		this.sequenceFld = sequenceFld;
		relatedFields = new Vector<ImportMappingRelFld>();
		localFields = new Vector<ImportMappingRelFld>();
	}
	/**
	 * @return the sequence
	 */
	public Integer getSequence()
	{
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence)
	{
		this.sequence = sequence;
	}

	/**
	 * @return the relatedFields
	 */
	public Vector<ImportMappingRelFld> getRelatedFields()
	{
		return relatedFields;
	}
	/**
	 * @param relatedField the relatedField to add
	 */
	public void addRelatedField(String relatedField, int idx)
	{
		relatedFields.add(new ImportMappingRelFld(relatedField, idx));
	} 
	/**
	 * @return the relatedTable
	 */
	public String getRelatedTable()
	{
		return relatedTable;
	}
	/**
	 * @param relatedTable the relatedTable to set
	 */
	public void setRelatedTable(String relatedTable)
	{
		this.relatedTable = relatedTable;
	}
	
    /**
     * @author timbo
     *
     * @code_status Alpha
     *
     *Simple class to store the name and workbench column index for uploaded fields.
     */
    public class ImportMappingRelFld
	{
		protected String fieldName;
		protected int fldIndex; //column index for the field's value
		/**
		 * @param fieldName
		 * @param fldIndex
		 */
		public ImportMappingRelFld(String fieldName, int fldIndex)
		{
			super();
			this.fieldName = fieldName;
			this.fldIndex = fldIndex;
		}
		/**
		 * @return the fieldName
		 */
		public String getFieldName()
		{
			return fieldName;
		}
		/**
		 * @param fieldName the fieldName to set
		 */
		public void setFieldName(String fieldName)
		{
			this.fieldName = fieldName;
		}
		/**
		 * @return the fldIndex
		 */
		public int getFldIndex()
		{
			return fldIndex;
		}
		/**
		 * @param fldIndex the fldIndex to set
		 */
		public void setFldIndex(int fldIndex)
		{
			this.fldIndex = fldIndex;
		}
	}
	/**
	 * @return the sequenceFld
	 */
	public String getSequenceFld()
	{
		return sequenceFld;
	}
	/**
	 * @return the fields
	 */
	public Vector<ImportMappingRelFld> getLocalFields()
	{
		return localFields;
	}
	
	/**
	 * @param fieldName
	 */
	public void addLocalField(String fieldName, int fldIndex)
	{
		localFields.add(new ImportMappingRelFld(fieldName, fldIndex));
	}
}
