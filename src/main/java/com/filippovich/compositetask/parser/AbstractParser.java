package com.filippovich.compositetask.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.filippovich.compositetask.composite.TextComponent;

public abstract class AbstractParser {
    private static final Logger logger = LogManager.getLogger();
    private AbstractParser nextParser;
    private final String regex;
    private static int parserCount = 0;

    protected AbstractParser(AbstractParser nextParser, String regex) {
        this.nextParser = nextParser;
        this.regex = regex;
        parserCount++;
        logger.debug(getClass().getSimpleName() + " created. Total parser count: " + parserCount);
    }

    public void parse(TextComponent composite, String textToParse) {
        logger.info(getClass().getSimpleName() + " started parsing text into components using regex: " + regex);
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(textToParse);
        int componentCount = 0;

        while (matcher.find()) {
            String componentString = matcher.group().trim();
            if (!componentString.isEmpty()) {
                TextComponent newComponent = createNewComponent();
                composite.add(newComponent);
                componentCount++;

                if (nextParser != null) {
                    logger.trace(getClass().getSimpleName() + " passing component to " + nextParser.getClass().getSimpleName());
                    nextParser.parse(newComponent, componentString);
                } else {
                    logger.warn(getClass().getSimpleName() + " has no next parser in the chain.");
                }
            }
        }
        logger.info(getClass().getSimpleName() + " finished. Total components found: " + componentCount);
    }

    protected abstract TextComponent createNewComponent();
}