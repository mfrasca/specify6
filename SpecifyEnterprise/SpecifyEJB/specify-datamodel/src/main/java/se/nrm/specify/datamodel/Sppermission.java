package se.nrm.specify.datamodel;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "sppermission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sppermission.findAll", query = "SELECT s FROM Sppermission s"),
    @NamedQuery(name = "Sppermission.findBySpPermissionID", query = "SELECT s FROM Sppermission s WHERE s.spPermissionID = :spPermissionID"),
    @NamedQuery(name = "Sppermission.findByName", query = "SELECT s FROM Sppermission s WHERE s.name = :name"),
    @NamedQuery(name = "Sppermission.findByTargetId", query = "SELECT s FROM Sppermission s WHERE s.targetId = :targetId")})
public class Sppermission implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpPermissionID")
    private Integer spPermissionID;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Actions")
    private String actions;
    
    @Size(max = 64)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "PermissionClass")
    private String permissionClass;
    
    @Column(name = "TargetId")
    private Integer targetId;
    
    @JoinTable(name = "spprincipal_sppermission", joinColumns = {
        @JoinColumn(name = "SpPermissionID", referencedColumnName = "SpPermissionID")}, inverseJoinColumns = {
        @JoinColumn(name = "SpPrincipalID", referencedColumnName = "SpPrincipalID")})
    @ManyToMany
    private Collection<Spprincipal> spprincipalCollection;

    public Sppermission() {
    }

    public Sppermission(Integer spPermissionID) {
        this.spPermissionID = spPermissionID;
    }

    public Sppermission(Integer spPermissionID, String permissionClass) {
        this.spPermissionID = spPermissionID;
        this.permissionClass = permissionClass;
    }

    public Integer getSpPermissionID() {
        return spPermissionID;
    }

    public void setSpPermissionID(Integer spPermissionID) {
        this.spPermissionID = spPermissionID;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionClass() {
        return permissionClass;
    }

    public void setPermissionClass(String permissionClass) {
        this.permissionClass = permissionClass;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    @XmlTransient
    public Collection<Spprincipal> getSpprincipalCollection() {
        return spprincipalCollection;
    }

    public void setSpprincipalCollection(Collection<Spprincipal> spprincipalCollection) {
        this.spprincipalCollection = spprincipalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spPermissionID != null ? spPermissionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sppermission)) {
            return false;
        }
        Sppermission other = (Sppermission) object;
        if ((this.spPermissionID == null && other.spPermissionID != null) || (this.spPermissionID != null && !this.spPermissionID.equals(other.spPermissionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Sppermission[ spPermissionID=" + spPermissionID + " ]";
    }
    
}
