package se.nrm.specify.business.logic.validation;
 
import java.util.ArrayList; 
import java.util.Collection;
import java.util.List;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.Collector;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public final class CollectingeventValidation extends BaseValidationRules {
    
    private final static String COLLECTINGEVENT = "collectingEvent"; 
    
    private Collectingevent collectingevent; 

    public CollectingeventValidation() {
        super(Collectingevent.class);
    }

    public CollectingeventValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Collectingevent) bean;
        this.collectingevent = (Collectingevent) bean;
        
        this.sbId = new SpecifyBeanId(bean);
 
        relatedTables = new ArrayList<String>();
        relatedTables.add(Collectionobject.class.getSimpleName());
        map.put(Collectionobject.class.getSimpleName(), COLLECTINGEVENT); 
    }
    
    @Override
    public Validation validationBeforeSave() { 
          
        Collection<Collector> collectors = collectingevent.getCollectors();
        if(collectors != null) { 
            
            for(Collector collector : collectors) {
                CollectorValidation validationRule = new CollectorValidation(collector);
                Validation validation = validationRule.validationBeforeSave(); 
                if(validation.isValidationNotOK()) {
                    return validation;
                }
            }
        }  
        return new ValidationOK(sbId, Status.Save);
    }
    
    @Override
    public int countForDelete() {
        List<Collectionobject> cos = (List<Collectionobject>)collectingevent.getCollectionObjects();
        if(cos != null && cos.size() == 1) {   
            Collectionobject co = cos.get(0);
            if(co != null) {
                se.nrm.specify.datamodel.Collection collection = co.getCollection(); 
                if(collection.getIsEmbeddedCollectingEvent()) {
                    return 1;
                } 
            }
            return 0;
        }  
        return 0;
    } 
    
    @Override
    public boolean isCheckForSaving() {
        return true;
    }
   
    @Override
    public boolean isCheckForDelete() {
        return true;
    } 
}
