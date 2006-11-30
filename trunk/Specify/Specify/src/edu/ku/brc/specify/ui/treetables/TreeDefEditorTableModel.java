/**
 * 
 */
package edu.ku.brc.specify.ui.treetables;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.ku.brc.specify.datamodel.TreeDefItemIface;
import edu.ku.brc.util.RankBasedComparator;

/**
 *
 * @code_status Beta
 * @author jstewart
 * @version %I% %G%
 */
@SuppressWarnings("serial")
public class TreeDefEditorTableModel <I extends TreeDefItemIface<?,?,I>>
										extends AbstractTableModel
{
	public static final int NAME_COL     = 0;
	public static final int REMARKS_COL  = 1;
	public static final int FULLNAME_COL = 2;
	public static final int ENFORCED_COL = 3;
	
	protected Vector<I> tableData;
	
	/**
	 *
	 *
	 */
	public TreeDefEditorTableModel(Set<? extends I> defItems)
	{
		tableData = new Vector<I>(defItems);
		Collections.sort(tableData,new RankBasedComparator());
	}
	
	/**
	 *
	 *
	 * @see javax.swing.table.TableModel#getColumnCount()
	 * @return
	 */
	public int getColumnCount()
	{
		return 4;
	}

	/**
	 *
	 *
	 * @see javax.swing.table.TableModel#getRowCount()
	 * @return
	 */
	public int getRowCount()
	{
		return tableData.size();
	}

	/**
	 *
	 *
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		I row = tableData.get(rowIndex);
		switch(columnIndex)
		{
			case NAME_COL:
			{
				return row.getName();
			}
			case REMARKS_COL:
			{
				return row.getRemarks();
			}
			case FULLNAME_COL:
			{
				return row.getIsInFullName();
			}
			case ENFORCED_COL:
			{
				return row.getIsEnforced();
			}
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		I row = tableData.get(rowIndex);
		switch(columnIndex)
		{
			case NAME_COL:
			{
				row.setName((String)aValue);
				break;
			}
			case REMARKS_COL:
			{
				row.setRemarks((String)aValue);
				break;
			}
			case FULLNAME_COL:
			{
				row.setIsInFullName((Boolean)aValue);
				break;
			}
			case ENFORCED_COL:
			{
				row.setIsEnforced((Boolean)aValue);
				break;
			}
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		switch(columnIndex)
		{
			case NAME_COL:
			case REMARKS_COL:
			{
				return String.class;
			}
			case FULLNAME_COL:
			case ENFORCED_COL:
			{
				return Boolean.class;
			}
		}
		return null;
	}

	@Override
	public String getColumnName(int column)
	{
		switch(column)
		{
			case NAME_COL:
			{
				return "Name";
			}
			case REMARKS_COL:
			{
				return "Remarks";
			}
			case FULLNAME_COL:
			{
				return "In Full Name";
			}
			case ENFORCED_COL:
			{
				return "Enforced";
			}
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if( tableData.get(rowIndex).getParent() == null )
		{
			if( columnIndex == ENFORCED_COL )
			{
				return false;
			}
			return true;
		}
		return true;
	}

	public void add(int index, I element)
	{
		tableData.add(index,element);
		fireTableRowsInserted(index,index);
	}

	public void add(I o)
	{
		tableData.add(o);
		int index = tableData.indexOf(o);
		fireTableRowsInserted(index,index);
	}

	public I get(int index)
	{
		return tableData.get(index);
	}

	public int indexOf(Object elem)
	{
		return tableData.indexOf(elem);
	}

	public I remove(int index)
	{
		I deleted = tableData.remove(index);
		fireTableRowsDeleted(index,index);
		return deleted;
	}

	public boolean remove(Object o)
	{
		int index = tableData.indexOf(o);
		boolean deleted = tableData.remove(o);
		
		if(deleted)
		{
			fireTableRowsDeleted(index,index);
		}
		return deleted;
	}
}
