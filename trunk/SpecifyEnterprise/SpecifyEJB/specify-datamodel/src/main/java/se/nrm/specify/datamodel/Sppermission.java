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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
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
    @NamedQuery(name = "Sppermission.findBySpPermissionID", query = "SELECT s FROM Sppermission s WHERE s.permissionId = :spPermissionID"),
    @NamedQuery(name = "Sppermission.findByName", query = "SELECT s FROM Sppermission s WHERE s.name = :name"),
    @NamedQuery(name = "Sppermission.findByTargetId", query = "SELECT s FROM Sppermission s WHERE s.targetId = :targetId")})
public class Sppermission implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpPermissionID")
    private Integer permissionId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Actions")
    private String actions;
    
    @Size(max = 64)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false) 
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
    private Collection<Spprincipal> principals;

    public Sppermission() {
    }

    public Sppermission(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Sppermission(Integer permissionId, String permissionClass) {
        this.permissionId = permissionId;
        this.permissionClass = permissionClass;
    }
 
    @XmlID
    @XmlAttribute(name = "id") 
    public String getIdentityString() {
        return (permissionId != null) ? permissionId.toString() : "0";
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

    @NotNull(message="PermissionClass must be specified.")
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

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @XmlTransient
    public Collection<Spprincipal> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Collection<Spprincipal> principals) {
        this.principals = principals;
    }

     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permissionId != null ? permissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sppermission)) {
            return false;
        }
        Sppermission other = (Sppermission) object;
        if ((this.permissionId == null && other.permissionId != null) || (this.permissionId != null && !this.permissionId.equals(other.permissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Sppermission[ spPermissionID=" + permissionId + " ]";
    }
    
}
