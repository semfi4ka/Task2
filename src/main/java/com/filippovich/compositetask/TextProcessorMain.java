package com.filippovich.compositetask;

import com.filippovich.compositetask.exeption.FileReadException;
import com.filippovich.compositetask.exeption.TextOperationException;
import com.filippovich.compositetask.service.SameWordsOperationService;
import com.filippovich.compositetask.service.SortSentencesByLexemesOperationService;
import com.filippovich.compositetask.service.SwapFirstLastLexemeOperationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;
import com.filippovich.compositetask.parser.*;
import com.filippovich.compositetask.reader.DataReader;

public class TextProcessorMain {
    private static final Logger logger = LogManager.getLogger();
    private static final String FILE_PATH = "data/text.txt";

    public static void main(String[] args) {
        logger.info("Application starting...");
        try {
            String rawText = DataReader.readFromFile(FILE_PATH);
            logger.info("--- Starting text processing ---");

            AbstractParser symbolParser = new SymbolParser();
            AbstractParser lexemeParser = new LexemeParser(symbolParser);
            AbstractParser sentenceParser = new SentenceParser(lexemeParser);
            AbstractParser paragraphParser = new ParagraphParser(sentenceParser);
            AbstractParser textParser = new TextParser(paragraphParser);

            TextComponent fullTextComposite = new TextComposite(TextComponentType.TEXT);
            logger.debug("Starting full text parsing...");
            textParser.parse(fullTextComposite, rawText);
            logger.info("Text successfully parsed into a hierarchical structure (Composite).");

            String restoredText = fullTextComposite.compose();
            logger.info("\n--- Restored Text ---\n" + restoredText + "\n------------------------------\n");

            try {
                TextComponent serviceRoot = new TextComposite(TextComponentType.PARAGRAPH);

                for (TextComponent paragraph : fullTextComposite.getComponents()) {
                    if (paragraph.getType() == TextComponentType.PARAGRAPH) {
                        for (TextComponent sentence : paragraph.getComponents()) {
                            serviceRoot.add(sentence);
                        }
                    }
                }

                logger.info("--- Executing Same Words ---");
                SameWordsOperationService op1 = new SameWordsOperationService();
                System.out.println(op1.execute(serviceRoot));

                logger.info("--- Executing Sort Sentences ---");
                SortSentencesByLexemesOperationService op2 = new SortSentencesByLexemesOperationService();
                System.out.println(op2.execute(serviceRoot));

                logger.info("--- Executing Swap Lexemes ---");
                SwapFirstLastLexemeOperationService op3 = new SwapFirstLastLexemeOperationService();
                System.out.println(op3.execute(fullTextComposite));

                String modifiedText = fullTextComposite.compose();
                logger.info("--- Text after executing Modified Model ---\n" + modifiedText + "\n------------------------------\n");

            } catch (TextOperationException e) {
                logger.error("Text Operation failed: " + e.getMessage(), e);
            }

        } catch (FileReadException e) {
            logger.error("Error reading file: " + FILE_PATH + ". Please ensure the file exists.", e);
        }
        logger.info("Application finished.");
    }
}