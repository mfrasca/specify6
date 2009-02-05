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
/**
 * 
 */
package edu.ku.brc.specify.datamodel;

import static edu.ku.brc.helpers.XMLHelper.addAttr;
import static edu.ku.brc.helpers.XMLHelper.getAttr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.hibernate.annotations.Index;

import edu.ku.brc.helpers.XMLHelper;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Feb 25, 2008
 *
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "spreport")
@org.hibernate.annotations.Table(appliesTo="spquery", indexes =
    {   @Index (name="SpReportNameIDX", columnNames={"Name"})
    })
public class SpReport extends DataModelObjBase
{
    private static final Logger log = Logger.getLogger(SpReport.class);

    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#toString()
     */
    @Override
    public String toString()
    {
        return getName();
    }

    protected Integer           spReportId;
    protected String            name;
    protected String            remarks;
    
    protected SpQuery           query;
    protected SpAppResource     appResource;
    protected SpecifyUser       specifyUser;

    protected Integer           repeatCount;
    protected String            repeatField;
    
 
    /**
     * 
     */
    public SpReport()
    {
        // no op
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#initialize()
     */
    @Override
    public void initialize()
    {
        super.init();
        
        spReportId       = null;
        name             = null;
        remarks          = null;
        query            = null;
        appResource      = null;
        specifyUser      = null;
        repeatCount      = null;
        repeatField      = null;
    }
    
    @Id
    @GeneratedValue
    @Column(name = "SpReportId", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getSpReportId()
    {
        return spReportId;
    }

    public void setSpReportId(Integer spReportId)
    {
        this.spReportId = spReportId;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    public void setSpecifyUser(SpecifyUser owner)
    {
        this.specifyUser = owner;
    }

    public void setQuery(SpQuery query)
    {
        this.query = query;
    }

    public void setAppResource(SpAppResource appResource)
    {
        this.appResource = appResource;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    
    /**
     * @return the name
     */
    @Column(name = "Name", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public String getName()
    {
        return name;
    }

    /**
     * @return the remarks
     */
    @Lob
    @Column(name = "Remarks", unique = false, nullable = true, insertable = true, updatable = true, length = 4096)
    public String getRemarks()
    {
        return remarks;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SpQueryID", unique = false, nullable = false, insertable = true, updatable = true)
    public SpQuery getQuery()
    {
        return query;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "AppResourceID", unique = false, nullable = false, insertable = true, updatable = true)
    public SpAppResource getAppResource()
    {
        return appResource;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SpecifyUserID", unique = false, nullable = false, insertable = true, updatable = true)
    public SpecifyUser getSpecifyUser() 
    {
        return this.specifyUser;
    }

    /**
     * @return the repeatCount;
     */
    @Column(name = "RepeatCount", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getRepeatCount()
    {
        return repeatCount;
    }
    
    /**
     * @param repeatCount the repeatCount to set.
     */
    public void setRepeatCount(Integer repeatCount)
    {
        this.repeatCount = repeatCount;
    }
    
    /**
     * @return the repeatField.
     */
    @Column(name = "RepeatField", unique = false, nullable = true, insertable = true, updatable = true)
    public String getRepeatField()
    {
        return repeatField;
    }
    
    @Transient
    public Object getRepeats()
    {
        return repeatCount != null ? repeatCount : repeatField;
    }
    
    public void setRepeats(Object repeats)
    {
        if (repeats == null)
        {
            setRepeatCount(null);
            setRepeatField(null);
        }
        else if (repeats instanceof Integer)
        {
            setRepeatCount((Integer )repeats);
            setRepeatField(null);
        }
        else if (repeats instanceof String)
        {
            setRepeatCount(null);
            setRepeatField((String )repeats);
        }
        else
        {
            log.error("invalid repeats parameter: " + repeats);
            setRepeatCount(null);
            setRepeatField(null);            
        }
    }
    /**
     * @param repeatField the repeatField to set.
     */
    public void setRepeatField(String repeatField)
    {
        this.repeatField = repeatField;
    }

    
    /**
     * Assuming object is attached to an open session, loads lazily-loaded members.
     */
    @Override
    public void forceLoad()
    {
        getQuery().forceLoad(false);
        getAppResource();
        getSpecifyUser();
    }
    
    //----------------------------------------------------------------------
    //-- DataModelObjBase
    //----------------------------------------------------------------------


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getDataClass()
     */
    @Override
    @Transient
    public Class<?> getDataClass()
    {
        return SpReport.class;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getId()
     */
    @Override
    @Transient
    public Integer getId()
    {
        return spReportId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    @Transient
    public int getTableId()
    {
        return getClassTableId();
    }
    
    /**
     * @return the Table ID for the class.
     */
    public static int getClassTableId()
    {
        return 519;
    }
       
    /**
     * @param sb
     */
    public void toXML(final StringBuilder sb)
    {
        sb.append("<report ");
        addAttr(sb, "name", name);
        addAttr(sb, "remarks", remarks);
        addAttr(sb, "repeatCount", repeatCount);
        addAttr(sb, "repeatField", repeatField);
        sb.append("\n");
        if (query != null)
        {
        	query.forceLoad();
        	query.toXML(sb);
        }
        sb.append("</report>\n");
    }

    /**
     * @param element
     */
    public void fromXML(final Element element)
    {
        name            = getAttr(element, "name", null);
        remarks     	=  getAttr(element, "remarks", null);
        String repeatCountStr = getAttr(element, "repeatCount", null);
        repeatCount = repeatCountStr == null ? null : Integer.valueOf(repeatCountStr);
        repeatField      = getAttr(element, "repeatField", null);
        
        Element qryNode = (Element)element.selectSingleNode("query");
        if (qryNode != null)
        {
        	query = new SpQuery();
        	query.fromXML(qryNode);
        }
        else
        {
        	query = null;
        }
    }

}
