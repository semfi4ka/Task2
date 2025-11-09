package com.filippovich.compositetask.parser;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;

public class TextParser extends AbstractParser {
    public TextParser(AbstractParser nextParser) {
        super(nextParser, TextRegex.PARAGRAPH_REGEX);
    }

    @Override
    protected TextComponent createNewComponent() {
        return new TextComposite(TextComponentType.PARAGRAPH);
    }
}