package com.filippovich.compositetask.parser;

public final class TextRegex {
    public static final String PARAGRAPH_REGEX = "(.+?)(\\n{2,}|$)";
    public static final String SENTENCE_REGEX = "([A-ZА-Я].+?([.?!])(\\s+|$))";
    public static final String LEXEME_REGEX = "(\\S+)(\\s*)";
    public static final String SYMBOL_REGEX = ".";
    private TextRegex() {}
}