package se.nrm.specify.specify.data.jpa.exceptions;
 
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.SQLException;  
import javax.ejb.EJBTransactionRolledbackException;
import org.eclipse.persistence.exceptions.JPQLException;
import org.eclipse.persistence.internal.libraries.antlr.runtime.NoViableAltException;

/**
 *
 * @author idali
 */
public class ExceptionUtil {

    /**
     * Looks up and returns the root cause of an exception. If none is found, returns
     * supplied Throwable object unchanged. If root is found, recursively "unwraps" it,
     * and returns the result to the user.
     */
    public static Throwable unwindException(Throwable th) {
        if (th instanceof SQLException) {
            SQLException sqlException = (SQLException) th;
            if (sqlException.getNextException() != null) {
                return unwindException(sqlException.getNextException());
            }
        } else if(th instanceof MySQLIntegrityConstraintViolationException) {
            MySQLIntegrityConstraintViolationException mysqlIntegrityException = (MySQLIntegrityConstraintViolationException) th;
            if (mysqlIntegrityException.getNextException() != null) {
                return unwindException(mysqlIntegrityException.getNextException());
            }  
        } else if(th instanceof JPQLException) {
            JPQLException jpqlException = (JPQLException) th;
            if (jpqlException.getCause() != null) {
                return unwindException(jpqlException.getCause());
            }  
        } else if(th instanceof NoViableAltException) {
            NoViableAltException noViableAltException = (NoViableAltException) th;
            if (noViableAltException.getCause() != null) {
                return unwindException(noViableAltException.getCause());
            }
        } else if(th instanceof IllegalArgumentException) {
            IllegalArgumentException illegalArgumentException = (IllegalArgumentException) th;
            if (illegalArgumentException.getCause() != null) {
                return unwindException(illegalArgumentException.getCause());
            }
        } else if (th.getCause() != null) {
            return unwindException(th.getCause());
        } 
        return th;
    }
} 
