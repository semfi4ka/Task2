package com.filippovich.compositetask.service;

import com.filippovich.compositetask.exeption.TextOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import java.util.ArrayList;
import java.util.List;

public class SwapFirstLastLexemeOperationService implements TextOperation {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(TextComponent textComponent) throws TextOperationException {
        logger.info("Starting: Swap first and last lexemes in sentences.");

        List<TextComponent> allSentences = new ArrayList<>();
        findAllComponents(textComponent, TextComponentType.SENTENCE, allSentences);

        logger.debug("Total sentences found for swapping (recursively): " + allSentences.size());

        allSentences.forEach(this::swapLexemesInSentence);

        logger.info("Finished. Lexemes swapped in all eligible sentences.");
        return "First and last lexemes swapped in every sentence.\n";
    }

    private void findAllComponents(TextComponent component, TextComponentType typeToFind, List<TextComponent> result) {
        if (component.getType() == typeToFind) {
            result.add(component);
            return;
        }
        if (!component.getComponents().isEmpty()) {
            for (TextComponent child : component.getComponents()) {
                findAllComponents(child, typeToFind, result);
            }
        }
    }

    private void swapLexemesInSentence(TextComponent sentence) {
        List<TextComponent> lexemes = sentence.getComponents();
        int lexemeCount = lexemes.size();

        if (lexemeCount < 2) {
            logger.trace("Skipping sentence (less than 2 lexemes): " + sentence.compose());
            return;
        }

        int firstLexemeIndex = 0;
        int lastLexemeIndex = lexemeCount - 1;

        TextComponent firstLexeme = lexemes.get(firstLexemeIndex);
        TextComponent lastLexeme = lexemes.get(lastLexemeIndex);

        sentence.getComponents().set(firstLexemeIndex, lastLexeme);
        sentence.getComponents().set(lastLexemeIndex, firstLexeme);
        logger.trace("Swapped lexemes in sentence: " + sentence.compose());
    }
}