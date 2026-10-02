package com.lmslite;

/**
 * Thrown when a loan operation is attempted under invalid conditions,
 * e.g. borrowing a book with no available copies.
 */
public class InvalidLoanException extends RuntimeException {

    public InvalidLoanException(String message) {
        super(message);
    }
}