package edu.ku.brc.specify.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;






/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "sp_userpermission")
public class UserPermission extends DataModelObjBase implements java.io.Serializable
{
    // Fields    

     protected Integer userPermissionId;
     protected Boolean dataAccessPrivilege;
     protected Boolean adminPrivilege;
     protected SpecifyUser specifyUser;
     protected CollectionType collectionType;


    // Constructors

    /** default constructor */
    public UserPermission() {
        //
    }
    
    /** constructor with id */
    public UserPermission(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }
   
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#initialize()
     */
    @Override
    public void initialize()
    {
        super.init();
        userPermissionId = null;
        dataAccessPrivilege = null;
        adminPrivilege = null;
        specifyUser = null;
        collectionType = null;
    }  

    // Property accessors

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = "UserPermissionID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getUserPermissionId() {
        return this.userPermissionId;
    }
    
    public void setUserPermissionId(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    /**
     *      * User definable
     */
    @Column(name="DataAccessPrivilege", unique=false, nullable=true, insertable=true, updatable=true)
    public Boolean getDataAccessPrivilege() {
        return this.dataAccessPrivilege;
    }
    
    public void setDataAccessPrivilege(Boolean dataAccessPrivilege) {
        this.dataAccessPrivilege = dataAccessPrivilege;
    }

    /**
     *      * User definable
     */
    @Column(name="AdminPrivilege", unique=false, nullable=true, insertable=true, updatable=true)
    public Boolean getAdminPrivilege() {
        return this.adminPrivilege;
    }
    
    public void setAdminPrivilege(Boolean adminPrivilege) {
        this.adminPrivilege = adminPrivilege;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SpecifyUserID", unique = false, nullable = false, insertable = true, updatable = true)
    public SpecifyUser getSpecifyUser() {
        return this.specifyUser;
    }
    
    public void setSpecifyUser(SpecifyUser owner) {
        this.specifyUser = owner;
    }
    
    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CollectionTypeID", unique = false, nullable = false, insertable = true, updatable = true)
    public CollectionType getCollectionType() {
        return this.collectionType;
    }
    
    public void setCollectionType(CollectionType collectionType) {
        this.collectionType = collectionType;
    }
    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.userPermissionId;
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
        return 88;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    @Transient
    public String getIdentityTitle()
    { 
        return super.getIdentityTitle();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#isIndexable()
     */
    @Transient
    @Override
    public boolean isIndexable()
    {
        return false;
    }
}
