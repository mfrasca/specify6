package edu.ku.brc.specify.datamodel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;




/**
 *  @hibernate.class
 *                 table="locationtreedefitem" 
 */
public class LocationTreeDefItem  implements TreeDefinitionItemIface,java.io.Serializable {

    // Fields    

     protected Integer treeDefItemId;
     protected String name;
     protected Integer rankId;
     protected LocationTreeDef treeDef;
     protected LocationTreeDefItem parent;
     protected Set children;


    // Constructors

    /** default constructor */
    public LocationTreeDefItem() {
    }
    
    /** constructor with id */
    public LocationTreeDefItem(Integer treeDefItemId) {
        this.treeDefItemId = treeDefItemId;
    }
   
    
    

    // Property accessors

    /**
     *      *  @hibernate.property column="TreeDefItemID" length="10" 
     */
    public Integer getTreeDefItemId() {
        return this.treeDefItemId;
    }
    
    public void setTreeDefItemId(Integer treeDefItemId) {
        this.treeDefItemId = treeDefItemId;
    }

    /**
     *      *  @hibernate.property column="Name" length="64"
     *                 
     */
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     *      *  @hibernate.property column="RankID" 
     */
    public Integer getRankId() {
        return this.rankId;
    }
    
    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    /**
     *      * A reference back to the tree defintion
     */
    public LocationTreeDef getTreeDef() {
        return this.treeDef;
    }
    
    public void setTreeDef(LocationTreeDef treeDef) {
        this.treeDef = treeDef;
    }

    /**
     * 
     */
    public LocationTreeDefItem getParent() {
        return this.parent;
    }
    
    public void setParent(LocationTreeDefItem parent) {
        this.parent = parent;
    }

    /**
     * 
     */
    public Set getChildren() {
        return this.children;
    }
    
    public void setChildren(Set children) {
        this.children = children;
    }

  /**
	 * toString
	 * @return String
	 */
  public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("treeDefItemId").append("='").append(getTreeDefItemId()).append("' ");			
      buffer.append("name").append("='").append(getName()).append("' ");			
      buffer.append("treeDef").append("='").append(getTreeDef()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
	}



  // The following is extra code specified in the hbm.xml files

                
                public TreeDefinitionIface getTreeDefinition()
                {
                    return getTreeDef();
                }
                
                public void setTreeDefinition(TreeDefinitionIface treeDef)
                {
                    if( !(treeDef instanceof LocationTreeDef) )
                    {
                        throw new IllegalArgumentException("Argument must be an instanceof LocationTreeDef");
                    }
                    setTreeDef((LocationTreeDef)treeDef);
                }
                
                public TreeDefinitionItemIface getParentItem()
                {
                    return getParent();
                }
                
                public void setParentItem(TreeDefinitionItemIface parent)
                {
                    if( !(parent instanceof LocationTreeDefItem) )
                    {
                        throw new IllegalArgumentException("Argument must be an instanceof LocationTreeDefItem");
                    }
                    setParent((LocationTreeDefItem)parent);
                }
    
                public TreeDefinitionItemIface getChildItem()
                {
                    if( getChildren().isEmpty() )
                    {
                        return null;
                    }
                    
                    return (TreeDefinitionItemIface)getChildren().iterator().next();
                }
                
                public void setChildItem(TreeDefinitionItemIface child)
                {
                    if( !(child instanceof LocationTreeDefItem) )
                    {
                        throw new IllegalArgumentException("Argument must be an instanceof LocationTreeDefItem");
                    }
                    Set children = Collections.synchronizedSet(new HashSet());
                    children.add(child);
                    setChildren(children);
                }
                
            
  // end of extra code specified in the hbm.xml files
}