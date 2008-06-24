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

package edu.ku.brc.ui.forms.formatters;

import static edu.ku.brc.ui.UIHelper.createButton;
import static edu.ku.brc.ui.UIHelper.createCheckBox;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.dbsupport.DBTableInfo;
import edu.ku.brc.ui.AddRemoveEditPanel;
import edu.ku.brc.ui.CustomDialog;
import edu.ku.brc.ui.UIRegistry;

public class DataObjFieldFormatMultiplePanelBuilder extends DataObjFieldFormatPanelBuilder 
	{

	protected static final String FIELD_VALUE_COL;
	protected static final String DISPLAY_FORMAT_COL;
	protected static final String ELLIPSIS_BUTTON_COL = "";

	protected final String ellipsisButtonLabel = "...";

	protected JTable formatSwitchTbl;
    protected AddRemoveEditPanel controlPanel;
    
    static 
    {
    	FIELD_VALUE_COL     = getResourceString("DOF_FIELD_VALUE");
    	DISPLAY_FORMAT_COL  = getResourceString("DOF_DISPLAY_FORMAT");
    }
    
    DataObjFieldFormatMultiplePanelBuilder(DBTableInfo 				      			tableInfo,
			 							   AvailableFieldsComponent 		  		availableFieldsComp,
			 							   DataObjSwitchFormatterContainerIface 	formatContainer,	
			 							   JButton               		 	  		okButton,
			 							   UIFieldFormatterMgr 						uiFieldFormatterMgrCache)
	{
    	super(tableInfo, availableFieldsComp, formatContainer, okButton, uiFieldFormatterMgrCache);
	}

	protected void buildUI() 
	{
		CellConstraints cc = new CellConstraints();
		PanelBuilder pb = new PanelBuilder(new FormLayout("f:p:g",
				"10px,f:130px:g,10px,p,15px")/* , new FormDebugPanel() */);

		formatSwitchTbl = new JTable(new DefaultTableModel());
		formatSwitchTbl.getSelectionModel().addListSelectionListener(new RowListener());
		addTableModelListener((DefaultTableModel) formatSwitchTbl.getModel());
		fillWithObjFormatter(null);

		// tool bar to host the add and delete buttons
		createToolbar();

		// lay out components on main panel
		JScrollPane sp = new JScrollPane(formatSwitchTbl);
		// set minimum and preferred sizes so that table shrinks with the dialog
		sp.setMinimumSize(new Dimension(50, 5));
		sp.setPreferredSize(new Dimension(50, 5));

		pb.add(sp, cc.xy(1, 2));
		pb.add(controlPanel, cc.xy(1, 4));
		this.mainPanelBuilder = pb;
	}

	public void enableUIControls() 
	{
		controlPanel.getDelBtn().setEnabled(formatSwitchTbl.getSelectedRowCount() > 0);
		
		if (okButton != null)
		{
			okButton.setEnabled(isValidFormatter());
		}
	}

	public JPanel getPanel() 
	{
		return mainPanelBuilder.getPanel();
	}

	public PanelBuilder getMainPanelBuilder() 
	{
		return mainPanelBuilder;
	}

	protected void addTableModelListener(DefaultTableModel model)
	{
		TableModelListener tml = new TableModelListener()
		{
			public void tableChanged(TableModelEvent e)
			{
				int row = e.getFirstRow();
		        int column = e.getColumn();
		        DefaultTableModel model = (DefaultTableModel) e.getSource();
		        String columnName = model.getColumnName(column);
		        
		        if (columnName.equals(FIELD_VALUE_COL))
		        {
			        int formatColumn = formatSwitchTbl.getColumnModel().getColumnIndex(DISPLAY_FORMAT_COL);
			        DataObjDataFieldFormat format = (DataObjDataFieldFormat) model.getValueAt(row, formatColumn);
			        String value = (String) model.getValueAt(row, column);
		        	format.setValue(value);
		        	enableUIControls();
		        }
			}
		};
		model.addTableModelListener(tml);		
	}
	
	protected boolean isValidFormatter()
	{
		// check if there's an empty row in the switch formatter table
		DefaultTableModel model = (DefaultTableModel) formatSwitchTbl.getModel();
		
		if (model.getRowCount() == 0)
		{
			// formatter is not valid if there are no internal formatters attached to it
			return false;
		}
		
		for (int i = 0; i < model.getRowCount(); ++i)
		{
			for (int j = 0; j <= 1; ++j)
			{
				Object obj = model.getValueAt(i, j);
				if (obj == null || StringUtils.isEmpty(obj.toString()))
					return false;
			}
		}
		return true;
	}
	
	private DefaultTableModel getCleanTableModel() 
	{
		DefaultTableModel model = new DefaultTableModel() 
		{
			public boolean isCellEditable(int row, int column) 
			{
				return (column != 1);
			}
		};
		model.addColumn(FIELD_VALUE_COL);
		model.addColumn(DISPLAY_FORMAT_COL);
		model.addColumn(ELLIPSIS_BUTTON_COL);

		addTableModelListener(model);
		return model;
	}

	private void createToolbar() 
	{
		ActionListener addAL = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				DefaultTableModel model = (DefaultTableModel) formatSwitchTbl.getModel();
				DataObjSwitchFormatter fmt = formatContainer.getSelectedFormatter();
				DataObjDataFieldFormat fld = new DataObjDataFieldFormat();
				fmt.add(fld);
				model.addRow(new Object[] {fld.getValue(), fld, ellipsisButtonLabel});
	            enableUIControls();
			}
		};

		ActionListener delAL = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int formatColumn = formatSwitchTbl.getColumn(DISPLAY_FORMAT_COL).getModelIndex();
				DefaultTableModel model = (DefaultTableModel) formatSwitchTbl.getModel();
				DataObjSwitchFormatter fmt = formatContainer.getSelectedFormatter();
				int[] rows = formatSwitchTbl.getSelectedRows();
				// sort rows in reverse order otherwise removing the first rows
				// will mess up with the row numbers
				Integer[] intRows = new Integer[rows.length];
				for (int i = 0; i < rows.length; ++i) {
					intRows[i] = new Integer(rows[i]);
				}
				Arrays.sort(intRows, Collections.reverseOrder());
				for (int currentRow : intRows) {
					fmt.remove((DataObjDataFieldFormatIFace) model.getValueAt(currentRow, formatColumn));
					model.removeRow(currentRow);
				}
				formatSwitchTbl.clearSelection();
	            enableUIControls();
			}
		};

        controlPanel = new AddRemoveEditPanel(addAL, delAL, null);
        controlPanel.getAddBtn().setEnabled(true);
	}

	public void fillWithObjFormatter(DataObjSwitchFormatter switchFormatter) 
	{
		// display each formatter as a table row
		// DefaultTableModel tableModel = (DefaultTableModel)
		// formatSwitch.getModel();
		DefaultTableModel model = getCleanTableModel();

		if (switchFormatter != null) 
		{
			Vector<DataObjDataFieldFormatIFace> formatters = new Vector<DataObjDataFieldFormatIFace>(switchFormatter.getFormatters());
			for (DataObjDataFieldFormatIFace formatter : formatters) 
			{
				model.addRow(new Object[] { formatter.getValue(), formatter, ellipsisButtonLabel });
			}
		}

		formatSwitchTbl.setModel(model);
		setFormatSwitchTblColumnProperties();
	}
	
	private void setFormatSwitchTblColumnProperties()
	{
		// set details of 1st column (field values)
		TableColumnModel model = formatSwitchTbl.getColumnModel();
		TableColumn column = model.getColumn(model.getColumnIndex(FIELD_VALUE_COL));
		column.setMinWidth(20);
		column.setMaxWidth(300);
		column.setPreferredWidth(70);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		column.setCellRenderer(renderer);

		// set details of 3rd column (ellipsis buttons)
		column = model.getColumn(model.getColumnIndex(ELLIPSIS_BUTTON_COL));
		column.setCellRenderer(new EditDataObjFormatButtonRenderer());
		column.setCellEditor(new EditDataObjFormatButtonEditor(createCheckBox()));
		column.setMinWidth(20);
		column.setMaxWidth(20);
		column.setPreferredWidth(20);
	}

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) 
        {
            if (event.getValueIsAdjusting()) 
                return;
            
            enableUIControls();
        }
    }

    /*
	 * Table cell renderer that renders ellipsis button that opens format editor
	 */
	protected class EditDataObjFormatButtonRenderer extends JButton implements TableCellRenderer 
	{
		public EditDataObjFormatButtonRenderer() 
		{
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, 
													   boolean isSelected, boolean hasFocus, 
													   int row, int column) 
		{
			if (isSelected) 
			{
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else 
			{
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText(ellipsisButtonLabel);
			return this;
		}
	}

	/*
	 * Table cell editor that forwards events to rendered ellipsis buttons on
	 * the table
	 */
	protected class EditDataObjFormatButtonEditor extends DefaultCellEditor 
	{
		protected JButton button;
		private boolean isPushed;
		private JTable table;
		private int row;

		public EditDataObjFormatButtonEditor(JCheckBox checkBox) 
		{
			super(checkBox);
			button = createButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table,
													 Object value, boolean isSelected, 
													 int row, int column) 
		{
			if (isSelected) 
			{
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else 
			{
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			button.setText(ellipsisButtonLabel);
			isPushed = true;

			this.table = table;
			this.row = row;

			return button;
		}

		public Object getCellEditorValue() 
		{
			if (isPushed) {
				// get formatter object that corresponds to the pressed button
				int formatCol = table.getColumn(DISPLAY_FORMAT_COL).getModelIndex();
				Object fieldObj = table.getValueAt(row, formatCol);
				if (fieldObj instanceof DataObjDataFieldFormatIFace) {
					DataObjDataFieldFormatIFace formatter = (DataObjDataFieldFormatIFace) fieldObj;

					// open dialog to edit format
					DataObjFieldFormatSingleDlg dlg;
					dlg = new DataObjFieldFormatSingleDlg((Frame) UIRegistry.getTopWindow(), tableInfo, 
							availableFieldsComp, formatter, uiFieldFormatterMgrCache);
					dlg.setVisible(true);

					// save format back to table row data object
	        		if (dlg.getBtnPressed() == CustomDialog.OK_BTN)
	        		{
	    				DataObjSwitchFormatter fmt = formatContainer.getSelectedFormatter();
	    				DataObjDataFieldFormatIFace field = dlg.getSingleFormatter();
	    				fmt.set(row, field);
	    				table.setValueAt(field, row, formatCol);
	    				// get field value from table and assign it to newly created formatter
	    				int valueCol = table.getColumn(FIELD_VALUE_COL).getModelIndex();
	    				String value = (String) table.getValueAt(row, valueCol);
	    				field.setValue(value);
	    				// update ok button based on results
	        			enableUIControls();
	        		}
				}
			}
			isPushed = false;
			return new String(ellipsisButtonLabel);
		}

		public boolean stopCellEditing() 
		{
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() 
		{
			super.fireEditingStopped();
		}
	}
}
