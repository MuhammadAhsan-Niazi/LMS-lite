package com.lmslite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void constructor_setsFieldsCorrectly_whenInputIsValid() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 3);

        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
        assertEquals("9780132350884", book.getIsbn());
        assertEquals(3, book.getTotalCopies());
        assertEquals(3, book.getAvailableCopies());
    }

    @Test
    void constructor_throwsException_whenTitleIsEmpty() {
        assertThrows(InvalidBookDataException.class, () ->
            new Book("", "Robert C. Martin", "9780132350884", 3)
        );
    }

    @Test
    void constructor_throwsException_whenTitleIsNull() {
        assertThrows(InvalidBookDataException.class, () ->
            new Book(null, "Robert C. Martin", "9780132350884", 3)
        );
    }

    @Test
    void constructor_throwsException_whenAuthorIsEmpty() {
        assertThrows(InvalidBookDataException.class, () ->
            new Book("Clean Code", "", "9780132350884", 3)
        );
    }

    @Test
    void constructor_throwsException_whenTotalCopiesIsNegative() {
        assertThrows(InvalidBookDataException.class, () ->
            new Book("Clean Code", "Robert C. Martin", "9780132350884", -1)
        );
    }

    @Test
    void constructor_allowsZeroCopies_asValidEdgeCase() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 0);
        assertEquals(0, book.getAvailableCopies());
    }

        @Test
    void constructor_throwsException_whenAuthorIsNull() {
        assertThrows(InvalidBookDataException.class, () ->
            new Book("Clean Code", null, "9780132350884", 3)
        );
    }

    @Test
    void constructor_throwsException_whenIsbnIsEmpty() {
        assertThrows(InvalidBookDataException.class, () ->
            new Book("Clean Code", "Robert C. Martin", "", 3)
        );
    }

    @Test
    void constructor_throwsException_whenIsbnIsNull() {
        assertThrows(InvalidBookDataException.class, () ->
            new Book("Clean Code", "Robert C. Martin", null, 3)
        );
    }

    @Test
    void matchesTitle_returnsTrue_whenQueryIsSubstringOfTitle() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 3);
        assertTrue(book.matchesTitle("clean"));
    }

    @Test
    void matchesTitle_returnsFalse_whenQueryDoesNotMatch() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 3);
        assertFalse(book.matchesTitle("java"));
    }

    @Test
    void matchesAuthor_returnsTrue_whenQueryIsSubstringOfAuthor() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 3);
        assertTrue(book.matchesAuthor("martin"));
    }

    @Test
    void decreaseAvailableCopies_throwsException_whenNoCopiesLeft() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 0);
        assertThrows(InvalidLoanException.class, book::decreaseAvailableCopies);
    }

    @Test
    void increaseAvailableCopies_throwsException_whenAtTotalCopies() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 1);
        assertThrows(InvalidLoanException.class, book::increaseAvailableCopies);
    }

    @Test
    void decreaseThenIncreaseAvailableCopies_returnsToOriginalCount() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 2);
        book.decreaseAvailableCopies();
        book.increaseAvailableCopies();
        assertEquals(2, book.getAvailableCopies());
    }
}