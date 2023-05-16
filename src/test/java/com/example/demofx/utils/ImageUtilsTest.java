package com.example.demofx.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilsTest {

    @Test
    void happyDaysBufferedImageReturned() throws Exception {
        assertNotNull(ImageUtils.generateQRCodeImage("xxx"));
    }

    @Test
    void nullThrowsAnException() throws Exception {
        assertThrows(NullPointerException.class, () -> ImageUtils.generateQRCodeImage(null));
    }

}