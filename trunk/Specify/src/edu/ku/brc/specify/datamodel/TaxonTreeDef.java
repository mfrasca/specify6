package edu.ku.brc.specify.datamodel;

import java.util.HashSet;
import java.util.Set;

public class TaxonTreeDef  implements TreeDefinitionIface,java.io.Serializable {

    // Fields    

     protected Integer treeDefId;
     protected String name;
     protected String remarks;
     protected CollectionObjDef collObjDef;
     protected Set<Taxon> treeEntries;
     protected Set<TaxonTreeDefItem> treeDefItems;

    // Constructors

    /** default constructor */
    public TaxonTreeDef() {
    }
    
    /** constructor with id */
    public TaxonTreeDef(Integer treeDefId) {
        this.treeDefId = treeDefId;
    }
   
    
    // Initializer
    public void initialize()
    {
        treeDefId = null;
        name = null;
        remarks = null;
        collObjDef = null;
        treeEntries = new HashSet<Taxon>();
        treeDefItems = new HashSet<TaxonTreeDefItem>();
    }
    // End Initializer
 
    // Property accessors

    /**
     * 
     */
    public Integer getTreeDefId() {
        return this.treeDefId;
    }
    
    public void setTreeDefId(Integer treeDefId) {
        this.treeDefId = treeDefId;
    }

    /**
     * 
     */
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     */
    public CollectionObjDef getCollObjDef() {
        return this.collObjDef;
    }
    
    public void setCollObjDef(CollectionObjDef collObjDef) {
        this.collObjDef = collObjDef;
    }

    /**
     * 
     */
    public Set getTreeEntries() {
        return this.treeEntries;
    }
    
    @SuppressWarnings("unchecked")
	public void setTreeEntries(Set treeEntries) {
        this.treeEntries = treeEntries;
    }

    /**
     * 
     */
    public Set getTreeDefItems() {
        return this.treeDefItems;
    }
    
    @SuppressWarnings("unchecked")
	public void setTreeDefItems(Set treeDefItems) {
        this.treeDefItems = treeDefItems;
    }
}