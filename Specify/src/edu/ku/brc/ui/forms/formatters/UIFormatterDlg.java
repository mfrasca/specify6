/*
 * Copyright (C) 2008  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 *
 */
package edu.ku.brc.ui.forms.formatters;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.dbsupport.DBFieldInfo;
import edu.ku.brc.dbsupport.DBTableInfo;
import edu.ku.brc.ui.CustomDialog;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Jan 27, 2008
 *
 */
public class UIFormatterDlg extends CustomDialog
{
	protected DBFieldInfo fieldInfo = null;
	protected UIFieldFormatterIFace selectedFormat = null;
	protected int initialFormatSelectionIndex = 0;
	protected boolean formatIsNew = false;
    protected Vector<UIFieldFormatterIFace> deletedFormats = new Vector<UIFieldFormatterIFace>(); 
    
    protected JLabel	 sampleLabel;
    protected JList      formatList;
    protected JTextField formatTF;
    protected JCheckBox  byYearCB;
    protected JButton    deleteBtn;
    
    protected ListSelectionListener formatListSelectionListener = null;
    protected DocumentListener formatChangedDL = null;
    
    
    /**
     * @param dialog
     * @param title
     * @param isModal
     * @param whichBtns
     * @param contentPanel
     * @throws HeadlessException
     */
    public UIFormatterDlg(Frame                 frame, 
    					  DBFieldInfo           fieldInfo, 
    					  int                   initialFormatSelectionIndex) 
    	throws HeadlessException
    {
        super(frame, getResourceString("FFE_DLG_TITLE"), true, OKCANCELHELP, null); //I18N 
        this.fieldInfo = fieldInfo;
        this.initialFormatSelectionIndex = initialFormatSelectionIndex;
    }

    /*
     * 
     */
    private UIFieldFormatterIFace getFormatByPattern(String pattern)
    {
    	DefaultListModel model = (DefaultListModel) formatList.getModel();
    	Enumeration en = model.elements();
    	while (en.hasMoreElements())
    	{
    		Object elem = en.nextElement();
    		if (elem instanceof UIFieldFormatterIFace)
    		{
    			UIFieldFormatterIFace fmt = (UIFieldFormatterIFace) elem;
    			if (fmt.toPattern().equals(pattern))
    			{
    				// found one element on format list that is the same as the one being set
    				return fmt;
    			}
    		}
    	}
    	return null;
    }

    /**
     * Tries to find an existing format with the same pattern
     * If one doesn't exist, creates a new one
     */
    private void findOrCreateFormat(String pattern)
    {
    	UIFieldFormatterIFace fmt = getFormatByPattern(pattern);
    	if (fmt != null)
    	{
    		setSelectedFormat(fmt);
    		formatIsNew = false;
    		return;
    	}
    	
    	// else: create a new format from the pattern
    	try 
    	{
    		// FIXME: not calling setSelectedFormat() intentionally not to change the value of the 
    		selectedFormat = UIFieldFormatterMgr.factory(pattern, fieldInfo);
    		formatIsNew = true;
        	updateSample();
    	}
    	catch (UIFieldFormatterParsingException e1) 
    	{
    		setSelectedFormat(null);
    		formatIsNew = false;
    		setError(getResourceString("FFE_INVALID_FORMAT")); 
    	}
    }
    
    private void updateSample() 
    {
    	if (selectedFormat != null) 
    	{
        	sampleLabel.setText(selectedFormat.getSample());
    	}
    	else
    	{
        	sampleLabel.setText(" "); 
    	}

    	resetError();
    }
    
    private void resetError() 
    {
        sampleLabel.setForeground(Color.black);
    	updateUIEnabled();
    }

    private void setError(String message) 
    {
        sampleLabel.setForeground(Color.red);
        sampleLabel.setText(message);
        setSelectedFormat(null);
    	updateUIEnabled();
    }

    private void hookByYearCheckBoxListener()
    {
    	ItemListener il = new ItemListener()
    	{
    		public void itemStateChanged(ItemEvent e)
    		{
    			if (e.getItem() == byYearCB)
    			{
    				// do nothing if format is new because it will be destroyed when the next key is pressed
    				// the correct byYear state of newly created formatters is corrected when the ok button is pressed
    				if (!formatIsNew && (selectedFormat != null))
    				{
    					selectedFormat.setByYear(e.getStateChange() == ItemEvent.SELECTED);
    				}
    			}
    		}
    	};
    	byYearCB.addItemListener(il);
    }
    
