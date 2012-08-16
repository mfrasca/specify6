package se.nrm.specify.business.logic.validation;
 
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.Taxon;

/**
 *
 * @author idali
 */
public final class TaxonValidation extends BaseValidationRules {
     
    private Taxon taxon; 

    public TaxonValidation() {
        super(Taxon.class);
    }

    public TaxonValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Taxon) bean;
        this.taxon = (Taxon) bean;
        
        this.sbId = new SpecifyBeanId(bean); 
    } 
}
