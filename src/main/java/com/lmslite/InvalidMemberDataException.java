package com.lmslite;

/**
 * Thrown when member data provided to the system fails validation,
 * e.g. an empty name or member ID.
 */
public class InvalidMemberDataException extends RuntimeException {

    public InvalidMemberDataException(String message) {
        super(message);
    }
}