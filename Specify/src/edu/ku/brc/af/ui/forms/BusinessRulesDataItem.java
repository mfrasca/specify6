package edu.ku.brc.af.ui.forms;

public class BusinessRulesDataItem
{

    protected Object  data      = null;
    protected boolean isChecked = false;
    
    public BusinessRulesDataItem(Object data)
    {
        this.data = data;
    }

    
    public Object getData()
    {
        return data;
    }


    public void setData(Object data)
    {
        this.data = data;
    }


    public boolean isChecked()
    {
        return isChecked;
    }


    public void setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }


    @Override
    public String toString()
    {
        return data.toString();
    }
}
