package com.filippovich.compositetask.exeption;

public class TextCompositeException extends Exception {
    public TextCompositeException() {
        super();
    }
    public TextCompositeException(String message) {
        super(message);
    }

    public TextCompositeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextCompositeException(Throwable cause) {
        super(cause);
    }
}