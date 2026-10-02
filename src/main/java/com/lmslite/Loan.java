package com.lmslite;

import java.time.LocalDate;

/**
 * Represents a loan of a Book to a Member.
 *
 * Pre-conditions for construction:
 *   - book must not be null
 *   - member must not be null
 *   - borrowDate must not be null
 * Post-conditions:
 *   - a Loan is created with dueDate = borrowDate + 14 days
 */
public class Loan {

    private static final int LOAN_PERIOD_DAYS = 14;

    private final Book book;
    private final Member member;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;

    public Loan(Book book, Member member, LocalDate borrowDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = calculateDueDate(borrowDate);
    }

    private static LocalDate calculateDueDate(LocalDate borrowDate) {
        return borrowDate.plusDays(LOAN_PERIOD_DAYS);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double calculateLateFee(LocalDate returnDate) {
        return 0.0;
    }
}