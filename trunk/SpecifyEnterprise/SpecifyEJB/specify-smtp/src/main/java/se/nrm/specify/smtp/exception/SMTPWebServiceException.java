package se.nrm.specify.smtp.exception;

/**
 *
 * @author idali
 */
public class SMTPWebServiceException extends SMTPException {
    
    public SMTPWebServiceException() {
    }

    public SMTPWebServiceException(String s) {
        super(s);
    }

    public SMTPWebServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SMTPWebServiceException(Throwable throwable) {
        super(throwable);
    }
}
