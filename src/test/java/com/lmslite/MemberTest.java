package com.lmslite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void constructor_setsFieldsCorrectly_whenInputIsValid() {
        Member member = new Member("Ali Khan", "M001");

        assertEquals("Ali Khan", member.getName());
        assertEquals("M001", member.getMemberId());
    }

    @Test
    void constructor_throwsException_whenNameIsEmpty() {
        assertThrows(InvalidMemberDataException.class, () ->
            new Member("", "M001")
        );
    }

    @Test
    void constructor_throwsException_whenNameIsNull() {
        assertThrows(InvalidMemberDataException.class, () ->
            new Member(null, "M001")
        );
    }

    @Test
    void constructor_throwsException_whenMemberIdIsEmpty() {
        assertThrows(InvalidMemberDataException.class, () ->
            new Member("Ali Khan", "")
        );
    }

    @Test
    void constructor_throwsException_whenMemberIdIsNull() {
        assertThrows(InvalidMemberDataException.class, () ->
            new Member("Ali Khan", null)
        );
    }
}