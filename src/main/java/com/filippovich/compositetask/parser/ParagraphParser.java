package com.filippovich.compositetask.parser;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.composite.TextComponentType;
import com.filippovich.compositetask.composite.TextComposite;

public class ParagraphParser extends AbstractParser {
    public ParagraphParser(AbstractParser nextParser) {
        super(nextParser, TextRegex.SENTENCE_REGEX);
    }

    @Override
    protected TextComponent createNewComponent() {
        return new TextComposite(TextComponentType.SENTENCE);
    }
}