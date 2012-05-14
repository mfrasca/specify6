package se.nrm.specify.datamodel;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@XmlRootElement
public class SpecifyBeanWrapper {

    @XmlElements({
        @XmlElement(name = "accession", type = Accession.class),
        @XmlElement(name = "agent", type = Agent.class),
        @XmlElement(name = "address", type = Address.class),
        @XmlElement(name = "attachment", type = Attachment.class),
        @XmlElement(name = "borrow", type = Borrow.class),
        @XmlElement(name = "collectingevent", type = Collectingevent.class),
        @XmlElement(name = "collectionobject", type = Collectionobject.class),
        @XmlElement(name = "collection", type = Collection.class),
        @XmlElement(name = "collector", type = Collector.class),
        @XmlElement(name = "determination", type = Determination.class),
        @XmlElement(name = "dnasequence", type = Dnasequence.class),
        @XmlElement(name = "locality", type = Locality.class),
        @XmlElement(name = "geography", type = Geography.class),
        @XmlElement(name = "taxon", type = Taxon.class),
        @XmlElement(name = "preparation", type = Preparation.class),
        @XmlElement(name = "specifyuser", type = Specifyuser.class)
    })
    private SpecifyBean bean;
    @XmlElements({
        @XmlElement(name = "accessions", type = Accession.class),
        @XmlElement(name = "addresses", type = Address.class),
        @XmlElement(name = "agents", type = Agent.class),
        @XmlElement(name = "attachments", type = Attachment.class),
        @XmlElement(name = "borrows", type = Borrow.class),
        @XmlElement(name = "collectingevents", type = Collectingevent.class),
        @XmlElement(name = "collectionobjects", type = Collectionobject.class),
        @XmlElement(name = "collections", type = Collection.class),
        @XmlElement(name = "collectors", type = Collector.class),
        @XmlElement(name = "determinations", type = Determination.class),
        @XmlElement(name = "dnasequences", type = Dnasequence.class),
        @XmlElement(name = "localities", type = Locality.class),
        @XmlElement(name = "geographies", type = Geography.class),
        @XmlElement(name = "taxons", type = Taxon.class),
        @XmlElement(name = "preparations", type = Preparation.class),
        @XmlElement(name = "specifyusers", type = Specifyuser.class)
    })
    private List<? extends SpecifyBean> beans;

    public SpecifyBeanWrapper() {
    }

    public SpecifyBeanWrapper(SpecifyBean bean) {
        this.bean = bean;
    }

    public SpecifyBeanWrapper(List<? extends SpecifyBean> beans) {
        this.beans = beans;
    }

    public SpecifyBean getBean() {
        return bean;
    }

    public List<? extends SpecifyBean> getBeans() {
        return beans;
    }
}
