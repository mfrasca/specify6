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
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

import edu.ku.brc.dbsupport.AttributeIFace;
import edu.ku.brc.ui.forms.FormDataObjIFace;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterIFace;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterMgr;

/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "collectionobject", uniqueConstraints = {
        @UniqueConstraint(columnNames={"CollectionID", "CatalogNumber"} ) 
        } 
)
@org.hibernate.annotations.Table(appliesTo="collectionobject", indexes =
    {   @Index (name="FieldNumberIDX", columnNames={"fieldNumber"}),
        @Index (name="CatalogedDateIDX", columnNames={"CatalogedDate"}),
        @Index (name="CatalogNumberIDX", columnNames={"CatalogNumber"})
    })
public class CollectionObject extends DataModelObjBase implements java.io.Serializable, Comparable<CollectionObject>
{

    // Fields

    protected Long                          collectionObjectId;
    protected String                        fieldNumber;
    protected String                        description;
    protected String                        text1;
    protected String                        text2;
    protected Float                         number1;
    protected Float                         number2;
    protected Boolean                       yesNo1;
    protected Boolean                       yesNo2;
    protected Integer                       countAmt;
    protected String                        remarks;
    protected String                        name;
    protected String                        modifier;
    protected Calendar                      catalogedDate;
    protected String                        catalogedDateVerbatim;
    protected String                        guid;
    // protected String altCatalogNumber;
    protected Integer                       groupPermittedToView;
    protected Boolean                       deaccessioned;
    protected String                        catalogNumber;
    protected Integer                       visibility;
    protected String                        visibilitySetBy;
    protected CollectingEvent               collectingEvent;
    protected Set<CollectionObjectCitation> collectionObjectCitations;
    protected Set<AttributeIFace>           attrs;
    protected Set<Preparation>              preparations;
    protected Set<Determination>            determinations;
    protected Set<ProjectCollectionObject>  projectCollectionObjects;
    // protected Set<DeaccessionPreparation> deaccessionPreparations;
    protected Set<OtherIdentifier>          otherIdentifiers;
    protected Collection                    collection;
    protected Accession                     accession;
    protected Agent                         cataloger;
    protected Set<Attachment>               attachments;
    protected Container                     container;
    protected ColObjAttributes              colObjAttributes;
    
    protected Set<CollectionRelationship>   leftSideRels;
    protected Set<CollectionRelationship>   rightSideRels;

    // Constructors

    /** default constructor */
    public CollectionObject()
    {
        // do nothing
    }

    /** constructor with id */
    public CollectionObject(Long collectionObjectId) {
        this.collectionObjectId = collectionObjectId;
    }




    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        collectionObjectId    = null;
        fieldNumber           = null;
        description           = null;
        text1                 = null;
        text2                 = null;
        number1               = null;
        number2               = null;
        yesNo1                = null;
        yesNo2                = null;
        countAmt              = null;
        remarks               = null;
        name                  = null;
        modifier              = null;
        catalogedDate         = null;
        catalogedDateVerbatim = null;
        guid                  = null;
        //altCatalogNumber = null;
        groupPermittedToView  = null;
        deaccessioned         = null;
        catalogNumber         = null;
        visibility            = null;
        visibilitySetBy       = null; 
        collectingEvent       = null;
        collectionObjectCitations = new HashSet<CollectionObjectCitation>();
        attrs                 = new HashSet<AttributeIFace>();
        preparations          = new HashSet<Preparation>();
        determinations        = new HashSet<Determination>();
        projectCollectionObjects = new HashSet<ProjectCollectionObject>();
        //deaccessionPreparations = new HashSet<DeaccessionPreparation>();
        otherIdentifiers      = new HashSet<OtherIdentifier>();
        collection            = null;
        accession             = null;
        cataloger             = null;
        attachments           = new HashSet<Attachment>();
        container             = null;
        
        // XXX Temp TESTING
        colObjAttributes      = new ColObjAttributes();
        colObjAttributes.initialize();
        
