package com.filippovich.compositetask.parser;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;

public class LexemeParser extends AbstractParser {
    public LexemeParser(AbstractParser nextParser) {
        super(nextParser, TextRegex.SYMBOL_REGEX);
    }

    @Override
    protected TextComponent createNewComponent() {
        return new TextComposite(TextComponentType.SYMBOL);
    }
}