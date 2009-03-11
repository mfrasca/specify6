/*
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 *
 */
package edu.ku.brc.specify.ui;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Toolkit;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import edu.ku.brc.ui.UIRegistry;

/**
 * Used for enforcing the length of data in a text field.
 * 
 * @author rod
 *
 * @code_status Alpha
 *
 * Jun 12, 2007
 *
 */
public class LengthInputVerifier extends InputVerifier
{
    protected String caption;
    protected int    maxLength;
    
    /**
     * @param caption
     * @param maxLength
     */
    public LengthInputVerifier(final String caption, final int maxLength)
    {
        this.caption   = caption;
        this.maxLength = maxLength;
    }
    
    /* (non-Javadoc)
     * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
     */
    @Override
    public boolean verify(JComponent comp)
    {
        boolean isOK = ((JTextComponent)comp).getText().length() <= maxLength;
        if (!isOK)
        {
            String msg = String.format(getResourceString("UI_NEWDATA_TOO_LONG"), new Object[] { caption, maxLength } );
            UIRegistry.getStatusBar().setErrorMessage(msg);
            Toolkit.getDefaultToolkit().beep();
            
        } else
        {
            UIRegistry.getStatusBar().setText("");
        }
        return isOK;
    }
    // This is for testing it is 256 characters
    // 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456
    // 12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234561234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345612345678901234567890
}