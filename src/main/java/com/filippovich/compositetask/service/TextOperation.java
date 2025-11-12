package com.filippovich.compositetask.service;

import com.filippovich.compositetask.composite.TextComponent;
import com.filippovich.compositetask.exeption.TextOperationException;

public interface TextOperation {
    String execute(TextComponent textComponent) throws TextOperationException;
}