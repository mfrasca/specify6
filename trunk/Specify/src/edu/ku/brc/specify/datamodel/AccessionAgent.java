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
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.dbsupport.DBTableIdMgr;

/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "accessionagent", uniqueConstraints = { @UniqueConstraint(columnNames = { "Role", "AgentID", "AccessionID" }) })
public class AccessionAgent extends DataModelObjBase implements java.io.Serializable {

    // Fields    

    protected Integer             accessionAgentId;
    protected String              role;
    protected String              remarks;
    protected Agent               agent;
    protected Accession           accession;
    protected RepositoryAgreement repositoryAgreement;


    // Constructors

    /** default constructor */
    public AccessionAgent()
    {
        // do nothing
    }
    
    /** constructor with id */
    public AccessionAgent(Integer accessionAgentId)
    {
        this.accessionAgentId = accessionAgentId;
    }
   
    
    

    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        accessionAgentId = null;
        role = null;
        remarks = null;
        agent = null;
        accession = null;
        repositoryAgreement = null;
    }
    // End Initializer

    // Property accessors

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = "AccessionAgentID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getAccessionAgentId() {
        return this.accessionAgentId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.accessionAgentId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return AccessionAgent.class;
    }
    
    public void setAccessionAgentId(Integer accessionAgentId) {
        this.accessionAgentId = accessionAgentId;
    }

    /**
     *      * Role the agent played in the accession process
     */
    @Column(name = "Role", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 
     */
    @Lob
    @Column(name = "Remarks", length = 4096)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     *      * AgentAdress of agent playing role in Accession
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "AgentID", unique = false, nullable = false, insertable = true, updatable = true)
    public Agent getAgent() {
        return this.agent;
    }
    
    public void setAgent(Agent agent) {
        this.agent = agent;
    }
    
//    @Transient
//    public String getImageURL()
//    {
//        return agent.getImageURL();
//    }
//    
//    public void setImageURL(String url)
//    {
//        agent.setImageURL(url);
//    }

    /**
     *      * Accession in which the Agent played a role
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "AccessionID", unique = false, nullable = false, insertable = true, updatable = true)
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
    @JoinColumn(name = "RepositoryAgreementID", unique = false, nullable = true, insertable = true, updatable = true)
    public RepositoryAgreement getRepositoryAgreement() {
        return this.repositoryAgreement;
    }
    
    public void setRepositoryAgreement(RepositoryAgreement repositoryAgreement) 
    {
        this.repositoryAgreement = repositoryAgreement;
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
        return 12;
    }

    @Override
    @Transient
    public String getIdentityTitle()
    {
        String str = (StringUtils.isNotEmpty(role) ? role : "")  + (agent != null ? (": " + agent.getIdentityTitle()) : "");
        
        if (str.length() == 0)
        {
            str = DBTableIdMgr.getInstance().getByShortClassName("Agent").getTitle();
        }
        return str;
    }
}
