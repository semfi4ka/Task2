package com.filippovich.compositetask.reader;

import com.filippovich.compositetask.exeption.TextCompositeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DataReader {
    private static final Logger logger = LogManager.getLogger();

    private DataReader() {}

    public static String readFromFile(String filePath) throws TextCompositeException {
        logger.info("Attempting to read file: " + filePath);

        String content;
        try {
            content = Files.lines(Paths.get(filePath)).collect(Collectors.joining("\n"));
            logger.debug("Raw file content read. Length: " + content.length());

            String cleanContent = content.replaceAll("[\t ]+", " ");
            logger.debug("Whitespace normalized. Final length: " + cleanContent.length());

            return cleanContent;
        } catch (IOException e) {
            logger.error("Failed to read file: " + filePath, e);
            throw new TextCompositeException("Error reading data from file: " + filePath, e);
        }
    }
}