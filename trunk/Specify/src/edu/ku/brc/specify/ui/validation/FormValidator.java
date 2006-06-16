/* Filename:    $RCSfile: FormValidator.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2006/01/16 19:59:54 $
 *
 * This library is free software; you can redistribute it and/or
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
package edu.ku.brc.specify.ui.validation;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class manages all the validators for a single form. One or all the UI components
 * can be validated and the form can have its own set of validation rukes for enabling or
 * disabling any of the controls depending on the state of other "named" controls.
 * Both UI Controls and thier labels are registered by name. When a component is disabledits
 * label can also be disabled.
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class FormValidator implements ValidationListener, DataChangeListener
{
    private static Log log = LogFactory.getLog(FormValidator.class);

    private String name = ""; // Optional for debugging
    
    // Form validation
    protected JexlContext jc  = null;
    protected Expression  exp = null;

    protected List<FormValidationRuleIFace>         formRules   = new Vector<FormValidationRuleIFace>();
    protected Hashtable<String, DataChangeNotifier> dcNotifiers = new Hashtable<String, DataChangeNotifier>();
    protected List<UIValidator>                     validators  = new Vector<UIValidator>();

    protected Hashtable<String, Component>  fields      = new Hashtable<String, Component>();
    protected Hashtable<String, JLabel>     labels      = new Hashtable<String, JLabel>();

    protected boolean                       hasChanged  = false;
    //protected boolean                       isFormValid = false;
    protected UIValidatable.ErrorType       formValidationState = UIValidatable.ErrorType.Error;

    protected JButton                       okBtn       = null;

    protected boolean                       ignoreValidationNotifications = false;
    protected boolean                       okToDataChangeNotification    = true;

    // This is a list of listeners for when any data changes in the form
    protected List<DataChangeListener>      dcListeners  = new ArrayList<DataChangeListener>();
    protected List<ValidationListener>      valListeners = new ArrayList<ValidationListener>();

    /**
     *
     */
    public FormValidator()
    {
        jc  = JexlHelper.createContext();
        addRuleObjectMapping("form", this );

    }

    /**
     * Register which UI Component is the OK button, meaning which button will
     * be enabled when all the controls are validated
     * @param btn the control
     */
    public void registerOKButton(final JButton btn)
    {
        okBtn = btn;
        okBtn.setEnabled(false);
    }

    /**
     * Returns whether the form is valid
     * @return Returns whether it is alright to enable the OK btn
     */
    public boolean isFormValid()
    {
        return formValidationState == UIValidatable.ErrorType.Valid;
    }
    
    /**
     * Returns the state of the form, which really means... return the worst
     * "state" that was found from all the validators.
     * @return the state of the form
     */
    public UIValidatable.ErrorType getState()
    {
        return formValidationState;
    }

    /**
     * Returns whether the form has been changed
     * @return Returns whether the form has been changed
     */
    public boolean hasChanged()
    {
        return hasChanged;
    }

    /**
     * Sets whether the form will notify the listeners of data change notifications
     * @param okToDataChangeNotification whether to notify
     */
    public void setDataChangeNotification(final boolean okToDataChangeNotification)
    {
        this.okToDataChangeNotification = okToDataChangeNotification;
    }

    /**
     * Helper to that adds a validation for enabling or disabling control by name
     * @param name the name of the rule
     * @param rule the rule that will be validated
     */
    public void addEnableRule(final String name, final String rule)
    {
        formRules.add(new RuleExpression(name, rule));
    }

    /**
     * Helper to that adds a validation for enabling or disabling control by name
     * @param rule the rule that will be validated
     */
    public void addRule(final FormValidationRuleIFace rule)
    {
        formRules.add(rule);
    }



    /**
     * Tells all the validators that are required not to validate
     */
    public void setAllUIValidatorsToNew(boolean isNew)
    {         
        for (UIValidator uiv : validators)
        {
            uiv.setAsNew(isNew);
        }   
    }


    /**
     * Evaluate all the enable/disable rules and set the control and label
     */
    public boolean processFormRules()
    {
        boolean formIsOK = true;

        boolean debug = false;
        if (debug)
        {
            log.info(name+" ****** processFormRules  ");
            Map map = jc.getVars();
            Object[] keys = map.keySet().toArray();
            for (Object key : keys)
            {
                log.info(name+" ### ["+key+"]["+map.get(key).getClass().toString()+"]");
            }
        }

        for (FormValidationRuleIFace rule : formRules)
        {
            try
            {
                // Now evaluate the expression, getting the result
                boolean result = rule.evaluate(jc);
                log.info(name+" Result "+result+" for "+rule.getId()+"  ["+((RuleExpression)rule).expression.getExpression()+"]");
                if (rule.getScope() == FormValidationRuleIFace.Scope.Field)
                {
                    Component comp = getComp(rule.getId());
                    if (comp != null)
                    {
                        log.info(name+" comp.setEnabled("+result+") "+comp.getClass().toString());
                        comp.setEnabled(result);
                    }

                    JLabel lbl = labels.get(rule.getId());
                    if (lbl != null)
                    {
                        lbl.setEnabled(result);
                    }

                } else if (rule.getScope() == FormValidationRuleIFace.Scope.Form)
                {
                    formIsOK &= result;
                }


            } catch (Exception ex)
            {
                log.error(name+" "+ex.toString());
                formIsOK = false;
                //ex.printStackTrace();
            }
        }
        return formIsOK;
    }

    /**
     * @param textField textField to be hooked up
     * @param id id of control
     * @param isRequired whether the field must be filled in
     * @param valType the type of validation to do
     * @param valStr the validation rule where the subject is its name
     * @param changeListenerOnly indicates whether to create a validator
     */
    public void hookupTextField(final JTextField       textField,
                                final String           id,
                                final boolean          isRequired,
                                final UIValidator.Type valType,
                                final String           valStr,
                                final boolean          changeListenerOnly)
    {

        fields.put(id, textField);

        UIValidator.Type type = isRequired ? UIValidator.Type.Changed : valType;

        UIValidator uiv;
        if (valStr == null)
        {
            uiv = createValidator(id, textField, valType);
        } else
        {
            uiv = changeListenerOnly ? null : createValidator(id, textField, isRequired, type, valStr);
        }
        DataChangeNotifier dcn = new DataChangeNotifier(id, textField, uiv);
        dcn.addDataChangeListener(this);

        dcNotifiers.put(id, dcn);

        if (type == UIValidator.Type.Changed || isRequired || changeListenerOnly)
        {
            textField.getDocument().addDocumentListener(dcn);

        } else if (type == UIValidator.Type.Focus)
        {
            textField.addFocusListener(dcn);

        } else
        {
           // Do nothing for UIValidator.Type.OK
        }

        addRuleObjectMapping(id, textField);
    }

    /**
     * Hooks up generic component to be validated
     * @param comp component to be hooked up
     * @param id id of control
     * @param isRequired whether the field must be filled in
     * @param valType the type of validation to do
     * @param valStr the validation rule where the subject is its name
     * @param changeListenerOnly indicates whether to create a validator
     * @return the component passed in
     */
    public DataChangeNotifier hookupComponent(final JComponent       comp,
                                              final String           id,
                                              final boolean          isRequired,
                                              final UIValidator.Type valType,
                                              final String           valStr,
                                              final boolean          changeListenerOnly)
    {

        fields.put(id, comp);

        UIValidator uiv = null;
        if (!changeListenerOnly)
        {
            if (isNotEmpty(valStr))
            {
                uiv = createValidator(id, comp, isRequired, valType, valStr);
            } else
            {
                uiv = createValidator(id, comp, valType);
            }
        }
        DataChangeNotifier dcn = new DataChangeNotifier(id, comp, uiv);
        dcn.addDataChangeListener(this);

        dcNotifiers.put(id, dcn);

        addRuleObjectMapping(id, comp);

        return dcn;
    }

    /**
     * Create a label and register it
     * @param name the logical name
     * @param labelStr the string to be shown in the label
     * @return returns the new JLabel
     */
    public JLabel createLabel(final String name, final String labelStr)
    {
        JLabel lbl = new JLabel(labelStr);
        labels.put(name, lbl);
        return lbl;
    }

    /**
     * Register the label by name
     * @param name the logical name of the label
     * @param lbl the label component
     * @return return the label that is passed in
     */
    public JLabel addUILabel(final String name, final JLabel lbl)
    {
        labels.put(name, lbl);
        return lbl;
    }

    /**
     * Gets a component by id and returns it as a ValTextField
     * @param id the id of the component
     * @return returns the component by id
     */
    public ValTextField getTextField(final String id)
    {
        Component comp = fields.get(id);
        if (comp instanceof ValTextField)
        {
            return (ValTextField)comp;
        }
        throw new RuntimeException("Desired JComponent ["+id+"]is not of type ValTextField"+comp);
    }

    /**
     * Returns a component by name
     * @param id the name of the component
     * @return Returns a component by name
     */
    public Component getComp(final String id)
    {
        return fields.get(id);
    }

    /**
     * Adds a generic Component by name and return it
     * @param name the logical name of the component
     * @param comp the component to add
     * @return returns the passed in component after it is registered
     */
    public Component addUIComp(final String id, final Component comp)
    {
        fields.put(id, comp);
        addRuleObjectMapping(id, comp);

        log.info(" Adding ["+id+"]["+comp.getClass().toString()+"] to validator.");

        return comp;
    }
    
    /**
     * Returns the Text of the label for a control's Id (strips ':' from end of string)
     * @param id the unique identifier
     * @return the text of the label
     */
    public String getLabelTextForId(final String id)
    {
        JLabel label = labels.get(id);
        if (label != null)
        {
            String labelStr = label.getText();
            if (labelStr.endsWith(":"))
            {
                return labelStr.substring(0, labelStr.length()-1);
            }
            return labelStr;
        }
        return "";
    }

    /**
     * Adds a component (or object) that can be referred to oby name in a validatoin rule
     * @param id the name of the component
     * @param comp the component
     */
    @SuppressWarnings("unchecked")
    public void addRuleObjectMapping(final String id, final Object comp)
    {
        if (isNotEmpty(id) && comp != null)
        {
            jc.getVars().put(id, comp);
        } else
        {
            throw new RuntimeException("id["+id+"] or Comp["+comp+"] is null.");
        }
    }

    /**
     * Creates a validator for the control (usually a JTextField) that can have a string as a default value
     *
     * @param componentName the name of the component
     * @param comp the component to be validated (MUST implement UIValidatable)
     * @param isRequired whether the component must have a value
     * @param valType the type of validation to occur
     * @param valStr the default value
     * @return the validator for the control
     */
    protected UIValidator createValidator(String          componentName,
                                         JComponent       comp,
                                         boolean          isRequired,
                                         UIValidator.Type valType,
                                         String           valStr)
    {
        if (comp instanceof UIValidatable)
        {
            UIValidator validator = new UIValidator(comp, valType, valStr);
            validator.setJc(jc);
            validator.addValidationListener(this);
            validators.add(validator);

            return validator;

        } else
        {
            throw new RuntimeException("Component is NOT an UIValidatable "+comp);
        }
    }

    /**
     * Create a validator for any generic UI control (defaults to be validated as Type.OK)
     * @param componentName the name of the component
     * @param comp the component to be validated (MUST implement UIValidatable)
     * @param valType the type of validation to occur
     * @return the validator for the control
     */
    public UIValidator createValidator(String componentName, JComponent comp, UIValidator.Type valType)
    {
        UIValidator validator = new UIValidator(comp, valType);
        validator.setJc(jc);
        validator.addValidationListener(this);
        validators.add(validator);
        return validator;
    }

    /**
     * This will evaluate the forms "rules" but none of the fields, instead
     * it asks each validatable field if it is ok (because they will have already
     * been validated.
     */
    protected void checkForValidForm()
    {
        formValidationState = processFormRules() ? UIValidatable.ErrorType.Valid : UIValidatable.ErrorType.Error;
        
        //log.info(name+" checkForValidForm -> formValidationState - processFormRules ["+formValidationState+"]");

        if (formValidationState == UIValidatable.ErrorType.Valid)
        {
            for (UIValidator uiv : validators)
            {
                if (uiv.isInError())
                {
                    UIValidatable.ErrorType state = uiv.getUIV().getState();
                    
                    // Assumes Error is th worst, so if it is "less than" an Error i.e. Incompete
                    // then we don't want to override an error with a lesser state 
                    if (formValidationState != UIValidatable.ErrorType.Error)
                    {
                        formValidationState = state;
                    }
                    
                    if (state == UIValidatable.ErrorType.Error)
                    {
                        // Break for an Erro, but keep going for Incomplete
                        // to see if there are any errors
                        //break; 
                    }
                    
                }
            }
        }

        turnOnOKButton(hasChanged && formValidationState == UIValidatable.ErrorType.Valid);
    }

    /**
     * Validates all or some of the field
     * @param validateAll indicates all field should be validated
     * @param type if validateAll is false, then validate only the fields with a
     * validator of this type.
     */
    protected void validateForm(boolean validateAll, UIValidator.Type valType)
    {
        ignoreValidationNotifications = true;

        formValidationState = processFormRules() ? UIValidatable.ErrorType.Valid : UIValidatable.ErrorType.Error;

        // We need to go ahead and validate everything even if processFormRules fails
        // because the user will need the visual feed back on the form for which fields are in error
        for (DataChangeNotifier dcn : dcNotifiers.values())
        {
            UIValidator uiv = dcn.getUIV();
            if (uiv != null)
            {
                dcn.manualCheckForDataChanged();

                // Make sure we validate the fields that only get validated the type matches
                if (uiv != null && (validateAll || uiv.getType() == valType))
                {
                    if (!uiv.validate())
                    {
                        UIValidatable.ErrorType state = uiv.getUIV().getState();
                        
                        // Assumes Error is th worst, so if it is "less than" an Error i.e. Incompete
                        // then we don't want to override an error with a lesser state 
                        if (formValidationState != UIValidatable.ErrorType.Error)
                        {
                            formValidationState = state;
                        }
                        if (state == UIValidatable.ErrorType.Error)
                        {
                            // Break for an Erro, but keep going for Incomplete
                            // to see if there are any errors
                            //break; 
                        }
                        
                    }

                }
            }
        }

        // when validating for OK we always leave it enabled
        //turnOnOKButton(true);

        ignoreValidationNotifications = false;
    }

    /**
     * Validate all the fields when the OK or Apply button is pressed.
     * Note that we turn off all the Validation notifications because we don't want them firing when
     * we manually call it.
     */
    public void validateFormForOK()
    {
        validateForm(false, UIValidator.Type.OK);
    }

    /**
     * Validate all the fields, period.
     */
    public void validateForm()
    {
        // Because we call it manually it will turn off validation notifications
        validateForm(true, UIValidator.Type.Changed); // second arg doesn't matter
    }

    /**
     * Reset all dataChangedNotifiers
     */
    public void resetFields()
    {
        for (Enumeration e=dcNotifiers.elements();e.hasMoreElements();)
        {
            DataChangeNotifier dcn = (DataChangeNotifier)e.nextElement();
            //UIValidator        uiv = dcn.getUIV();
            //if (uiv != null && uiv.getType() == UIValidator.Type.OK)
            //{
                dcn.reset();
            //}
        }
    }

    /**
     * Creates and register a DataChangeNotifier
     * @param id the id
     * @param comp the component
     * @param uiv the UI validator
     * @return the dcn
     */
    public DataChangeNotifier createDataChangeNotifer(String id, Component comp, UIValidator uiv)
    {
        DataChangeNotifier dcn = new DataChangeNotifier(id, comp, uiv);
        dcn.addDataChangeListener(this);
        dcNotifiers.put(id, dcn);

        if (uiv != null && !validators.contains(uiv))
        {
            validators.add(uiv);
        }
        return dcn;
    }

    /**
     * Clean up internal data
     */
    public void cleanUp()
    {
        jc  = null;
        exp = null;

        for (DataChangeNotifier dcn : dcNotifiers.values())
        {
            dcn.cleanUp();
        }
        dcNotifiers.clear();
        validators.clear();

        fields.clear();
        labels.clear();

        for (FormValidationRuleIFace rule : formRules)
        {
            if (rule instanceof RuleExpression)
            {
                ((RuleExpression)rule).cleanUp();
            }
        }
        formRules.clear();

        okBtn = null;
    }


    /**
     * Adds validation listener
     * @param l the listener
     */
    public void addDataChangeListener(final DataChangeListener l)
    {
        dcListeners.add(l);
    }

    /**
     * Removes validation listener
     * @param l the listener
     */
    public void removeDataChangeListener(final DataChangeListener l)
    {
        dcListeners.remove(l);
    }

    /**
     * Adds validation listener
     * @param l the listener
     */
    public void addValidationListener(final ValidationListener l)
    {
        valListeners.add(l);
    }

    /**
     * Removes validation listener
     * @param l the listener
     */
    public void removeValidationListenerListener(final ValidationListener l)
    {
        valListeners.remove(l);
    }

    /**
     * Returns the name of the validator (optional)
     * @return the name of the validator (optional)
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the validator (optional for debugging)
     * @param name the name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Returns the Map of Notifiers
     * @return the Map of Notifiers
     */
    public Map<String, DataChangeNotifier> getDCNs()
    {
        return dcNotifiers;
    }
    
    /**
     * Dumps the State of the Validation State
     * @param doBrief just the state of validator, when false it dumps everthing about all the DCNs and validators
     */
    public void dumpState(final boolean doBrief)
    {
        String displayName = (name != null ? name : "No Name");
        
        if (doBrief)
        {
            log.info("*** "+isFormValid()+"  "+displayName);
        } else
        {
            StringBuilder strBuf = new StringBuilder(64);
            log.info("\n------------"+displayName+"-------------");
            log.info("Valid: "+isFormValid());
            log.info("\n");
            
            int maxLen = 0;
            for (DataChangeNotifier dcn : dcNotifiers.values())
            {
                int len = dcn.getId().length();
                maxLen = Math.max(maxLen, len);
            }
            
            for (DataChangeNotifier dcn : dcNotifiers.values())
            {
                String nm = dcn.getId();
                strBuf.setLength(0); 
                strBuf.append(nm);
                for (int i=0;i<=(maxLen-nm.length());i++) strBuf.append(" ");
                UIValidator uiv = dcn.getUIV();
                if (uiv != null)
                {
                    strBuf.append("UIV: ");
                    strBuf.append(uiv.getType());
                    strBuf.append(" ");
                    strBuf.append(uiv.getUIV().getState());
                }

                log.info(strBuf.toString());
            }
            log.info("-------------------------");
        }
            
    }
    
    //-----------------------------------------------------
    // ValidationListener
    //-----------------------------------------------------


    /**
     * Helper methods for turning on the "default" OK button after a validation was completed.
     * @param itsOKToEnable indicates whether it is OK to enable the "OK" button
     */
    protected void turnOnOKButton(final boolean itsOKToEnable)
    {
        //log.info(name+" hasChanged "+hasChanged+"  itsOKToEnable "+itsOKToEnable+ " hasBtn: " + (okBtn != null));

        if (okBtn != null)
        {
            okBtn.setEnabled(itsOKToEnable);
        }
    }

   /* (non-Javadoc)
     * @see ValidationListener#wasValidated(UIValidator)
     */
    public void wasValidated(final UIValidator validator)
    {
       
        // When the form has been asked manually to be validated then ignore the notifications
        if (!ignoreValidationNotifications)
        {
            checkForValidForm();
            
            for (ValidationListener vcl : valListeners)
            {
                vcl.wasValidated(validator);
            }
        }

    }

    //-----------------------------------------------------
    // DataChangeListener
    //-----------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.validation.DataChangeListener#dataChanged(java.lang.String, java.awt.Component, edu.ku.brc.specify.ui.validation.DataChangeNotifier)
     */
    public void dataChanged(final String name, final Component comp, DataChangeNotifier dcn)
    {

        if (!okToDataChangeNotification)
        {
            return;
        }
        //log.debug("DataChangeListener "+name + " was changed");

        // Here is the big assumption:
        // The validation system is built on the premise that when a control changes
        // it is validated before it calls all of it's listeners to tell them it has been changed.
        //
        // This means that this form has already recieved a callback "wasValidated" and validated
        // the entire form. we don't have to do anything here bu flip the boolean that data has changed
        // and then see if the button can enabled
        // But if the DataChangeNotifier didn't have a UIValidator then we need to re-validate the
        // form because "wasValidated" wouldn't have been called

        UIValidator uiv = dcn != null ? dcn.getUIV() : null;
        if (uiv == null)
        {
            validateForm();

        } else
        {
            // OK, now turn on the button if the form is (or has been valid)
            // and we were just waiting for (I guess in "wasValidated" we could assume that the form
            // data had changed, but I don't want to do that)
            //if (isFormValid && !hasChanged)
            //{
            //    turnOnOKButton(true);
            //}
        }

        // Notify anyone else who is listening to the form for changes

        for (DataChangeListener dcl : dcListeners)
        {
            dcl.dataChanged(name, comp, dcn);
        }

        hasChanged = true;

        turnOnOKButton(formValidationState == UIValidatable.ErrorType.Valid);
    }

    /**
     * Sets whether the data in the form has changed
     * @param hasChanged whether the data in the form has changed
     */
    public void setHasChanged(boolean hasChanged)
    {
        this.hasChanged = hasChanged;
    }
    
    
}
