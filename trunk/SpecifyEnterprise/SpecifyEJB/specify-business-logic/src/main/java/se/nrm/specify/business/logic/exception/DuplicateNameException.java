package se.nrm.specify.business.logic.exception;

import se.nrm.specify.specify.data.jpa.exceptions.SpecifyException;

/**
 *
 * @author idali
 */
public class DuplicateNameException extends SpecifyException {
    public DuplicateNameException() {
    }

    public DuplicateNameException(String s) {
        super(s);
    }

    public DuplicateNameException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DuplicateNameException(Throwable throwable) {
        super(throwable);
    }
}
