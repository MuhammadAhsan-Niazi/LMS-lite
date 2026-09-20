package com.lmslite;

/**
 * Represents a book in the library catalog.
 *
 * Pre-conditions for construction:
 *   - title must not be null or empty
 *   - author must not be null or empty
 *   - isbn must not be null or empty
 *   - totalCopies must be greater than or equal to 0
 * Post-conditions:
 *   - a Book is created with availableCopies equal to totalCopies
 */
public class Book {

    private final String title;
    private final String author;
    private final String isbn;
    private final int totalCopies;
    private int availableCopies;

    public Book(String title, String author, String isbn, int totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
}