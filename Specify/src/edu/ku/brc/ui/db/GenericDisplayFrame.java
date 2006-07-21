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
package edu.ku.brc.ui.db;

import static edu.ku.brc.ui.UICacheManager.getResourceString;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.ButtonBarBuilder;

import edu.ku.brc.af.core.NavBoxLayoutManager;
import edu.ku.brc.ui.UICacheManager;
import edu.ku.brc.ui.forms.MultiView;
import edu.ku.brc.ui.forms.ViewMgr;
import edu.ku.brc.ui.forms.persist.AltView;
import edu.ku.brc.ui.forms.persist.View;

/**
 * This is a "generic" or more specifically "configurable" search dialog class. This enables you to specify a form to be used to enter the search criteria
 * and then the search definition it is to use to do the search and display the results as a table in the dialog. The resulting class is to be passed in
 * on construction so the results of the search can actually yield a Hibernate object.
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class GenericDisplayFrame extends JFrame implements ActionListener
{
    private static final Logger log  = Logger.getLogger(GenericDisplayFrame.class);

    // Form Stuff
    protected MultiView      multiView;
    protected View           formView;
    protected List<String>   fieldNames;

    protected PropertyChangeListener propertyChangeListener = null;

    // Members needed for creating results
    protected String         className;
    protected String         idFieldName;
    protected String         displayName;

    // UI
    protected JButton        okBtn;

    protected JPanel         contentPanel;

    public GenericDisplayFrame(final String title,
                               final String className,
                               final String idFieldName) throws HeadlessException
    {
        this.setTitle(title);

        this.className   = className;
        this.idFieldName = idFieldName;
    }

    /**
     * Constructs a search dialog from form infor and from search info
     * @param viewSetName the viewset name
     * @param viewName the form name from the viewset
     * @param displayName the search name, this is looked up by name in the "search_config.xml" file
     * @param title the title (should be already localized before passing in)
     * @param className the name of the class to be created from the selected results
     * @param idFieldName the name of the field in the clas that is the primary key which is filled in from the search table id
     * @throws HeadlessException an exception
     */
    public GenericDisplayFrame(final String viewSetName,
                                final String viewName,
                                final String displayName,
                                final String title,
                                final String className,
                                final String idFieldName,
                                final boolean isEdit) throws HeadlessException
    {
        //super((Frame)UICacheManager.get(UICacheManager.FRAME), title, true);
        this.setTitle(title);

        this.className   = className;
        this.idFieldName = idFieldName;
        this.displayName  = displayName;

        createUI(viewSetName, viewName, title, isEdit ? AltView.CreationMode.Edit : AltView.CreationMode.View);

        setLocationRelativeTo((JFrame)(Frame)UICacheManager.get(UICacheManager.FRAME));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //this.setModal(false);
    }

    /**
     * Creates the Default UI
     *
     */
    protected void createUI(final String viewSetName,
                            final String viewName,
                            final String title,
                            final AltView.CreationMode mode)
    {
        boolean isEdit = mode == AltView.CreationMode.Edit;

        formView = ViewMgr.getView(viewSetName, viewName);
        if (formView != null)
        {
            multiView   = new MultiView(null, formView, mode, false, !isEdit);

        } else
        {
            log.error("Couldn't load form with name ["+viewSetName+"] Id ["+viewName+"]");
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));

        panel.add(multiView, BorderLayout.NORTH);
        contentPanel = new JPanel(new NavBoxLayoutManager(0,2));

        okBtn = new JButton(getResourceString(isEdit ? "Save" : "Close"));
        okBtn.addActionListener(this);
        getRootPane().setDefaultButton(okBtn);

        ButtonBarBuilder btnBuilder = new ButtonBarBuilder();
        btnBuilder.addGlue();
        btnBuilder.addGriddedButtons(new JButton[] { okBtn });

        panel.add(btnBuilder.getPanel(), BorderLayout.SOUTH);

        setContentPane(panel);
        pack();
    }



    /**
     * @return the multiview
     */
    public MultiView getMultiView()
    {
        return multiView;
    }

    /**
     * Set a listener to know when the dialog is closed
     * @param propertyChangeListener the listener
     */
    public void setCloseListener(final PropertyChangeListener propertyChangeListener)
    {
        this.propertyChangeListener = propertyChangeListener;
    }

    /**
     * Sets data into the dialog
     * @param dataObj the data object
     */
    public void setData(final Object dataObj)
    {
        multiView.setData(dataObj);
    }


    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        // Handle clicks on the OK and Cancel buttons.
        setVisible(false);
        propertyChangeListener.propertyChange(null);
        propertyChangeListener = null;
    }

}