    private void hookFormatListMouseListener()
    {
    	MouseAdapter mAdp = new MouseAdapter()
    	{
    		public void mouseClicked(MouseEvent e)
    		{
    			if(e.getClickCount() == 2)
    			{
    				int index = formatList.locationToIndex(e.getPoint());
    				formatList.ensureIndexIsVisible(index);
    				okButtonPressed();
    			}
    		}
    	};
    	
    	formatList.addMouseListener(mAdp);
    }
    
    private void hookFormatTextChangeListener()
    {
    	if (formatChangedDL == null)
    	{
            formatChangedDL = new DocumentListener()
            {
                public void removeUpdate(DocumentEvent e) 
                {
                	changed(e);
                }
                public void insertUpdate(DocumentEvent e)
                {
                	changed(e);
                }
                public void changedUpdate(DocumentEvent e)
                {
                	changed(e);
                }

                private void changed(DocumentEvent e)
                {
                	String text = formatTF.getText();
                	findOrCreateFormat(text);
                	// also change selection on format list to match typed format
                	changeListSelection(text);
                }            	
            };

            formatTF.getDocument().addDocumentListener(formatChangedDL);
    	}
    }
    
	private void hookFormatListSelectionListener() 
	{
		if (formatListSelectionListener == null)
		{
	        formatListSelectionListener = new ListSelectionListener()
	        {
	        	public void valueChanged(ListSelectionEvent e)
	        	{
	        	    if (e.getValueIsAdjusting()) 
	        	    	return;
	
	        	    JList theList = (JList) e.getSource();
	        	    if (theList.isSelectionEmpty())
	        	    {
	        	    	// just clear up sample
		        	    updateSample();
	        	    	return;
	        	    }
	
	        	    int index = theList.getSelectedIndex();
	        	    if (index == 0) 
	        	    {
	        	    	// set selected formatter to null
	        	    	setSelectedFormat(null);
	        	    }
	        	    else
	        	    {
	        	    	// object has got to be an instance of UIFieldFormatterIFace
	        	    	// only one that isn't is the index==0 (it's a string, and is treated above)
	        	    	setSelectedFormat((UIFieldFormatterIFace) theList.getSelectedValue());
	        	    }

	        	    setFormatText();
	        	    updateSample();
	        	}
	        };
		}
		
        formatList.addListSelectionListener(formatListSelectionListener);
	}

	private void setSelectionOnFormatListWithoutListener(Object element)
	{
    	// un-hook selection listener for a bit while we change selection to avoid loop
		formatList.removeListSelectionListener(formatListSelectionListener);
		if (element != null)
		{
    		formatIsNew = false;
			formatList.setSelectedValue(element, true);
			setByYearSelected(element);
		}
		else
		{
			formatList.clearSelection();
			setByYearSelected(null);
		}
		// hook listener again
		formatList.addListSelectionListener(formatListSelectionListener);
	}
	private void setSelectionOnFormatListWithoutListener(int index)
	{
		formatIsNew = false;

    	// un-hook selection listener for a bit while we change selection to avoid loop
		formatList.removeListSelectionListener(formatListSelectionListener);
		formatList.setSelectedIndex(0);
		setByYearSelected(null);
		// hook listener again
		formatList.addListSelectionListener(formatListSelectionListener);
	}
    
	/**
	 * 
	 */
	private void setByYearSelected(Object obj)
	{
		if (obj instanceof UIFieldFormatterIFace)
		{
			UIFieldFormatterIFace fmt = (UIFieldFormatterIFace) obj;
			byYearCB.setSelected(fmt.getByYear());
		}
	}
	
    /*
     * Changes selection in format list according to given text
     */
    private void changeListSelection(String text)
    {
    	if (text.length() == 0) 
    	{
    		// empty string: select first item (no formatting)
    		setSelectionOnFormatListWithoutListener(0);
    		return;
    	}
    	
    	UIFieldFormatterIFace fmt = getFormatByPattern(text);
		setSelectionOnFormatListWithoutListener((Object) fmt);
    }
    
