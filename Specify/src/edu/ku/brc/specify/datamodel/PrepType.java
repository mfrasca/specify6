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




/**

 */
public class PrepType  implements java.io.Serializable {

    // Fields    

     protected Long prepTypeId;
     protected String name;
     protected Set<Preparation> preparations;
     protected Set<AttributeDef> attributeDefs;


    // Constructors

    /** default constructor */
    public PrepType() {
    }
    
    /** constructor with id */
    public PrepType(Long prepTypeId) {
        this.prepTypeId = prepTypeId;
    }
   
    
    

    // Initializer
    public void initialize()
    {
        prepTypeId = null;
        name = null;
        preparations = new HashSet<Preparation>();
        attributeDefs = new HashSet<AttributeDef>();
    }
    // End Initializer

    // Property accessors

    /**
     * 
     */
    public Long getPrepTypeId() {
        return this.prepTypeId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    public Long getId()
    {
        return this.prepTypeId;
    }
    
    public void setPrepTypeId(Long prepTypeId) {
        this.prepTypeId = prepTypeId;
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
    public Set<Preparation> getPreparations() {
        return this.preparations;
    }
    
    public void setPreparations(Set<Preparation> preparations) {
        this.preparations = preparations;
    }

    /**
     * 
     */
    public Set<AttributeDef> getAttributeDefs() {
        return this.attributeDefs;
    }
    
    public void setAttributeDefs(Set<AttributeDef> attributeDefs) {
        this.attributeDefs = attributeDefs;
    }





    // Add Methods

    public void addPreparations(final Preparation preparation)
    {
        this.preparations.add(preparation);
        preparation.setPrepType(this);
    }

    public void addAttributeDefs(final AttributeDef attributeDef)
    {
        this.attributeDefs.add(attributeDef);
        attributeDef.setPrepType(this);
    }

    // Done Add Methods

    // Delete Methods

    public void removePreparations(final Preparation preparation)
    {
        this.preparations.remove(preparation);
        preparation.setPrepType(null);
    }

    public void removeAttributeDefs(final AttributeDef attributeDef)
    {
        this.attributeDefs.remove(attributeDef);
        attributeDef.setPrepType(null);
    }

    // Delete Add Methods
}
