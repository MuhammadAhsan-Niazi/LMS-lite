package com.lmslite;

/**
 * Thrown when book data provided to the system fails validation,
 * e.g. an empty title or a negative copy count.
 */
public class InvalidBookDataException extends RuntimeException {

    public InvalidBookDataException(String message) {
        super(message);
    }
}