/*
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
package edu.ku.brc.specify.datamodel;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.ui.forms.FormDataObjIFace;

/**
 * 
 */
public class AccessionAuthorizations extends DataModelObjBase implements java.io.Serializable,
        Comparable<AccessionAuthorizations>
{

    // Fields

    protected Long                accessionAuthorizationsId;
    protected String              remarks;
    protected Permit              permit;
    protected Accession           accession;
    protected RepositoryAgreement repositoryAgreement;

    // Constructors

    /** default constructor */
    public AccessionAuthorizations()
    {
        // do nothing
    }

    /** constructor with id */
    public AccessionAuthorizations(Long accessionAuthorizationsId)
    {
        this.accessionAuthorizationsId = accessionAuthorizationsId;
    }

    // Initializer
    @Override
    public void initialize()
    {
        accessionAuthorizationsId = null;
        remarks = null;
        timestampModified = null;
        timestampCreated = new Date();
        lastEditedBy = null;
        permit = null;
        accession = null;
        repositoryAgreement = null;
    }

    // End Initializer

    // Property accessors

    /**
     * 
     */
    public Long getAccessionAuthorizationsId()
    {
        return this.accessionAuthorizationsId;
    }

    /**
     * Generic Getter for the ID Property.
     * 
     * @returns ID Property.
     */
    @Override
    public Long getId()
    {
        return this.accessionAuthorizationsId;
    }

    public void setAccessionAuthorizationsId(Long accessionAuthorizationsId)
    {
        this.accessionAuthorizationsId = accessionAuthorizationsId;
    }

    /**
     * 
     */
    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    /**
     * * Permit authorizing accession
     */
    public Permit getPermit()
    {
        return this.permit;
    }

    public void setPermit(Permit permit)
    {
        this.permit = permit;
    }

    /**
     * * Accession authorized by permit
     */
    public Accession getAccession()
    {
        return this.accession;
    }

    public void setAccession(Accession accession)
    {
        this.accession = accession;
    }

    /**
     * 
     */
    public RepositoryAgreement getRepositoryAgreement()
    {
        return this.repositoryAgreement;
    }

    public void setRepositoryAgreement(RepositoryAgreement repositoryAgreement)
    {
        this.repositoryAgreement = repositoryAgreement;
    }

    public int compareTo(AccessionAuthorizations obj)
    {
        if (permit != null && permit.permitNumber != null &&
                obj.permit.permitNumber != null &&
                obj.permit.permitNumber != null)
        {
            return permit.permitNumber.compareTo(obj.permit.permitNumber);
        }
        // else
        return timestampCreated.compareTo(obj.timestampCreated);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#addReference(edu.ku.brc.ui.forms.FormDataObjIFace, java.lang.String)
     */
    @Override
    public void addReference(FormDataObjIFace ref, String refType)
    {
        if (StringUtils.isNotEmpty(refType))
        {
            if (refType.equals("permit"))
            {
                if (ref instanceof Permit)
                {
                    permit = (Permit)ref;
                    ((Permit)ref).getAccessionAuthorizations().add(this);
                    
                } else
                {
                    throw new RuntimeException("ref ["+ref.getClass().getSimpleName()+"] is not an instance of Permit");
                }
                
            } else if (refType.equals("accession"))
            {
                if (ref instanceof Accession)
                {
                    accession = (Accession)ref;
                    ((Accession)ref).getAccessionAuthorizations().add(this);
                    
                } else
                {
                    throw new RuntimeException("ref ["+ref.getClass().getSimpleName()+"] is not an instance of Accession");
                }
                
            } else if (refType.equals("repositoryAgreement"))
            {
                if (ref instanceof RepositoryAgreement)
                {
                    repositoryAgreement = (RepositoryAgreement)ref;
                    ((RepositoryAgreement)ref).getRepositoryAgreementAuthorizations().add(this);
                    
                } else
                {
                    throw new RuntimeException("ref ["+ref.getClass().getSimpleName()+"] is not an instance of RepositoryAgreement");
                }
                
            }
        } else
        {
            throw new RuntimeException("Adding Object ["+ref.getClass().getSimpleName()+"] and the refType is null.");
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#removeReference(edu.ku.brc.ui.forms.FormDataObjIFace, java.lang.String)
     */
    @Override
    public void removeReference(FormDataObjIFace ref, String refType)
    {
        if (StringUtils.isNotEmpty(refType))
        {
            if (refType.equals("permit"))
            {
                if (ref instanceof Permit)
                {
                    permit = null;
                    ((Agent)ref).getAccessionAgents().remove(this);
                    
                } else
                {
                    throw new RuntimeException("ref ["+ref.getClass().getSimpleName()+"] is not an instance of Permit");
                }
                
            } else if (refType.equals("accession"))
            {
                if (ref instanceof Accession)
                {
                    accession = null;
                    ((Accession)ref).getAccessionAgents().remove(this);
                    
                } else
                {
                    throw new RuntimeException("ref ["+ref.getClass().getSimpleName()+"] is not an instance of Accession");
                }
                
            } else if (refType.equals("repositoryAgreement"))
            {
                if (ref instanceof RepositoryAgreement)
                {
                    repositoryAgreement = null;
                    ((RepositoryAgreement)ref).getRepositoryAgreementAgents().remove(this);
                    
                } else
                {
                    throw new RuntimeException("ref ["+ref.getClass().getSimpleName()+"] is not an instance of RepositoryAgreement");
                }
                
            }
        } else
        {
            throw new RuntimeException("Removing Object ["+ref.getClass().getSimpleName()+"] and the refType is null.");
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    public Integer getTableId()
    {
        return 13;
    }

}
