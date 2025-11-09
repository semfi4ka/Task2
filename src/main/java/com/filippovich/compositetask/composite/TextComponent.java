package com.filippovich.compositetask.composite;

import java.util.List;

public interface TextComponent {

    String compose();
    void add(TextComponent component);
    void remove(TextComponent component);
    List<TextComponent> getComponents();
    TextComponentType getType();

}