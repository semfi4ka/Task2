package com.filippovich.compositetask.parser;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;

public class SentenceParser extends AbstractParser {
    public SentenceParser(AbstractParser nextParser) {
        super(nextParser, TextRegex.LEXEME_REGEX);
    }

    @Override
    protected TextComponent createNewComponent() {
        return new TextComposite(TextComponentType.LEXEME);
    }
}