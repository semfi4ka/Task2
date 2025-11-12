package com.filippovich.compositetask.service;

import com.filippovich.compositetask.exeption.TextOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortSentencesByLexemesOperationService implements TextOperation {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(TextComponent textComponent) throws TextOperationException {
        logger.info("Starting: Sort sentences by lexeme count.");

        List<TextComponent> allSentences = new ArrayList<>();
        findAllComponents(textComponent, TextComponentType.SENTENCE, allSentences);

        logger.debug("Total sentences collected for sorting (recursively): " + allSentences.size());

        allSentences.sort(Comparator.comparingInt(s -> s.getComponents().size()));
        logger.debug("Sentences sorted by lexeme count successfully.");

        StringBuilder sb = new StringBuilder();

        logger.info("Sentences sorted by lexeme count (ascending):");

        for (TextComponent sentence : allSentences) {
            int lexemeCount = sentence.getComponents().size();
            String sentenceText = sentence.compose();

            logger.info("[" + lexemeCount + " lexeme(s)]: " + sentenceText);

            sb.append(sentenceText).append("\n");
        }

        logger.info("Finished.");

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
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
}