package se.nrm.specify.business.logic.exception;

import se.nrm.specify.specify.data.jpa.exceptions.SpecifyException;

/**
 *
 * @author idali
 */
public class DuplicteNumberException extends SpecifyException {
    
    public DuplicteNumberException() {
    }

    public DuplicteNumberException(String s) {
        super(s);
    }

    public DuplicteNumberException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DuplicteNumberException(Throwable throwable) {
        super(throwable);
    }
    
}
