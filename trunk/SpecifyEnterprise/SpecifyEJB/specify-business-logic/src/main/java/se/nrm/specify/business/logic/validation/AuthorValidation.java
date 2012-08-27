package se.nrm.specify.business.logic.validation;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import se.nrm.specify.business.logic.util.ValidationMessage;
import se.nrm.specify.datamodel.Agent;
import se.nrm.specify.datamodel.Author;
import se.nrm.specify.datamodel.Referencework;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;

/**
 *
 * @author idali
 */
public class AuthorValidation extends BaseValidationRules {
     
    
    private final static String AGENT_ID = "agent.agentId";
    private final static String PRIMARY_FIELD = "authorId"; 
    private final static String SPECIAL_FIELD_NAME = "referenceWork.referenceWorkId";
    
    private Author author; 
    private int agentId = 0;

    public AuthorValidation() {
        super(Author.class);
    }

    public AuthorValidation(SpecifyBean bean) {
        super(bean);
        this.author = (Author) bean; 
    }

    @Override
    public void initialize(SpecifyBean bean) {
         
        this.bean = (Author) bean;
        this.author = (Author) bean;

        map = new HashMap<String, Object>();  
        agentId = author.getAgent() == null ? agentId : author.getAgent().getAgentId();
        map.put(AGENT_ID, agentId);
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, author.getAuthorId());
        }
        
        map.put(ConstantsClass.getInstance().TABLE_NAME, Author.class.getSimpleName()); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD_VALUE, 
                                        author.getReferenceWork() == null ? null : author.getReferenceWork().getReferenceWorkId());
        
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(AGENT_ID);
         
        this.sbId = new SpecifyBeanId(author); 
    }
    
    @Override
    public Validation validationBeforeSave() { 
        
        boolean isValid = true;
        List<String> msgs = new ArrayList<String>();
         
        if(author.getAgent() == null) {
            isValid = false;
            String msg = ValidationMessage.getInstance().createEntityCanNotBeNullMsg(Author.class.getSimpleName(), Agent.class);  
            msgs.add(msg);
        } 
         
        if(author.getReferenceWork() == null) {
            isValid = false;
            String msg = ValidationMessage.getInstance().createEntityCanNotBeNullMsg(Author.class.getSimpleName(), Referencework.class);  
            msgs.add(msg);
        } else {
            logger.info("authours : {}", author.getReferenceWork().getAuthors());
        }
        return isValid ? new ValidationOK() :  new ValidationError(sbId, Status.ConstraintViolation, msgs);  
    }
    
    @Override
    public boolean isCheckForSaving() {
        return true;
    }
  
    @Override
    public boolean isCheckForDuplication() {
        return author.getReferenceWork() == null ? false : author.getReferenceWork().getReferenceWorkId() == null ? false : true;
    } 
}
