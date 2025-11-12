package com.filippovich.compositetask.service;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;
import com.filippovich.compositetask.composite.TextLeaf;
import com.filippovich.compositetask.exeption.TextOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortSentencesByLexemesOperationServiceTest {

    private TextComponent root;
    private SortSentencesByLexemesOperationService service;

    @BeforeEach
    void setUp() {
        service = new SortSentencesByLexemesOperationService();
        root = new TextComposite(TextComponentType.TEXT);
        TextComponent p1 = new TextComposite(TextComponentType.PARAGRAPH);
        root.add(p1);
        TextComponent s1 = new TextComposite(TextComponentType.SENTENCE);
        s1.add(new TextComposite(TextComponentType.LEXEME));
        s1.add(new TextComposite(TextComponentType.LEXEME));
        s1.add(new TextComposite(TextComponentType.LEXEME));
        p1.add(s1);

        TextComponent s2 = new TextComposite(TextComponentType.SENTENCE);
        s2.add(new TextComposite(TextComponentType.LEXEME));
        p1.add(s2);

        TextComponent s3 = new TextComposite(TextComponentType.SENTENCE);
        s3.add(new TextComposite(TextComponentType.LEXEME));
        s3.add(new TextComposite(TextComponentType.LEXEME));
        p1.add(s3);
    }

    @Test
    void testExecuteSort() throws TextOperationException {
        root.getComponents().get(0).getComponents().get(0).add(new TextComposite(TextComponentType.LEXEME));
        root.getComponents().get(0).getComponents().get(1).add(new TextComposite(TextComponentType.LEXEME));
        root.getComponents().get(0).getComponents().get(2).add(new TextComposite(TextComponentType.LEXEME));

        TextComponent s1_real = new TextComposite(TextComponentType.SENTENCE);
        s1_real.add(new TextLeaf("Three", TextComponentType.LEXEME));
        s1_real.add(new TextLeaf("words", TextComponentType.LEXEME));
        s1_real.add(new TextLeaf("here.", TextComponentType.LEXEME));

        TextComponent s2_real = new TextComposite(TextComponentType.SENTENCE);
        s2_real.add(new TextLeaf("Bye.", TextComponentType.LEXEME));

        TextComponent s3_real = new TextComposite(TextComponentType.SENTENCE);
        s3_real.add(new TextLeaf("Two", TextComponentType.LEXEME));
        s3_real.add(new TextLeaf("words.", TextComponentType.LEXEME));

        TextComponent testRoot = new TextComposite(TextComponentType.TEXT);
        TextComponent p1_real = new TextComposite(TextComponentType.PARAGRAPH);
        testRoot.add(p1_real);
        p1_real.add(s1_real);
        p1_real.add(s2_real);
        p1_real.add(s3_real);

        String result = service.execute(testRoot);

        String expected = "Bye.\n" +
                "Two words.\n" +
                "Three words here.";
        assertEquals(expected, result.trim());
    }
}