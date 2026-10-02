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
        validateTitle(title);
        validateAuthor(author);
        validateIsbn(isbn);
        validateTotalCopies(totalCopies);

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    private static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidBookDataException("Title must not be empty.");
        }
    }

    private static void validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new InvalidBookDataException("Author must not be empty.");
        }
    }

    private static void validateIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidBookDataException("ISBN must not be empty.");
        }
    }

    private static void validateTotalCopies(int totalCopies) {
        if (totalCopies < 0) {
            throw new InvalidBookDataException("Total copies cannot be negative.");
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

        public void decreaseAvailableCopies() {
        if (availableCopies <= 0) {
            throw new InvalidLoanException("No available copies to borrow.");
        }
        availableCopies--;
    }

    public void increaseAvailableCopies() {
        if (availableCopies >= totalCopies) {
            throw new InvalidLoanException("Cannot exceed total copies.");
        }
        availableCopies++;
    }
}