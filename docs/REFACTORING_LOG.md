# Refactoring Log — LMS-Lite

This log documents genuine refactorings performed during development, each
verified by re-running the full test suite afterward to confirm behavior
was preserved.

---

## Refactor 1: Extract Due Date Calculation into Named Method

**Smell:** Long Method / constructor doing more than one job — the `Loan`
constructor was both assigning fields and computing the due date inline.

**Technique:** Extract Method

**Before:**
```java
public Loan(Book book, Member member, LocalDate borrowDate) {
    this.book = book;
    this.member = member;
    this.borrowDate = borrowDate;
    this.dueDate = borrowDate.plusDays(LOAN_PERIOD_DAYS);
}
```

**After:**
```java
public Loan(Book book, Member member, LocalDate borrowDate) {
    this.book = book;
    this.member = member;
    this.borrowDate = borrowDate;
    this.dueDate = calculateDueDate(borrowDate);
}

private static LocalDate calculateDueDate(LocalDate borrowDate) {
    return borrowDate.plusDays(LOAN_PERIOD_DAYS);
}
```

**Result:** The constructor now reads as a simple sequence of assignments;
the due-date rule is isolated and independently testable/readable.

Commit: `9f3ed78`

---

## Refactor 2: Replace Nested Conditional with Intention-Revealing Method

**Smell:** Unclear conditional logic — `calculateLateFee` mixed the "is this
late at all?" check with the day-counting math, making intent harder to read.

**Technique:** Extract Method (guard clause with a named predicate)

**Before:**
```java
public double calculateLateFee(LocalDate returnDate) {
    long daysLate = calculateDaysLate(returnDate);
    if (daysLate <= 0) {
        return 0.0;
    }
    return daysLate * LATE_FEE_PER_DAY;
}
```

**After:**
```java
public double calculateLateFee(LocalDate returnDate) {
    if (!isReturnedLate(returnDate)) {
        return 0.0;
    }
    long daysLate = calculateDaysLate(returnDate);
    return daysLate * LATE_FEE_PER_DAY;
}

private boolean isReturnedLate(LocalDate returnDate) {
    return returnDate.isAfter(dueDate);
}
```

**Result:** The guard clause now reads in plain English ("if not returned
late, return zero"), separating the late-check from the fee-math.

Commit: `1acb2b3`

---

## Refactor 3: Use the Correct Exception Type for Loan-Related Violations

**Smell:** Inappropriate exception type — `Book.decreaseAvailableCopies()`
and `increaseAvailableCopies()` threw `InvalidBookDataException`, which is
meant for malformed book *data* (bad title/author/isbn), not for runtime
*borrowing* violations like "no copies available."

**Technique:** Replace with a more specific, correctly-scoped type

**Before:**
```java
public void decreaseAvailableCopies() {
    if (availableCopies <= 0) {
        throw new InvalidBookDataException("No available copies to borrow.");
    }
    availableCopies--;
}
```

**After:**
```java
public void decreaseAvailableCopies() {
    if (availableCopies <= 0) {
        throw new InvalidLoanException("No available copies to borrow.");
    }
    availableCopies--;
}
```

**Result:** Exception types now correctly communicate the category of
failure, making it possible for calling code to distinguish "bad book data"
from "invalid loan operation" if it ever needs to catch them separately.

Commit: `8aef50f`

---

## Refactor 4: Add Missing Javadoc and Fix Static Analysis Violations

**Smell:** Undocumented public constructors/methods and a line-length
violation, flagged by running Checkstyle (Google style rules) over the
full codebase.

**Technique:** Add Javadoc comments; Extract Variable (for the long line)

**Before (Main.java, line length violation):**
```java
System.out.println("Registered member: " + member.getName() + " (" + member.getMemberId() + ")");
```

**After:**
```java
String memberInfo = "Registered member: " + member.getName() + " (" + member.getMemberId() + ")";
System.out.println(memberInfo);
```

**Before (Book.java, missing Javadoc):**
```java
public Book(String title, String author, String isbn, int totalCopies) {
```

**After:**
```java
/**
 * Creates a new Book after validating all required fields.
 *
 * @param title the book's title, must not be empty
 * @param author the book's author, must not be empty
 * @param isbn the book's ISBN, must not be empty
 * @param totalCopies the total number of copies owned, must be >= 0
 */
public Book(String title, String author, String isbn, int totalCopies) {
```

(Similar Javadoc additions were made to `Loan`'s constructor and
`Library.addBook`, and a missing `<p>` tag was added to `Member`'s class
Javadoc.)

**Result:** 5 Checkstyle violations resolved; public API is now documented
for future maintainers.

Commit: `d3f81c3`

---

## Summary

| # | Smell | Technique | Commit |
|---|-------|-----------|--------|
| 1 | Long method (constructor doing two jobs) | Extract Method | `9f3ed78` |
| 2 | Unclear conditional | Extract Method (guard clause) | `1acb2b3` |
| 3 | Wrong exception type | Replace with correct type | `8aef50f` |
| 4 | Missing documentation / long line | Add Javadoc, Extract Variable | `d3f81c3` |

All refactors were verified by running `mvn clean test` immediately
afterward, confirming all tests still passed (behavior preserved).