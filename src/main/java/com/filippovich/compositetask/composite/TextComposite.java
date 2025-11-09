package com.filippovich.compositetask.composite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextComponent {
    private static final Logger logger = LogManager.getLogger();
    private final List<TextComponent> components = new ArrayList<>();
    private final TextComponentType type;

    public TextComposite(TextComponentType type) {
        this.type = type;
        logger.debug("TextComposite created with type: " + type);
    }

    @Override
    public void add(TextComponent component) {
        components.add(component);
        logger.trace("Added component of type " + component.getType() + " to " + type);
    }

    @Override
    public void remove(TextComponent component) {
        components.remove(component);
        logger.trace("Removed component of type " + component.getType() + " from " + type);
    }

    @Override
    public List<TextComponent> getComponents() {
        return components;
    }

    @Override
    public TextComponentType getType() {
        return type;
    }

    @Override
    public String compose() {
        logger.debug("Composing component of type: " + type);
        StringBuilder sb = new StringBuilder();

        String delimiter;

        switch (type) {
            case PARAGRAPH:
                if (!components.isEmpty() && components.get(0).getType() == TextComponentType.PARAGRAPH) {
                    delimiter = "\n\n";
                } else {
                    delimiter = " ";
                }
                break;
            case SENTENCE:
                delimiter = " ";
                break;
            case LEXEME:
                delimiter = "";
                break;
            default:
                delimiter = "";
                break;
        }

        for (TextComponent component : components) {
            sb.append(component.compose());
            sb.append(delimiter);
        }

        if (sb.length() > 0 && delimiter.length() > 0) {
            sb.setLength(sb.length() - delimiter.length());
        }

        logger.trace("Finished composing " + type);
        return sb.toString();
    }
}