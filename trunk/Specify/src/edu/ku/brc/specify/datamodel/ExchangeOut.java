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
package edu.ku.brc.specify.datamodel;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "exchangeout")
@org.hibernate.annotations.Table(appliesTo="exchangeout", indexes =
    {   @Index (name="ExchangeOutdateIDX", columnNames={"ExchangeDate"}),
        @Index (name="ExChgOutColMemIDX", columnNames={"CollectionMemberID"})
    })
public class ExchangeOut extends CollectionMember implements java.io.Serializable {

    // Fields    

     protected Integer  exchangeOutId;
     protected Calendar exchangeDate;
     protected Short    quantityExchanged;
     protected String   descriptionOfMaterial;
     protected String   remarks;
     protected String   text1;
     protected String   text2;
     protected Float    number1;
     protected Float    number2;
     protected Boolean  yesNo1;
     protected Boolean  yesNo2;
     
     protected AddressOfRecord addressOfRecord;
     protected Agent           agentSentTo;
     protected Agent           agentCatalogedBy;
     protected Set<Shipment>  shipments;


    // Constructors

    /** default constructor */
    public ExchangeOut() {
        //
    }
    
    /** constructor with id */
    public ExchangeOut(Integer exchangeOutId) {
        this.exchangeOutId = exchangeOutId;
    }
   
    
    

    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        
        exchangeOutId    = null;
        exchangeDate     = null;
        quantityExchanged = null;
        descriptionOfMaterial = null;
        remarks          = null;
        text1            = null;
        text2            = null;
        number1          = null;
        number2          = null;
        yesNo1           = null;
        yesNo2           = null;
        addressOfRecord  = null;
        agentSentTo      = null;
        agentCatalogedBy = null;
        shipments        = new HashSet<Shipment>();
    }
    // End Initializer

    // Property accessors

    /**
     *      * Primary key
     */
    @Id
    @GeneratedValue
    @Column(name = "ExchangeOutID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getExchangeOutId()
    {
        return this.exchangeOutId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.exchangeOutId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return ExchangeOut.class;
    }
    
    public void setExchangeOutId(Integer exchangeOutId)
    {
        this.exchangeOutId = exchangeOutId;
    }

    /**
     *      * Date exchange was sent
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ExchangeDate", unique = false, nullable = true, insertable = true, updatable = true)
    public Calendar getExchangeDate() 
    {
        return this.exchangeDate;
    }
    
    public void setExchangeDate(Calendar exchangeDate) 
    {
        this.exchangeDate = exchangeDate;
    }

    /**
     *      * Number of items sent
     */
    @Column(name = "QuantityExchanged", unique = false, nullable = false, insertable = true, updatable = true)
    public Short getQuantityExchanged() 
    {
        return this.quantityExchanged;
    }
    
    public void setQuantityExchanged(Short quantityExchanged) 
    {
        this.quantityExchanged = quantityExchanged;
    }

    /**
     * 
     */
    @Column(name = "DescriptionOfMaterial", unique = false, nullable = true, insertable = true, updatable = true, length = 120)
    public String getDescriptionOfMaterial() 
    {
        return this.descriptionOfMaterial;
    }
    
    public void setDescriptionOfMaterial(String descriptionOfMaterial) 
    {
        this.descriptionOfMaterial = descriptionOfMaterial;
    }

    /**
     * 
     */
    @Lob
    @Column(name = "Remarks", length = 4096)
    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    /**
     *      * User definable
     */
    @Column(name = "Text1", length=300, unique = false, nullable = true, insertable = true, updatable = true)
    public String getText1()
    {
        return this.text1;
    }

    public void setText1(String text1)
    {
        this.text1 = text1;
    }

    /**
     *      * User definable
     */
    @Column(name = "Text2", length=300, unique = false, nullable = true, insertable = true, updatable = true)
    public String getText2()
    {
        return this.text2;
    }

    public void setText2(String text2)
    {
        this.text2 = text2;
    }

    /**
     *      * User definable
     */
    @Column(name = "Number1", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
    public Float getNumber1()
    {
        return this.number1;
    }

    public void setNumber1(Float number1)
    {
        this.number1 = number1;
    }

    /**
     *      * User definable
     */
    @Column(name = "Number2", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
    public Float getNumber2()
    {
        return this.number2;
    }

    public void setNumber2(Float number2)
    {
        this.number2 = number2;
    }

    /**
     * User definable
     */
    @Column(name = "YesNo1", unique = false, nullable = true, updatable = true, insertable = true)
    public Boolean getYesNo1()
    {
        return this.yesNo1;
    }

    public void setYesNo1(Boolean yesNo1)
    {
        this.yesNo1 = yesNo1;
    }

    /**
     *      * User definable
     */
    @Column(name="YesNo2",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getYesNo2()
    {
        return this.yesNo2;
    }

    public void setYesNo2(Boolean yesNo2)
    {
        this.yesNo2 = yesNo2;
    }

    /**
     *      * Agent ID of organization material was sent to
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SentToOrganizationID", unique = false, nullable = false, insertable = true, updatable = true)
    public Agent getAgentSentTo()
    {
        return this.agentSentTo;
    }

    public void setAgentSentTo(Agent agentSentTo)
    {
        this.agentSentTo = agentSentTo;
    }

    /**
     *      * Agent ID of person who recorded  the exchange
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CatalogedByID", unique = false, nullable = false, insertable = true, updatable = true)
    public Agent getAgentCatalogedBy()
    {
        return this.agentCatalogedBy;
    }

    public void setAgentCatalogedBy(Agent agentCatalogedBy)
    {
        this.agentCatalogedBy = agentCatalogedBy;
    }

    /**
     *      * Shipment information for the exchange
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "exchangeOut")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<Shipment> getShipments()
    {
        return this.shipments;
    }

    public void setShipments(Set<Shipment> shipments)
    {
        this.shipments = shipments;
    }

    /**
     * @return the addressOfRecord
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "AddressOfRecordID", unique = false, nullable = true, insertable = true, updatable = true)
    public AddressOfRecord getAddressOfRecord()
    {
        return addressOfRecord;
    }

    /**
     * @param addressOfRecord the addressOfRecord to set
     */
    public void setAddressOfRecord(AddressOfRecord addressOfRecord)
    {
        this.addressOfRecord = addressOfRecord;
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
        return 40;
    }

}
