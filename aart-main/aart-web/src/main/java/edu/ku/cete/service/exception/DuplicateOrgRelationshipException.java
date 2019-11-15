package edu.ku.cete.service.exception;

public class DuplicateOrgRelationshipException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateOrgRelationshipException() {
        super();
    }

    public DuplicateOrgRelationshipException(String message) {
        super(message);
    }
}
