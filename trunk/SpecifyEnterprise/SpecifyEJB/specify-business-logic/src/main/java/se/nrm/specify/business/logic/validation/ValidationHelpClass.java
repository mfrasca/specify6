package se.nrm.specify.business.logic.validation;

import java.util.HashMap;
import java.util.Map;
import se.nrm.specify.datamodel.*; 

/**
 *
 * @author idali
 */
public class ValidationHelpClass {
    
    private static final Map<String, IBaseValidationRules> map = new HashMap<String, IBaseValidationRules>(); 
    
    static {
        map.put(Accession.class.getSimpleName(), new AccessionValidation());
        map.put(Accessionagent.class.getSimpleName(), new AccessionAgentValidation()); 
        map.put(Accessionauthorization.class.getSimpleName(), new AccessionAuthorizationValidation()); 
        map.put(Address.class.getSimpleName(), new AddressValidation());
        map.put(Agent.class.getSimpleName(), new AgentValidation());
        map.put(Appraisal.class.getSimpleName(), new AppraisalValidation());
        map.put(Author.class.getSimpleName(), new AuthorValidation());
        map.put(Borrow.class.getSimpleName(), new BorrowValidation());
        map.put(Collectingevent.class.getSimpleName(), new CollectingeventValidation());
        map.put(Collection.class.getSimpleName(), new CollectionValidation());
        map.put(Collectionobject.class.getSimpleName(), new CollectionobjectValidation());
        map.put(Collector.class.getSimpleName(), new CollectorValidation());
        map.put(Discipline.class.getSimpleName(), new DisciplineValidation());
        map.put(Division.class.getSimpleName(), new DivisionValidation());
        map.put(Gift.class.getSimpleName(), new GiftValidation());
        map.put(Institution.class.getSimpleName(), new InstitutionValidation());
        map.put(Journal.class.getSimpleName(), new JournalValidation());
        map.put(Permit.class.getSimpleName(), new PermitValidation()); 
        map.put(Picklist.class.getSimpleName(), new PicklistValidation()); 
        map.put(Preparation.class.getSimpleName(), new PreparationValidation()); 
        map.put(Preptype.class.getSimpleName(), new PreptypeValidation()); 
        map.put(Referencework.class.getSimpleName(), new ReferenceworkValidation()); 
        map.put(Repositoryagreement.class.getSimpleName(), new RepositoryAgreementValidation());
        map.put(Shipment.class.getSimpleName(), new ShipmentValidation());
        map.put(Specifyuser.class.getSimpleName(), new SpecifyUserValidation());
    }
 
    public ValidationHelpClass() {  
    } 
    
    public static IBaseValidationRules getValidationRules(SpecifyBean bean) { 
        String key = bean.getClass().getSimpleName();
        return (map.containsKey(key)) ? map.get(key) : new BaseValidationRules(bean) ; 
    }  
}
