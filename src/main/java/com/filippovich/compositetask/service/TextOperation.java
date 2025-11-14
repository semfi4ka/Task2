package com.filippovich.compositetask.service;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.exeption.TextCompositeException;

public interface TextOperation {
    String execute(TextComponent textComponent) throws TextCompositeException;
}