package com.lmslite;

import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void calculateLateFee_returnsZero_whenReturnedOnDueDate() {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 2);
        Member member = new Member("Ali Khan", "M001");

        LocalDate borrowDate = LocalDate.of(2026, 1, 1);
        Clock fixedClock = Clock.fixed(
            borrowDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        );

        Loan loan = new Loan(book, member, borrowDate);
        LocalDate dueDate = loan.getDueDate(); // should be borrowDate + 14 days

        double fee = loan.calculateLateFee(dueDate);

        assertEquals(0.0, fee, 0.001);
    }
}