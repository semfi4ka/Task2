package com.filippovich.compositetask.composite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextLeafTest {

    private TextComponent leaf;

    @BeforeEach
    void setUp() {
        leaf = new TextLeaf("SymbolA", TextComponentType.SYMBOL);
    }

    @Test
    void testCompose() {
        assertEquals("SymbolA", leaf.compose());
    }

    @Test
    void testAddThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            leaf.add(new TextLeaf("B", TextComponentType.SYMBOL));
        });
    }

    @Test
    void testRemoveThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            leaf.remove(null);
        });
    }

    @Test
    void testGetComponents() {
        assertTrue(leaf.getComponents().isEmpty());
    }
}