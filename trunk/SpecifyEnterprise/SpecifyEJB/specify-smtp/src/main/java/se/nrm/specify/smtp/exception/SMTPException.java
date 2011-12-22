package se.nrm.specify.smtp.exception;

/**
 * This is the base class for all custom specific exceptions in SMTP project.
 * Note that it extends {@link RuntimeException} 
 * 
 * @author idali
 */
public class SMTPException extends RuntimeException {

    public SMTPException() {
    }

    public SMTPException(String s) {
        super(s);
    }

    public SMTPException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SMTPException(Throwable throwable) {
        super(throwable);
    }
}
