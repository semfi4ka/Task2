package com.filippovich.compositetask;

import com.filippovich.compositetask.exeption.TextOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;
import com.filippovich.compositetask.sorter.*;
import com.filippovich.compositetask.parser.*;
import com.filippovich.compositetask.reader.DataReader;
import java.io.IOException;

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

            TextComponent textComposite = new TextComposite(TextComponentType.PARAGRAPH);
            logger.debug("Starting full text parsing...");
            textParser.parse(textComposite, rawText);
            logger.info("Text successfully parsed into a hierarchical structure (Composite).");

            String restoredText = textComposite.compose();
            logger.info("\n--- Restored Text ---\n" + restoredText + "\n------------------------------\n");

            try {
                logger.info("--- Executing Same Words ---");
                SameWordsOperation op1 = new SameWordsOperation();
                System.out.println(op1.execute(textComposite));

                logger.info("--- Executing Sort Sentences ---");
                SortSentencesByLexemesOperation op2 = new SortSentencesByLexemesOperation();
                System.out.println(op2.execute(textComposite));

                logger.info("--- Executing Swap Lexemes ---");
                SwapFirstLastLexemeOperation op3 = new SwapFirstLastLexemeOperation();
                System.out.println(op3.execute(textComposite));

                String modifiedText = textComposite.compose();
                logger.info("--- Text after executing Modified Model ---\n" + modifiedText + "\n------------------------------\n");

            } catch (TextOperationException e) {
                logger.error("Text Operation failed: " + e.getMessage(), e);
            }

        } catch (IOException e) {
            logger.error("Error reading file: " + FILE_PATH + ". Please ensure the file exists.", e);
        }
        logger.info("Application finished.");
    }
}