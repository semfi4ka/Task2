package com.filippovich.compositetask.service;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;
import com.filippovich.compositetask.composite.TextLeaf;
import com.filippovich.compositetask.exeption.TextCompositeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SameWordsOperationServiceTest {

    private SameWordsOperationService service = new SameWordsOperationService();

    private TextComponent createLexeme(String word) {
        TextComponent lexeme = new TextComposite(TextComponentType.LEXEME);
        for (char c : word.toCharArray()) {
            lexeme.add(new TextLeaf(String.valueOf(c), TextComponentType.SYMBOL));
        }
        return lexeme;
    }

    @Test
    void testExecuteFindsSameWords() throws TextCompositeException {
        TextComponent s1 = new TextComposite(TextComponentType.SENTENCE);
        s1.add(createLexeme("it"));
        s1.add(createLexeme("works."));

        TextComponent s2 = new TextComposite(TextComponentType.SENTENCE);
        s2.add(createLexeme("no"));
        s2.add(createLexeme("it."));

        TextComponent s3 = new TextComposite(TextComponentType.SENTENCE);
        s3.add(createLexeme("no"));
        s3.add(createLexeme("words."));

        TextComponent root = new TextComposite(TextComponentType.TEXT);
        TextComponent p1 = new TextComposite(TextComponentType.PARAGRAPH);
        root.add(p1);
        p1.add(s1);
        p1.add(s2);
        p1.add(s3);

        String result = service.execute(root);

        assertTrue(result.contains("(2) share the word(s):"));
        assertTrue(result.contains("it") || result.contains("no"));
    }

    @Test
    void testExecuteNoRepeats() throws TextCompositeException {
        TextComponent s1 = new TextComposite(TextComponentType.SENTENCE);
        s1.add(createLexeme("one"));
        TextComponent s2 = new TextComposite(TextComponentType.SENTENCE);
        s2.add(createLexeme("two"));

        TextComponent root = new TextComposite(TextComponentType.TEXT);
        TextComponent p1 = new TextComposite(TextComponentType.PARAGRAPH);
        root.add(p1);
        p1.add(s1);
        p1.add(s2);

        String result = service.execute(root);
        assertEquals("No words are repeated in two or more sentences.", result.trim());
    }
}