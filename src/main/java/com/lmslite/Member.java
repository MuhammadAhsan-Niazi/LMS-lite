package com.lmslite;

/**
 * Represents a library member.
 *
 * Pre-conditions for construction:
 *   - name must not be null or empty
 *   - memberId must not be null or empty
 * Post-conditions:
 *   - a Member is created with the given name and memberId
 */
public class Member {

    private final String name;
    private final String memberId;

    public Member(String name, String memberId) {
        validateName(name);
        validateMemberId(memberId);

        this.name = name;
        this.memberId = memberId;
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMemberDataException("Name must not be empty.");
        }
    }

    private static void validateMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new InvalidMemberDataException("Member ID must not be empty.");
        }
    }

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }
}