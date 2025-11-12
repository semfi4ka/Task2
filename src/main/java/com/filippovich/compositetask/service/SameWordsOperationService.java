package com.filippovich.compositetask.service;

import com.filippovich.compositetask.exeption.TextOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import java.util.*;
import java.util.stream.Collectors;

public class SameWordsOperationService implements TextOperation {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(TextComponent textComponent) throws TextOperationException {
        logger.info("Starting: Find words repeated in multiple sentences.");

        if (textComponent.getType() != TextComponentType.PARAGRAPH) {
            logger.error("Root component type is incorrect: " + textComponent.getType());
            throw new TextOperationException("Invalid root component type. Expected PARAGRAPH.");
        }

        Map<String, List<TextComponent>> wordToSentencesMap = new HashMap<>();

        List<TextComponent> allSentences = textComponent.getComponents().stream()
                .filter(s -> s.getType() == TextComponentType.SENTENCE)
                .collect(Collectors.toList());

        logger.debug("Total sentences found: " + allSentences.size());

        for (TextComponent sentence : allSentences) {
            Set<String> uniqueWordsInSentence = sentence.getComponents().stream()
                    .filter(l -> l.getType() == TextComponentType.LEXEME)
                    .map(lexemeComposite -> lexemeComposite.compose().replaceAll("[^A-Za-zА-Яа-я]", "").toLowerCase())
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toSet());

            for (String word : uniqueWordsInSentence) {
                wordToSentencesMap.computeIfAbsent(word, k -> new ArrayList<>()).add(sentence);
            }
        }

        int maxCount = 0;
        List<String> wordsWithMaxCount = new ArrayList<>();

        for (Map.Entry<String, List<TextComponent>> entry : wordToSentencesMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                logger.trace("Word '" + entry.getKey() + "' repeated in " + entry.getValue().size() + " sentences.");
                if (entry.getValue().size() > maxCount) {
                    maxCount = entry.getValue().size();
                    wordsWithMaxCount.clear();
                    wordsWithMaxCount.add(entry.getKey());
                } else if (entry.getValue().size() == maxCount) {
                    wordsWithMaxCount.add(entry.getKey());
                }
            }
        }

        logger.info("Finished. Max repetition count found: " + maxCount);

        if (maxCount == 0) {
            return "No words are repeated in two or more sentences.";
        }

        StringBuilder result = new StringBuilder();
        result.append("The maximum number of sentences (").append(maxCount)
                .append(") share the word(s): ").append(String.join(", ", wordsWithMaxCount)).append("\n");
        result.append("Example sentences for word '").append(wordsWithMaxCount.get(0)).append("':\n");

        wordToSentencesMap.get(wordsWithMaxCount.get(0)).stream()
                .forEach(s -> result.append(" - ").append(s.compose()).append("\n"));

        return result.toString();
    }
}