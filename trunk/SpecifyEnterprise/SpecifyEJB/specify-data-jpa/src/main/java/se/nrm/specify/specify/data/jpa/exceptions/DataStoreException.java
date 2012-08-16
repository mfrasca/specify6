package se.nrm.specify.specify.data.jpa.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author idali
 */
@ApplicationException(rollback=true) 
public class DataStoreException extends SpecifyException {

    public DataStoreException() {
    }

    public DataStoreException(String s) {
        super(s);
    }

    public DataStoreException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataStoreException(Throwable throwable) {
        super(throwable);
    }
}
