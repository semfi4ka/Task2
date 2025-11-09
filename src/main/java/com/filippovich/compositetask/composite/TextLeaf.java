package com.filippovich.compositetask.composite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Collections;
import java.util.List;

public class TextLeaf implements TextComponent {
    private static final Logger logger = LogManager.getLogger();
    private final String content;
    private final TextComponentType type;

    public TextLeaf(String content, TextComponentType type) {
        this.content = content;
        this.type = type;
        logger.trace("TextLeaf created: [" + type + "] " + content);
    }

    @Override
    public String compose() {
        return content;
    }

    @Override
    public void add(TextComponent component) {
        logger.error("Attempted to add component to TextLeaf (" + type + "): Operation not supported.");
        throw new UnsupportedOperationException("Leaf component cannot contain other components.");
    }

    @Override
    public void remove(TextComponent component) {
        logger.error("Attempted to remove component from TextLeaf (" + type + "): Operation not supported.");
        throw new UnsupportedOperationException("Leaf component cannot contain other components.");
    }

    @Override
    public List<TextComponent> getComponents() {
        return Collections.emptyList();
    }

    @Override
    public TextComponentType getType() {
        return type;
    }

    public String toString() {
        return content;
    }
}