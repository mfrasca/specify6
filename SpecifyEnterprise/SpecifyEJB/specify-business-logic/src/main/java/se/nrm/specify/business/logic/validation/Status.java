package se.nrm.specify.business.logic.validation;
 
import se.nrm.specify.business.logic.exception.DatabaseException;
import se.nrm.specify.business.logic.exception.DuplicateNameException;
import se.nrm.specify.business.logic.exception.DuplicteNumberException;

/**
 *
 * @author idali
 */ 
public enum Status {

    DuplicateNumber,
    DuplicateName,
    DuplicateAgentRole, 
    DuplicatePermit,
    ForeignKeyConstraint,
    FieldCanNotBeNull,
    PrimaryAddressForAgent,
    CurrentDetermination,
    ShipToAgentNotExist,
    ShipToAddressNotExist,
    Exist, 
    CreateNew,
    Save,
    Update,
    Delete, 
    Database,
    ConstraintViolation,
    InvalidUserName,
    Other;

    /**
     * Simple way to create an Operation from an Exception, to simplify error logging when the operation is hard to determine.
     * @param exception the occurred exception
     * @return the corresponding operation.
     */
    static Status fromException(Exception exception) {
        if (exception instanceof DuplicteNumberException) {
            return DuplicateNumber;
        }
        if (exception instanceof DuplicateNameException) {
            return DuplicateName;
        }
        if (exception instanceof DatabaseException) {
            return Database;
        }
        return Other;
    }

    public String getValidationText() {
        return this.toString();
    }
}
