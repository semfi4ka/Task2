package com.filippovich.compositetask.service;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;
import com.filippovich.compositetask.composite.TextLeaf;
import com.filippovich.compositetask.exeption.TextOperationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SwapFirstLastLexemeOperationServiceTest {

    private SwapFirstLastLexemeOperationService service = new SwapFirstLastLexemeOperationService();

    private TextComponent createLexeme(String word) {
        TextComponent lexeme = new TextComposite(TextComponentType.LEXEME);
        for (char c : word.toCharArray()) {
            lexeme.add(new TextLeaf(String.valueOf(c), TextComponentType.SYMBOL));
        }
        return lexeme;
    }

    @Test
    void testExecuteSwap() throws TextOperationException {
        TextComponent s1 = new TextComposite(TextComponentType.SENTENCE);
        s1.add(createLexeme("A"));
        s1.add(createLexeme("B"));
        s1.add(createLexeme("C"));
        TextComponent root = new TextComposite(TextComponentType.TEXT);
        TextComponent p1 = new TextComposite(TextComponentType.PARAGRAPH);
        root.add(p1);
        p1.add(s1);
        service.execute(root);
        assertEquals("C B A", s1.compose());
    }

    @Test
    void testExecuteSwapTwo() throws TextOperationException {
        TextComponent s1 = new TextComposite(TextComponentType.SENTENCE);
        s1.add(createLexeme("A"));
        s1.add(createLexeme("B"));
        TextComponent root = new TextComposite(TextComponentType.TEXT);
        TextComponent p1 = new TextComposite(TextComponentType.PARAGRAPH);
        root.add(p1);
        p1.add(s1);
        service.execute(root);
        assertEquals("B A", s1.compose());
    }

    @Test
    void testExecuteSwapOne() throws TextOperationException {
        TextComponent s1 = new TextComposite(TextComponentType.SENTENCE);
        s1.add(createLexeme("A"));
        TextComponent root = new TextComposite(TextComponentType.TEXT);
        TextComponent p1 = new TextComposite(TextComponentType.PARAGRAPH);
        root.add(p1);
        p1.add(s1);
        service.execute(root);
        assertEquals("A", s1.compose());
    }
}