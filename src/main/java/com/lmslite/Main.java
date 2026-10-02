package com.lmslite;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

/**
 * Simple driver program demonstrating LMS-Lite's core functionality:
 * adding books, registering members, borrowing, returning, and
 * listing overdue loans.
 */
public class Main {

    public static void main(String[] args) {
        Library library = new Library(Clock.systemDefaultZone());

        Book book1 = new Book("Clean Code", "Robert C. Martin", "9780132350884", 2);
        Book book2 = new Book("The Pragmatic Programmer", "Andrew Hunt", "9780135957059", 1);
        library.addBook(book1);
        library.addBook(book2);

        System.out.println("Catalog initialized with 2 books.");

        Member member = new Member("Ali Khan", "M001");
        System.out.println("Registered member: " + member.getName() + " (" + member.getMemberId() + ")");

        System.out.println("\nSearching for 'clean':");
        List<Book> results = library.searchByTitle("clean");
        for (Book b : results) {
            System.out.println("  Found: " + b.getTitle() + " by " + b.getAuthor());
        }

        System.out.println("\nBorrowing 'Clean Code' for " + member.getName() + "...");
        Loan loan = library.borrowBook(book1, member);
        System.out.println("  Due date: " + loan.getDueDate());
        System.out.println("  Available copies left: " + book1.getAvailableCopies());

        System.out.println("\nReturning 'Clean Code' on time...");
        double fee = library.returnBook(loan);
        System.out.println("  Late fee: $" + fee);
        System.out.println("  Available copies now: " + book1.getAvailableCopies());

        System.out.println("\nChecking for overdue loans...");
        List<Loan> overdue = library.listOverdueLoans();
        System.out.println("  Overdue loans: " + overdue.size());

        System.out.println("\nDemo complete.");
    }
}