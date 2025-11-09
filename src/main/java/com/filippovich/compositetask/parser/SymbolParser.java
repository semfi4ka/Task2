package com.filippovich.compositetask.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextLeaf;

public class SymbolParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger();

    public SymbolParser() {
        super(null, TextRegex.SYMBOL_REGEX);
        logger.debug(getClass().getSimpleName() + " created. Final parser in chain.");
    }

    @Override
    public void parse(TextComponent composite, String textToParse) {
        logger.info(getClass().getSimpleName() + " started parsing lexeme into symbols.");

        textToParse.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .forEach(symbolString -> {
                    if (!symbolString.trim().isEmpty()) {
                        composite.add(new TextLeaf(symbolString, TextComponentType.SYMBOL));
                    }
                });

        logger.info(getClass().getSimpleName() + " finished.");
    }

    @Override
    protected TextComponent createNewComponent() {
        return null;
    }
}