    private void setFormatText()
    {
    	String text = (selectedFormat == null)? "" : selectedFormat.toString(); 
    	formatTF.getDocument().removeDocumentListener(formatChangedDL);
    	formatTF.setText(text);
    	formatTF.getDocument().addDocumentListener(formatChangedDL);
    	updateSample();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CustomDialog#createUI()
     */
    @Override
    public void createUI()
    {
        super.createUI();
        
        CellConstraints cc = new CellConstraints();
        PanelBuilder    pb = new PanelBuilder(new FormLayout("10px,p:g,10px,p,10px",  
        		"10px,"       + // empty space on top of panel 
        		"p,p,"        + // table and field names 
        		"p,p,"        + // field type and length 
        		"10px,"       + // --- separator 
        		"p,"          + // sample panel 
        		"10px,"       + // --- separator 
        		"p,p,"        + // format label and text field 
        		"p,"          + // by year checkbox  
        		"5px,"        + // --- separator 
        		"p,"          + // available formats label 
        		"f:100px:g,"  + // available formats list 
        		"10px,"       + // --- separator 
        		"p,"          + // delete button 
        		"15px"          // --- separator 
        		)/*, new FormDebugPanel()*/);
        
        //"10px,p,12px,p,p,p,10px,p,f:p:g,6px,p,6px"

        // get formatters for field
        List<UIFieldFormatterIFace> fmtrs = new Vector<UIFieldFormatterIFace>(
        		UIFieldFormatterMgr.getFormatterList(fieldInfo.getTableInfo().getClassObj(), fieldInfo.getName()));
        Collections.sort(fmtrs, new Comparator<UIFieldFormatterIFace>() {
            public int compare(UIFieldFormatterIFace o1, UIFieldFormatterIFace o2)
            {
                return o1.toPattern().compareTo(o2.toPattern());
            }
        });

        // table and field titles
        String typeStr = fieldInfo.getType();
        typeStr = typeStr.indexOf('.') > -1 ? StringUtils.substringAfterLast(fieldInfo.getType(), ".") : typeStr;

        JLabel tableTitleLbl = new JLabel(getResourceString("FFE_TABLE") + ": " + 
        		fieldInfo.getTableInfo().getTitle(), SwingConstants.LEFT); 
        JLabel fieldTitleLbl = new JLabel(getResourceString("FFE_FIELD") + ": " + 
        		fieldInfo.getTitle(),  SwingConstants.LEFT); 
        JLabel fieldTypeLbl = new JLabel(getResourceString("FFE_TYPE") + ": " + 
        		typeStr, SwingConstants.LEFT);
        JLabel fieldLengthLbl = new JLabel(getResourceString("FFE_LENGTH") + ": " + 
        		Integer.toString(fieldInfo.getLength()), SwingConstants.LEFT);
        
        
        // sample panel
        sampleLabel = new JLabel("YYYY-AAA-BB", SwingConstants.LEFT); 
        JPanel samplePanel = new JPanel();
        samplePanel.setBorder(BorderFactory.createTitledBorder(getResourceString("FFE_SAMPLE"))); 
        samplePanel.add(sampleLabel);

        // format text field
        formatTF = new JTextField();
        formatTF.setColumns(20);
        hookFormatTextChangeListener();
        
        DefaultListModel listModel = new DefaultListModel();

        // add blank format, i.e, none
        listModel.addElement("None"); 
        // add available formatters
        for (UIFieldFormatterIFace format : fmtrs)
        {
        	listModel.addElement(format);
        }
        
        formatList = new JList(listModel);
        formatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hookFormatListSelectionListener();
        hookFormatListMouseListener();
        
        byYearCB = new JCheckBox(getResourceString("FFE_BY_YEAR_CHECKBOX")); 
        hookByYearCheckBoxListener();
        
        ActionListener deleteListener = new ActionListener()
        {
        	public void actionPerformed(ActionEvent e) 
        	{ 	
        		int index = formatList.getSelectedIndex();
        		DefaultListModel model = (DefaultListModel) formatList.getModel();
        		model.removeElement(selectedFormat);
        		index = (index >= model.getSize())? index - 1: index;
        		formatList.setSelectedIndex(index);
        		deletedFormats.add(selectedFormat);
        	}
        };
        
        PanelBuilder deletePB = new PanelBuilder(new FormLayout("l:p", "p"));  
        deleteBtn = new JButton(getResourceString("FFE_DELETE")); 
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(deleteListener);
        deletePB.add(deleteBtn);
        
        // formatting key panel
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new BoxLayout(keyPanel, BoxLayout.Y_AXIS));
        keyPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("FFE_HELP"))); 
        keyPanel.add(new JLabel("  Use these patterns to specify the field format:")); 
        keyPanel.add(new JLabel(" ")); 
        keyPanel.add(new JLabel("    AAAA - alphanumeric")); 
        keyPanel.add(new JLabel("    aaaa - alphabetic only")); 
        keyPanel.add(new JLabel("    NNNN - integer number")); 
        keyPanel.add(new JLabel("    NNN.NN - number with decimal places")); 
        keyPanel.add(new JLabel("    #### - auto-incrementing number")); 
        keyPanel.add(new JLabel("    DD - day of the month from 01 to 31")); 
        keyPanel.add(new JLabel("    MM - month number from 01 to 12")); 
        keyPanel.add(new JLabel("    MMM - abbreviated month name (Jan-Dec)")); 
        keyPanel.add(new JLabel("    MMMM - full month name (January-December)")); 
        keyPanel.add(new JLabel("    YY - any year with 2 digits")); 
        keyPanel.add(new JLabel("    YYYY - any year with 4 digits")); 
        keyPanel.add(new JLabel("    YEAR - current year with 4 digits")); 
        keyPanel.add(new JLabel(" ")); 
        keyPanel.add(new JLabel(" ")); 
        keyPanel.add(new JLabel("  As separators, use '/', '-', '_' or space")); 
        keyPanel.add(new JLabel(" ")); 

        int y = 2; // leave first row blank 
        pb.add(tableTitleLbl,  cc.xyw(2, y, 3)); y += 1;
        pb.add(fieldTitleLbl,  cc.xyw(2, y, 3)); y += 1;
        pb.add(fieldTypeLbl,   cc.xyw(2, y, 3)); y += 1;
        pb.add(fieldLengthLbl, cc.xyw(2, y, 3)); y += 2;
        
        int y2 = y; // align formatting legend key with row marked by y2 (see below)
        pb.add(samplePanel, cc.xy(2, y)); y += 2;  

        pb.add(new JLabel("Type Format Pattern:", SwingConstants.LEFT), cc.xy(2,y)); y += 1; 
        pb.add(formatTF, cc.xy(2,y)); y += 1;
        pb.add(byYearCB, cc.xy(2,y)); y += 2;
        
        pb.add(new JLabel(getResourceString("FFE_AVAILABLE_FORMATS"), SwingConstants.LEFT), cc.xy(2,y)); y += 1; 
        pb.add(new JScrollPane(formatList), cc.xy(2,y)); y += 2;

        pb.add(deletePB.getPanel(), cc.xy(2,y)); y += 2;
        
        pb.add(keyPanel, cc.xywh(4, y2, 1, 11)); y2 += 1;
        
        contentPanel = pb.getPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        pack();
        
        // after all is created, set initial selection on format list 
        formatList.setSelectedIndex(initialFormatSelectionIndex);
    }

   
    /**
     * 
     */
    protected void updateUIEnabled()
    {
    	// enable ok button only if currently selected format is valid 
    	boolean formatOk = (selectedFormat != null) || (formatList.getSelectedIndex() == 0);
        okBtn.setEnabled(formatOk);
        
        // delete button is enabled only if selected format is not a system one
        // cannot delete system formatters because there may be references to them on various forms 
        boolean deleteEnabled = false;
        Object listSelectionValue = formatList.getSelectedValue();
        if (listSelectionValue instanceof UIFieldFormatterIFace) 
        {
        	UIFieldFormatterIFace fmt = (UIFieldFormatterIFace) listSelectionValue;
        	deleteEnabled = !fmt.isSystem();
        }
        deleteBtn.setEnabled(deleteEnabled);
        
        // by year checkbox is enabled if there's one YEAR and one auto-number (###) in the format
        boolean byYearEnabled = (selectedFormat != null) && (selectedFormat.byYearApplies());
        byYearCB.setEnabled(byYearEnabled);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CustomDialog#okButtonPressed()
     */
    @Override
    protected void okButtonPressed()
    {
    	UIFieldFormatterMgr instance = UIFieldFormatterMgr.getInstance();
    	
    	// remove non-system formatters marked for deletion
    	Iterator<UIFieldFormatterIFace> it = deletedFormats.iterator();
    	while (it.hasNext()) 
    	{
    		instance.removeFormatter(it.next());
    	}
    	
    	// save formatter if new
    	if (formatIsNew) 
    	{
    		// if format is new, maybe the "by year" field hasn't been flipped correctly 
    		// (i.e. if formatter was created (ie. by a key press) after the checkbox was clicked
    		selectedFormat.setByYear(byYearCB.isSelected());
    		
    		// add formatter to list of existing ones and save it
    		instance.addFormatter(selectedFormat);
    		instance.save();
    	}
        
        super.okButtonPressed();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CustomDialog#cleanUp()
     */
    @Override
    public void cleanUp()
    {
        // TODO Auto-generated method stub
        super.cleanUp();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CustomDialog#getOkBtn()
     */
    @Override
    public JButton getOkBtn()
    {
        // TODO Auto-generated method stub
        return super.getOkBtn();
    }

	public UIFieldFormatterIFace getSelectedFormat() {
		return selectedFormat;
	}

	public void setSelectedFormat(UIFieldFormatterIFace selectedFormat) {
		this.selectedFormat = selectedFormat;
		setByYearSelected(selectedFormat);
	}

	/**
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception
    {
        UIFormatterDlg dlg = new UIFormatterDlg(null, null, 0);
        dlg.pack();
        dlg.setVisible(true);
    }
}
