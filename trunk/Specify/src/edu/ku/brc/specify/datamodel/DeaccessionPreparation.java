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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "deaccessionpreparation")
public class DeaccessionPreparation extends DataModelObjBase implements java.io.Serializable {

    // Fields    

     protected Integer deaccessionPreparationId;
     protected Short quantity;
     protected String remarks;
     protected Deaccession deaccession;
     protected Set<LoanReturnPhysicalObject> loanReturnPhysicalObjects;
     protected Preparation preparation;


    // Constructors

    /** default constructor */
    public DeaccessionPreparation() {
        //
    }
    
    /** constructor with id */
    public DeaccessionPreparation(Integer deaccessionPreparationId) {
        this.deaccessionPreparationId = deaccessionPreparationId;
    }
   
    
    

    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        deaccessionPreparationId = null;
        quantity = null;
        remarks = null;
        deaccession = null;
        loanReturnPhysicalObjects = new HashSet<LoanReturnPhysicalObject>();
        preparation = null;
    }
    // End Initializer

    // Property accessors

    /**
     *      * Primary key
     */
    @Id
    @GeneratedValue
    @Column(name = "DeaccessionPreparationID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getDeaccessionPreparationId() {
        return this.deaccessionPreparationId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.deaccessionPreparationId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return DeaccessionPreparation.class;
    }
    
    public void setDeaccessionPreparationId(Integer deaccessionPreparationId) {
        this.deaccessionPreparationId = deaccessionPreparationId;
    }

    /**
     *      * Number of specimens deaccessioned (necessary for lots)
     */
    @Column(name = "Quantity", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     */
    @Lob
    @Column(name="Remarks", unique=false, nullable=true, updatable=true, insertable=true)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     *      * The deaccession
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "DeaccessionID", unique = false, nullable = false, insertable = true, updatable = true)
    public Deaccession getDeaccession() {
        return this.deaccession;
    }
    
    public void setDeaccession(Deaccession deaccession) {
        this.deaccession = deaccession;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "deaccessionPreparation")
    public Set<LoanReturnPhysicalObject> getLoanReturnPhysicalObjects() {
        return this.loanReturnPhysicalObjects;
    }
    
    public void setLoanReturnPhysicalObjects(Set<LoanReturnPhysicalObject> loanReturnPhysicalObjects) {
        this.loanReturnPhysicalObjects = loanReturnPhysicalObjects;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "PreparationID", unique = false, nullable = true, insertable = true, updatable = true)
    public Preparation getPreparation()
    {
        return this.preparation;
    }

    public void setPreparation(Preparation preparation)
    {
        this.preparation = preparation;
    }

    // Add Methods

    public void addLoanReturnPhysicalObjects(final LoanReturnPhysicalObject loanReturnPhysicalObject)
    {
        this.loanReturnPhysicalObjects.add(loanReturnPhysicalObject);
        loanReturnPhysicalObject.setDeaccessionPreparation(this);
    }

    // Done Add Methods

    // Delete Methods

    public void removeLoanReturnPhysicalObjects(final LoanReturnPhysicalObject loanReturnPhysicalObject)
    {
        this.loanReturnPhysicalObjects.remove(loanReturnPhysicalObject);
        loanReturnPhysicalObject.setDeaccessionPreparation(null);
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
        return 36;
    }

}