        leftSideRels          = new HashSet<CollectionRelationship>();
        rightSideRels         = new HashSet<CollectionRelationship>();

    }
    // End Initializer
    
    public void initForSearch()
    {
        collection = new Collection();
        collection.initialize();
        
        accession = new Accession();
        accession.initialize();
        
        cataloger  = new Agent();
        cataloger.initialize();
    }

    // Property accessors

    /**
     *
     */
    @Id
    @GeneratedValue
    @Column(name = "CollectionObjectID", unique = false, nullable = false, insertable = true, updatable = true)
    public Long getCollectionObjectId() {
        return this.collectionObjectId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Long getId()
    {
        return this.collectionObjectId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return CollectionObject.class;
    }

    public void setCollectionObjectId(Long collectionObjectId) {
        this.collectionObjectId = collectionObjectId;
    }

    /**
     *      * BiologicalObject (Bird, Fish, etc)
     */
    @Column(name = "FieldNumber", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getFieldNumber() {
        return this.fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    /**
     *      * Image, Sound, Preparation, Container(Container Label?)
     */
    @Column(name = "Description", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *      * User definable
     */
    @Column(name = "Text1", length=300, unique = false, nullable = true, insertable = true, updatable = true)
    public String getText1() {
        return this.text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    /**
     *      * User definable
     */
    @Column(name = "Text2", length=300, unique = false, nullable = true, insertable = true, updatable = true)
    public String getText2() {
        return this.text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    /**
     *      * User definable
     */
    @Column(name = "Number1", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
    public Float getNumber1() {
        return this.number1;
    }

    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    /**
     *      * User definable
     */
    @Column(name = "Number2", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
    public Float getNumber2() {
        return this.number2;
    }

    public void setNumber2(Float number2) {
        this.number2 = number2;
    }

    /**
     *      * User definable
     */
    @Column(name="YesNo1",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getYesNo1() {
        return this.yesNo1;
    }

    public void setYesNo1(Boolean yesNo1) {
        this.yesNo1 = yesNo1;
    }

    /**
     *      * User definable
     */
    @Column(name="YesNo2",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getYesNo2() {
        return this.yesNo2;
    }

    public void setYesNo2(Boolean yesNo2) {
        this.yesNo2 = yesNo2;
    }

    /**
     *
     */
    @Column(name = "CountAmt", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getCountAmt() {
        return this.countAmt;
    }

    public void setCountAmt(Integer countAmt) {
        this.countAmt = countAmt;
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
     *
     */
    @Column(name = "Name", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    @Column(name = "Modifier", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CatalogedDate", unique = false, nullable = true, insertable = true, updatable = true)
    public Calendar getCatalogedDate() {
        return this.catalogedDate;
    }

    public void setCatalogedDate(Calendar catalogedDate) {
        this.catalogedDate = catalogedDate;
    }

    /**
     *
     */
    @Column(name = "CatalogedDateVerbatim", length=32, unique = false, nullable = true, insertable = true, updatable = true)
    public String getCatalogedDateVerbatim() {
        return this.catalogedDateVerbatim;
    }

    public void setCatalogedDateVerbatim(String catalogedDateVerbatim) {
        this.catalogedDateVerbatim = catalogedDateVerbatim;
    }

    /**
     *
     */
    @Column(name = "GUID", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

//    /**
//     *
//     */
//    public String getAltCatalogNumber() {
//        return this.altCatalogNumber;
//    }
//
//    public void setAltCatalogNumber(String altCatalogNumber) {
//        this.altCatalogNumber = altCatalogNumber;
//    }

    /**
     *
     */
    @Column(name = "GroupPermittedToView", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getGroupPermittedToView() {
        return this.groupPermittedToView;
    }

    public void setGroupPermittedToView(Integer groupPermittedToView) {
        this.groupPermittedToView = groupPermittedToView;
    }

    /**
     *
     */
    @Column(name = "Deaccessioned", unique = false, nullable = true, insertable = true, updatable = true)
    public Boolean getDeaccessioned() {
        return this.deaccessioned;
    }

    public void setDeaccessioned(Boolean deaccessioned) {
        this.deaccessioned = deaccessioned;
    }

    /**
     *
     */
    @Column(name = "CatalogNumber", unique = false, nullable = true, insertable = true, updatable = true, length=32)
    public String getCatalogNumber() {
        return this.catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }
    
    /**
     *      * Indicates whether this record can be viewed - by owner, by instituion, or by all
     */
    @Column(name = "Visibility", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getVisibility() {
        return this.visibility;
    }
    
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @Transient
    @Override
    public boolean isRestrictable()
    {
        return true;
    }   
    /**
     * 
     */
    @Column(name = "VisibilitySetBy", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getVisibilitySetBy() {
        return this.visibilitySetBy;
    }
    
    public void setVisibilitySetBy(String visibilitySetBy) {
        this.visibilitySetBy = visibilitySetBy;
    }
    
    /**
     *      * BiologicalObject (Bird, Fish, etc)
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "CollectingEventID", unique = false, nullable = true, insertable = true, updatable = true)
    public CollectingEvent getCollectingEvent() {
        return this.collectingEvent;
    }

    public void setCollectingEvent(CollectingEvent collectingEvent) {
        this.collectingEvent = collectingEvent;
    }

    /**
     *
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "ColObjAttributesID", unique = false, nullable = true, insertable = true, updatable = true)
    public ColObjAttributes getColObjAttributes() {
        return this.colObjAttributes;
    }

    public void setColObjAttributes(ColObjAttributes colObjAttributes) {
        this.colObjAttributes = colObjAttributes;
    }

    /**
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "collectionObject")
    public Set<CollectionObjectCitation> getCollectionObjectCitations() {
        return this.collectionObjectCitations;
    }

    public void setCollectionObjectCitations(Set<CollectionObjectCitation> collectionObjectCitations) {
        this.collectionObjectCitations = collectionObjectCitations;
    }

    /**
     *
     */
    @OneToMany(targetEntity=CollectionObjectAttr.class,
            cascade = {}, fetch = FetchType.LAZY, mappedBy="collectionObject")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<AttributeIFace> getAttrs() {
        return this.attrs;
    }

    public void setAttrs(Set<AttributeIFace> attrs) {
        this.attrs = attrs;
    }

    /**
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "collectionObject")
    public Set<Preparation> getPreparations() {
        return this.preparations;
    }

    public void setPreparations(Set<Preparation> preparations) {
        this.preparations = preparations;
    }

    /**
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "collectionObject")
    public Set<Determination> getDeterminations() {
        return this.determinations;
    }

    public void setDeterminations(Set<Determination> determinations) {
        this.determinations = determinations;
    }

    /**
     *
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "collectionObject")
    public Set<ProjectCollectionObject> getProjectCollectionObjects() {
        return this.projectCollectionObjects;
    }

    public void setProjectCollectionObjects(Set<ProjectCollectionObject> projectCollectionObjects) {
        this.projectCollectionObjects = projectCollectionObjects;
    }

//    /**
//     *
//     */
//    public Set<DeaccessionPreparation> getDeaccessionPreparations() {
//        return this.deaccessionPreparations;
//    }
//
//    public void setDeaccessionPreparations(Set<DeaccessionPreparation> deaccessionPreparations) {
//        this.deaccessionPreparations = deaccessionPreparations;
//    }

    /**
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "collectionObject")
    public Set<OtherIdentifier> getOtherIdentifiers() {
        return this.otherIdentifiers;
    }

    public void setOtherIdentifiers(Set<OtherIdentifier> otherIdentifiers) {
        this.otherIdentifiers = otherIdentifiers;
    }

    /**
     *
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "CollectionID", unique = false, nullable = false, insertable = true, updatable = true)
    public Collection getCollection() {
        return this.collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    /**
     *
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "AccessionID", unique = false, nullable = true, insertable = true, updatable = true)
    public Accession getAccession() {
        return this.accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
    }

    /**
     *
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    @JoinColumn(name = "CatalogerID", unique = false, nullable = true, insertable = true, updatable = true)
    public Agent getCataloger() {
        return this.cataloger;
    }

    public void setCataloger(Agent cataloger) {
        this.cataloger = cataloger;
    }


    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "collectionObject")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<Attachment> getAttachments()
    {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments)
    {
        this.attachments = attachments;
    }

    /**
     *      * Preparation, Container
     */
    @ManyToOne(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "ContainerID", unique = false, nullable = true, insertable = true, updatable = true)
    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
    
    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "leftSide")
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    public Set<CollectionRelationship> getLeftSideRels() {
        return this.leftSideRels;
    }
    
    public void setLeftSideRels(Set<CollectionRelationship> leftSideRels) {
        this.leftSideRels = leftSideRels;
    }

    /**
     * 
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "rightSide")
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    public Set<CollectionRelationship> getRightSideRels() {
        return this.rightSideRels;
    }
    
    public void setRightSideRels(Set<CollectionRelationship> rightSideRels) {
        this.rightSideRels = rightSideRels;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#addReference(edu.ku.brc.ui.forms.FormDataObjIFace, java.lang.String)
     */
    @Override
    public void addReference(FormDataObjIFace ref, String refType)
    {
        //System.out.println(getFieldName(ref));
        
        super.addReference(ref, refType);
        
        /*
        DataObjectGettable getter = DataObjectGettableFactory.get(getClass().getName(), "edu.ku.brc.ui.forms.DataGetterForObj");
        Object dataMember = getter.getFieldValue(this, refType);
        if (dataMember instanceof Set)
        {
            try
            {
                Method method = dataMember.getClass().getMethod("add", Object.class);
                Object value = method.invoke(dataMember, ref);
    
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            try
            {
                Method method = ref.getDataClass().getMethod("setCollectionObject", getClass());
                Object value = method.invoke(ref, this);
    
            } catch (Exception ex)
            {
                ex.printStackTrace();
    
            }
        }
        */
        /*
        if (ref instanceof Preparation)
        {
            preparations.add((Preparation)ref);
            ((Preparation)ref).setCollectionObject(this);
            
        } else if (ref instanceof Determination)
        {
            determinations.add((Determination)ref);
            ((Determination)ref).setCollectionObject(this);
            
        } else if (ref instanceof CollectionObjectCitation)
        {
            collectionObjectCitations.add((CollectionObjectCitation)ref);
            ((CollectionObjectCitation)ref).setCollectionObject(this);
            
        } else if (ref instanceof CollectionObjectAttr)
        {
            attrs.add((CollectionObjectAttr)ref);
            ((CollectionObjectAttr)ref).setCollectionObject(this);
            
        } else if (ref instanceof ProjectCollectionObject)
        {
            projectCollectionObjects.add((ProjectCollectionObject)ref);
            ((ProjectCollectionObject)ref).setCollectionObject(this);
            
        } //else if (ref instanceof DeaccessionPreparation)
//        {
//            deaccessionPreparations.add((DeaccessionPreparation)ref);
//            ((DeaccessionPreparation)ref).setCollectionObject(this);
//            
//        } 
            else if (ref instanceof OtherIdentifier)
        {
            otherIdentifiers.add((OtherIdentifier)ref);
            ((OtherIdentifier)ref).setCollectionObject(this);

        } else
        {
            throw new RuntimeException("Adding Object ["+ref.getClass().getSimpleName()+"] and the refType is null.");
        }
        */
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#removeReference(edu.ku.brc.ui.forms.FormDataObjIFace, java.lang.String)
     */
    @Override
    public void removeReference(FormDataObjIFace ref, String refType)
    {
        super.removeReference(ref, refType);
        /*
        
        if (ref instanceof Preparation)
        {
            preparations.remove(ref);
            //((Preparation)ref).setCollectionObject(null);
            
        } else if (ref instanceof Determination)
        {
            determinations.remove(ref);
            //((Determination)ref).setCollectionObject(null);
            
        } else if (ref instanceof CollectionObjectCitation)
        {
            collectionObjectCitations.remove(ref);
            //((CollectionObjectCitation)ref).setCollectionObject(null);
            
        } else if (ref instanceof CollectionObjectAttr)
        {
            attrs.remove(ref);
            //((CollectionObjectAttr)ref).setCollectionObject(null);
            
        } else if (ref instanceof ProjectCollectionObject)
        {
            projectCollectionObjects.remove(ref);
            //((ProjectCollectionObject)ref).setCollectionObject(null);
            
        } 
//        else if (ref instanceof DeaccessionPreparation)
//        {
//            deaccessionPreparations.remove(ref);
//            ((DeaccessionPreparation)ref).setCollectionObject(null);
//            
//        } 
        else if (ref instanceof OtherIdentifier)
        {
            otherIdentifiers.remove(ref);
            //((OtherIdentifier)ref).setCollectionObject(null);

        } else
        {
            throw new RuntimeException("Removing Object ["+ref.getClass().getSimpleName()+"] and the refType is null.");
        }*/
    }

    
    //---------------------------------------------------------------------------
    // Overrides DataModelObjBase
    //---------------------------------------------------------------------------
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    @Transient
    public String getIdentityTitle()
    {
        if (StringUtils.isNotEmpty(catalogNumber))
        {
            UIFieldFormatterIFace fmt = UIFieldFormatterMgr.getFormatter("CatalogNumber");
            if (fmt != null)
            {
                return fmt.formatInBound(catalogNumber).toString();
            }
        }
        return fieldNumber != null ? fieldNumber : super.getIdentityTitle();
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
        return 1;
    }
    

    //----------------------------------------------------------------------
    //-- Comparable Interface
    //----------------------------------------------------------------------
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(CollectionObject obj)
    {
        // XXX TODO need to fix when Cat Nums change to Strings!
        if (catalogNumber != null && obj != null && obj.catalogNumber != null)
        {
            return catalogNumber.compareTo(obj.catalogNumber);
        }
        // else
        return timestampCreated.compareTo(obj.timestampCreated);
    }
}
