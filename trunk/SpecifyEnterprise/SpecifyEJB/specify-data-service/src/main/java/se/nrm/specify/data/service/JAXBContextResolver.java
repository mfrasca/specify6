package se.nrm.specify.data.service;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;   
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext; 
import se.nrm.specify.business.logic.validation.ValidationError;
import se.nrm.specify.business.logic.validation.ValidationOK;
import se.nrm.specify.business.logic.validation.ValidationWarning;
import se.nrm.specify.business.logic.validation.ValidationWrapper;
import se.nrm.specify.datamodel.Accessionagent;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Preparation;
import se.nrm.specify.datamodel.SpecifyBeanWrapper; 

/**
 *
 * This class is to fix JSON array for single element in collection
 * 
 * 
 * 
 * @author idali
 */
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private JAXBContext context;
    private Class[] types = {SpecifyBeanWrapper.class, ValidationWrapper.class, DataWrapper.class, Determination.class, Preparation.class, 
                             Accessionagent.class, Address.class, ValidationOK.class, ValidationWarning.class, ValidationError.class};

    public JAXBContextResolver() throws Exception {  
        this.context = new JSONJAXBContext(JSONConfiguration.mapped().arrays("determinations", "preparations", "accessionAgents", "addresses").build(), types);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        return context;
    }
}  