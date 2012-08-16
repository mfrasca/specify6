package se.nrm.specify.specify.data.jpa.exceptions;

/**
 /**
 * This is the base class for all custom specific exceptions in Specify.
 * Note that it extends {@link RuntimeException} 
 * 
 * @author idali
 * 
 * 
 */
public class SpecifyException extends RuntimeException {

    public SpecifyException() {
    }

    public SpecifyException(String s) {
        super(s);
    }

    public SpecifyException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SpecifyException(Throwable throwable) {
        super(throwable);
    }
}
