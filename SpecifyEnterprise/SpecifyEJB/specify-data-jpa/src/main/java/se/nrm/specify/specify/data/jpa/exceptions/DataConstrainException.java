package se.nrm.specify.specify.data.jpa.exceptions;
 

/**
 *
 * @author idali
 */ 
public class DataConstrainException extends SpecifyException  {
   public DataConstrainException() {
    }

    public DataConstrainException(String s) {
        super(s);
    }

    public DataConstrainException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataConstrainException(Throwable throwable) {
        super(throwable);
    }
}