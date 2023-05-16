package com.example.demofx.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class VCardUtilsTest {

    @Test
    void prepareAddress() {
        var result = VCardUtils.prepareAddress("street", "city", "region", "postalcode", "country");
        assertEquals("street;city;region;postalcode;country;", result);
    }

    @Test
    void prepareEmptyAddressField() {
        var result = VCardUtils.prepareAddress("", "", "", "", "");
        assertEquals(VCardUtils.EMPTY_FORMATTED_ADDRESS, result);
    }

    @Test
    void formatPhoneNumber() {
        var result = VCardUtils.formatPhoneNumber("+49 111 1111", "home");
        assertEquals("TEL;TYPE=home:+49 111 1111\r\n", result);
    }

    @Test
    void formatAddress() {
        var result = VCardUtils.formatAddress(";;street;city;region;postalcode;country;", "home");
        assertEquals("ADR;TYPE=home:;;street;city;region;postalcode;country;\r\n", result);
    }

    @Test
    void formatEmailAddress() {
        var result = VCardUtils.formatEmailAddress("a@b.com", "home");
        assertEquals("EMAIL;TYPE=home:a@b.com\r\n", result);
    }

    @Test
    void formatFormattedName() {
        var result = VCardUtils.formatFormattedName("first", "middle", "family");
        assertEquals("FN:first middle family\r\n", result);
    }

    @Test
    void formatDisplayName() {
        var result = VCardUtils.formatDisplayName("first", "middle", "family");
        assertEquals("N:family;first;middle\r\n", result);
    }

    @Test
    void formatOrgName() {
        var result = VCardUtils.formatOrgName("Org GmbH");
        assertEquals("ORG:Org GmbH\r\n", result);
    }

    @Test
    void formatOpenTags() {
        var result = VCardUtils.formatOpenTags();
        assertEquals("BEGIN:VCARD\r\nVERSION:4.0\r\n", result);
    }

    @Test
    void formatCloseTags() {
        var result = VCardUtils.formatCloseTags();
        assertEquals("END:VCARD\r\n", result);
    }
}