package com.lmslite;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates the book catalog, members, and active loans for the library.
 *
 * Pre-conditions:
 *   - a Clock must be supplied so "today" is testable and deterministic
 * Post-conditions:
 *   - borrowBook creates a Loan, reduces the book's available copies by one
 *   - returnBook marks a Loan as returned, increases available copies, and
 *     returns any late fee owed
 */
public class Library {

    private final Clock clock;
    private final List<Book> catalog = new ArrayList<>();
    private final List<Loan> activeLoans = new ArrayList<>();

    public Library(Clock clock) {
        this.clock = clock;
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new InvalidBookDataException("Book must not be null.");
        }
        catalog.add(book);
    }

    public List<Book> searchByTitle(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : catalog) {
            if (book.matchesTitle(query)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> searchByAuthor(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : catalog) {
            if (book.matchesAuthor(query)) {
                results.add(book);
            }
        }
        return results;
    }

    public Loan borrowBook(Book book, Member member) {
        validateBorrow(book, member);
        LocalDate today = LocalDate.now(clock);
        book.decreaseAvailableCopies();
        Loan loan = new Loan(book, member, today);
        activeLoans.add(loan);
        return loan;
    }

    private void validateBorrow(Book book, Member member) {
        if (book == null) {
            throw new InvalidLoanException("Book must not be null.");
        }
        if (member == null) {
            throw new InvalidLoanException("Member must not be null.");
        }
        if (book.getAvailableCopies() <= 0) {
            throw new InvalidLoanException("No copies available to borrow.");
        }
    }

    public double returnBook(Loan loan) {
        if (loan == null) {
            throw new InvalidLoanException("Loan must not be null.");
        }
        LocalDate today = LocalDate.now(clock);
        double lateFee = loan.returnBook(today);
        activeLoans.remove(loan);
        return lateFee;
    }

    public List<Loan> listOverdueLoans() {
        List<Loan> overdue = new ArrayList<>();
        LocalDate today = LocalDate.now(clock);
        for (Loan loan : activeLoans) {
            if (today.isAfter(loan.getDueDate())) {
                overdue.add(loan);
            }
        }
        return overdue;
    }
}