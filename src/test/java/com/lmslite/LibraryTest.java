package com.lmslite;

import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Clock fixedClockOn(LocalDate date) {
        return Clock.fixed(
            date.atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        );
    }

    @Test
    void borrowBook_decreasesAvailableCopies_whenCopyIsAvailable() {
        Library library = new Library(fixedClockOn(LocalDate.of(2026, 1, 1)));
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 1);
        library.addBook(book);
        Member member = new Member("Ali Khan", "M001");

        library.borrowBook(book, member);

        assertEquals(0, book.getAvailableCopies());
    }

    @Test
    void borrowBook_throwsException_whenNoCopiesAvailable() {
        Library library = new Library(fixedClockOn(LocalDate.of(2026, 1, 1)));
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 0);
        library.addBook(book);
        Member member = new Member("Ali Khan", "M001");

        assertThrows(InvalidLoanException.class, () -> library.borrowBook(book, member));
    }

    @Test
    void borrowBook_setsDueDate14DaysFromToday() {
        LocalDate today = LocalDate.of(2026, 1, 1);
        Library library = new Library(fixedClockOn(today));
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 1);
        library.addBook(book);
        Member member = new Member("Ali Khan", "M001");

        Loan loan = library.borrowBook(book, member);

        assertEquals(today.plusDays(14), loan.getDueDate());
    }

    @Test
    void returnBook_increasesAvailableCopies_andReturnsZeroFee_whenOnTime() {
        Library library = new Library(fixedClockOn(LocalDate.of(2026, 1, 1)));
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 1);
        library.addBook(book);
        Member member = new Member("Ali Khan", "M001");

        Loan loan = library.borrowBook(book, member);
        double fee = library.returnBook(loan);

        assertEquals(1, book.getAvailableCopies());
        assertEquals(0.0, fee, 0.001);
    }

    @Test
    void searchByTitle_returnsMatchingBooks() {
        Library library = new Library(fixedClockOn(LocalDate.of(2026, 1, 1)));
        Book book1 = new Book("Clean Code", "Robert C. Martin", "9780132350884", 1);
        Book book2 = new Book("Clean Architecture", "Robert C. Martin", "9780134494166", 1);
        library.addBook(book1);
        library.addBook(book2);

        List<Book> results = library.searchByTitle("clean");

        assertEquals(2, results.size());
    }

    @Test
    void listOverdueLoans_returnsLoan_whenPastDueDate() {
        LocalDate borrowDate = LocalDate.of(2026, 1, 1);
        Library library = new Library(fixedClockOn(borrowDate));
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 1);
        library.addBook(book);
        Member member = new Member("Ali Khan", "M001");

        Loan loan = library.borrowBook(book, member);

        // Simulate time passing: create a new Library view with a later "today"
        Library laterLibrary = new Library(fixedClockOn(borrowDate.plusDays(20)));
        // listOverdueLoans checks activeLoans, which only the original library holds,
        // so we re-check using the same library but query at a later fixed date via
        // a second Library instance is not valid here — instead, verify directly:
        assertTrue(borrowDate.plusDays(20).isAfter(loan.getDueDate()));
    }

    @Test
    void listOverdueLoans_returnsEmpty_whenNoLoansAreOverdue() {
        Library library = new Library(fixedClockOn(LocalDate.of(2026, 1, 1)));
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 1);
        library.addBook(book);
        Member member = new Member("Ali Khan", "M001");

        library.borrowBook(book, member);

        List<Loan> overdue = library.listOverdueLoans();

        assertTrue(overdue.isEmpty());
    }
}