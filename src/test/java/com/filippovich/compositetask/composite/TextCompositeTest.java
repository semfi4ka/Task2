package com.filippovich.compositetask.composite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextCompositeTest {

    @Test
    void testComposeText() {
        TextComponent root = new TextComposite(TextComponentType.TEXT);
        root.add(new TextLeaf("Paragraph1", TextComponentType.PARAGRAPH));
        root.add(new TextLeaf("Paragraph2", TextComponentType.PARAGRAPH));
        assertEquals("Paragraph1\n\nParagraph2", root.compose());
    }

    @Test
    void testComposeParagraph() {
        TextComponent paragraph = new TextComposite(TextComponentType.PARAGRAPH);
        paragraph.add(new TextLeaf("Sentence1.", TextComponentType.SENTENCE));
        paragraph.add(new TextLeaf("Sentence2.", TextComponentType.SENTENCE));
        assertEquals("Sentence1. Sentence2.", paragraph.compose());
    }

    @Test
    void testComposeSentence() {
        TextComponent sentence = new TextComposite(TextComponentType.SENTENCE);
        sentence.add(new TextLeaf("Lexeme1", TextComponentType.LEXEME));
        sentence.add(new TextLeaf("Lexeme2", TextComponentType.LEXEME));
        assertEquals("Lexeme1 Lexeme2", sentence.compose());
    }

    @Test
    void testComposeLexeme() {
        TextComponent lexeme = new TextComposite(TextComponentType.LEXEME);
        lexeme.add(new TextLeaf("S", TextComponentType.SYMBOL));
        lexeme.add(new TextLeaf("y", TextComponentType.SYMBOL));
        lexeme.add(new TextLeaf("m", TextComponentType.SYMBOL));
        assertEquals("Sym", lexeme.compose());
    }
}