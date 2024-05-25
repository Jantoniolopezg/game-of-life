package net.gameoflife.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceBundleUtilTest {
    @Test
    void getLabel() {
        String result = ResourceBundleUtil.getLabel("screen.main.menu");
        assertEquals("Menú principal", result);
    }

    @Test
    void getLabelWithMissingPropertyShouldReturnEmptyString() {
        String result = ResourceBundleUtil.getLabel("non.existing.property");
        assertEquals("", result);
    }
}