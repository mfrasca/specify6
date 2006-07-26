package edu.ku.brc.specify.datamodel;

import java.util.Date;

import edu.ku.brc.dbsupport.AttributeIFace;

/**
 * 
 */
public class ExternalResourceAttr implements AttributeIFace, java.io.Serializable
{

    // Fields

    protected Integer          attrId;
    protected String           strValue;
    protected Double           dblValue;
    protected Date             timestampCreated;
    protected Date             timestampModified;
    protected ExternalResource externalResource;
    protected AttributeDef     definition;

    // Constructors

    /** default constructor */
    public ExternalResourceAttr()
    {
    }

    /** constructor with id */
    public ExternalResourceAttr(Integer attrId)
    {
        this.attrId = attrId;
    }

    // Initializer
    public void initialize()
    {
        attrId = null;
        strValue = null;
        dblValue = null;
        timestampCreated = new Date();
        timestampModified = new Date();
        externalResource = null;
        definition = null;
    }

    // End Initializer

    // Property accessors

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getAttrId()
     */
    public Integer getAttrId()
    {
        return this.attrId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setAttrId(java.lang.Integer)
     */
    public void setAttrId(Integer attrId)
    {
        this.attrId = attrId;
    }
    
    /**
     * Verifies that the Attr type if correct 
     * @param type the type to check
     */
    protected void verifyType(AttributeIFace.FieldType type)
    {
        if (definition == null)
        {
            throw new RuntimeException("Attribute being accessed without a definition.");
        }
        if (definition.getDataType() != type.getType())
        {
            throw new RuntimeException("Attribute being accessed as ["+definition.getDataType()+"] when it is of type["+type.getType()+"]");
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getStrValue()
     */
    public String getStrValue()
    {
        verifyType(AttributeIFace.FieldType.StringType);
        
        return this.strValue;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setStrValue(java.lang.String)
     */
    public void setStrValue(String strValue)
    {
        verifyType(AttributeIFace.FieldType.StringType);
        this.strValue = strValue;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getDblValue()
     */
    public Double getDblValue()
    {
        verifyType(AttributeIFace.FieldType.DoubleType);
        return dblValue;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setDblValue(java.lang.Double)
     */
    public void setDblValue(Double value)
    {
        verifyType(AttributeIFace.FieldType.DoubleType);
        dblValue = value;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getFloatValue()
     */
    public Float getFloatValue()
    {
        verifyType(AttributeIFace.FieldType.FloatType);
        return new Float(dblValue);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setFloatValue(java.lang.Float)
     */
    public void setFloatValue(Float value)
    {
        verifyType(AttributeIFace.FieldType.FloatType);
        dblValue = new Double(value);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getIntValue()
     */
    public Integer getIntValue()
    {
        verifyType(AttributeIFace.FieldType.IntegerType);
        return new Integer(dblValue.intValue());
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setIntValue(java.lang.Integer)
     */
    public void setIntValue(Integer value)
    {
        verifyType(AttributeIFace.FieldType.IntegerType);
        dblValue = new Double(value);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getBoolValue()
     */
    public Boolean getBoolValue()
    {
        verifyType(AttributeIFace.FieldType.BooleanType);
        return new Boolean(dblValue.intValue() == 1);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setBoolValue(java.lang.Boolean)
     */
    public void setBoolValue(Boolean value)
    {
        verifyType(AttributeIFace.FieldType.BooleanType);
        dblValue = new Double(value ? 1.0 : 0.0);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getTimestampCreated()
     */
    public Date getTimestampCreated()
    {
        return this.timestampCreated;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setTimestampCreated(java.util.Date)
     */
    public void setTimestampCreated(Date timestampCreated)
    {
        verifyType(AttributeIFace.FieldType.FloatType);
        this.timestampCreated = timestampCreated;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#getTimestampModified()
     */
    public Date getTimestampModified()
    {
        return this.timestampModified;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.dbsupport.AttributeIFace#setTimestampModified(java.util.Date)
     */
    public void setTimestampModified(Date timestampModified)
    {
        this.timestampModified = timestampModified;
    }
    /**
     * 
     */
    public ExternalResource getExternalResource()
    {
        return this.externalResource;
    }

    public void setExternalResource(ExternalResource externalResource)
    {
        this.externalResource = externalResource;
    }

    /**
     * 
     */
    public AttributeDef getDefinition()
    {
        return this.definition;
    }

    public void setDefinition(AttributeDef definition)
    {
        this.definition = definition;
    }

    // Add Methods

    // Done Add Methods

    // Delete Methods

    // Delete Add Methods
}
