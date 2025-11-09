package com.filippovich.compositetask.exeption;

public class TextOperationException extends Exception {
    public TextOperationException() {
        super();
    }
    public TextOperationException(String message) {
        super(message);
    }

    public TextOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextOperationException(Throwable cause) {
        super(cause);
    }
}