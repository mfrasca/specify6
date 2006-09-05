package edu.ku.brc.ui.renderers;

import java.awt.Component;
import java.lang.reflect.Method;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;


/**
 * Provides a facility for producing components appropriate for rendering the cells
 * of a list.  If the objects being rendered have a <code>getName()</code> method that
 * returns a <code>String</code>, then that method is used to provide text for the
 * cell components.  Otherwise, the {@link Object#toString()} method is used.
 *
 * @code_status Complete
 * @author jstewart
 */
public class NameBasedListCellRenderer extends DefaultListCellRenderer
{
	/**
	 * Returns a {@link JLabel} with the text set to the value of <code>value</code>'s
	 * <code>getName()</code> method, if one exists.  The value of
	 * <code>value.toString()</code> is used otherwise. 
	 *
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 * @param list the list the cells are contained in
	 * @param value the element to be rendered
	 * @param index the index of the element to be rendered
	 * @param isSelected indicator of the cell's selection state
	 * @param cellHasFocus indicator of the cell's focus state
	 * @return the paintable component
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		JLabel l = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
		
		Method getName = null;
		try
		{
			getName = value.getClass().getMethod("getName",new Class[]{});
		}
		catch( NoSuchMethodException e )
		{
			// do nothing, just move on
		}
		
		if( getName!=null )
		{
			try
			{
				String name = (String) getName.invoke(value,new Object[]{});
				l.setText(name);
			}
			catch( Exception e )
			{
				l.setText(value.toString());
			}
		}

		return l;
	}

}
