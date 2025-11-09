package com.filippovich.compositetask.sorter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.exeption.TextOperationException;
import java.util.List;
import java.util.stream.Collectors;

public class SwapFirstLastLexemeOperation implements TextOperation {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(TextComponent textComponent) throws TextOperationException {
        logger.info("Starting: Swap first and last lexemes in sentences.");

        if (textComponent.getType() != TextComponentType.PARAGRAPH) {
            logger.error("Root component type is incorrect: " + textComponent.getType());
            throw new TextOperationException("Invalid root component type. Expected PARAGRAPH.");
        }

        List<TextComponent> allSentences;

        allSentences = textComponent.getComponents().stream()
                .filter(s -> s.getType() == TextComponentType.SENTENCE)
                .collect(Collectors.toList());

        logger.debug("Total sentences found for swapping: " + allSentences.size());

        allSentences.forEach(this::swapLexemesInSentence);

        logger.info("Finished. Lexemes swapped in all eligible sentences.");
        return "First and last lexemes swapped in every sentence.\n";
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