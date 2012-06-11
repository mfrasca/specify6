package se.nrm.specify.data.service;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;  
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import se.nrm.specify.datamodel.Accessionagent;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Preparation;
import se.nrm.specify.datamodel.SpecifyBeanWrapper;

/**
 *
 * @author idali
 */
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private JAXBContext context;
    private Class[] types = {SpecifyBeanWrapper.class, Determination.class, Preparation.class, Accessionagent.class, Address.class};

    public JAXBContextResolver() throws Exception { 
        
        this.context = new JSONJAXBContext(JSONConfiguration.mapped().arrays("determinations", "preparations", "accessionAgents", "addresses").build(), types);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        return context;
    }
}  