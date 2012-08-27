package se.nrm.specify.specify.data.jpa.exceptions;

/**
 *
 * @author idali
 */

public class DataReflectionException extends SpecifyException {
    
    public DataReflectionException() {
    }

    public DataReflectionException(String s) {
        super(s);
    }

    public DataReflectionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataReflectionException(Throwable throwable) {
        super(throwable);
    }
}
