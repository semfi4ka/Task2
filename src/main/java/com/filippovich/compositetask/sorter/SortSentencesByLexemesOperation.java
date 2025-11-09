package com.filippovich.compositetask.sorter;

import com.filippovich.compositetask.exeption.TextOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortSentencesByLexemesOperation implements TextOperation {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(TextComponent textComponent) throws TextOperationException {
        logger.info("Starting: Sort sentences by lexeme count.");

        if (textComponent.getType() != TextComponentType.PARAGRAPH) {
            logger.error("Root component type is incorrect: " + textComponent.getType());
            throw new TextOperationException("Invalid root component type. Expected PARAGRAPH.");
        }

        List<TextComponent> allSentences;

        allSentences = textComponent.getComponents().stream()
                .filter(s -> s.getType() == TextComponentType.SENTENCE)
                .collect(Collectors.toList());

        logger.debug("Total sentences collected for sorting: " + allSentences.size());

        allSentences.sort(Comparator.comparingInt(s -> s.getComponents().size()));
        logger.debug("Sentences sorted by lexeme count successfully.");

        StringBuilder sb = new StringBuilder("Sentences sorted by lexeme count (ascending):\n");
        for (TextComponent sentence : allSentences) {
            int lexemeCount = sentence.getComponents().size();
            sb.append("[").append(lexemeCount).append(" lexeme(s)]: ").append(sentence.compose()).append("\n");
        }

        logger.info("Finished.");
        return sb.toString();
    }
}