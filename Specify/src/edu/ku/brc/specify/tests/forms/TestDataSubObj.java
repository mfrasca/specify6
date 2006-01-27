package edu.ku.brc.specify.tests.forms;

import java.net.URL;
import java.util.Set;

import javax.swing.*;

import edu.ku.brc.specify.ui.*;

public class TestDataSubObj
{

    protected String  textField = "Sub Object";
    protected String  textArea  = "123456789012345678901234567890123456789012345678901234567890\n";
    protected boolean checkBox  = true;
    protected String  comboBox  = "Item 1";
    protected String  imagePath = "";
    protected String  imagePathURL = "http://localhost/Mybon_u0.jpg";
    protected String  browseField  = "this needs to be a path";
    protected java.util.Date date;
    protected String  noImage = "";
    
    protected Set subObjects;

    public TestDataSubObj()
    {
        imagePath = IconManager.getImagePath("../tests/forms/Mybon_u0.jpg").toString();
        date = new java.util.Date();
    }

    public Set getSubObjects() {
        return this.subObjects;
    }
    
    public void setSubObjects(Set subObjects) {
        this.subObjects = subObjects;
    }
    
    public boolean isCheckBox()
    {
        return checkBox;
    }


    public void setCheckBox(boolean checkBox)
    {
        this.checkBox = checkBox;
    }


    public String getComboBox()
    {
        return comboBox;
    }


    public void setComboBox(String comboBox)
    {
        this.comboBox = comboBox;
    }


    public String getImagePath()
    {
        return imagePath;
    }


    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }


    public String getTextArea()
    {
        return textArea;
    }


    public void setTextArea(String textArea)
    {
        this.textArea = textArea;
    }


    public String getTextField()
    {
        return textField;
    }


    public void setTextField(String textField)
    {
        this.textField = textField;
    }


    public String getBrowseField()
    {
        return browseField;
    }


    public void setBrowseField(String browseField)
    {
        this.browseField = browseField;
    }


    public java.util.Date getDate()
    {
        return date;
    }


    public void setDate(java.util.Date date)
    {
        this.date = date;
    }


    public String getImagePathURL()
    {
        return imagePathURL;
    }


    public void setImagePathURL(String imagePathURL)
    {
        this.imagePathURL = imagePathURL;
    }


    public String getNoImage()
    {
        return noImage;
    }


    public void setNoImage(String noImage)
    {
        this.noImage = noImage;
    }

}
