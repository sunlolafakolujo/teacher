package com.teacher.address.exception;

public class ContactException extends RuntimeException{
    public ContactException() {
        super();
    }

    public ContactException(String message) {
        super(message);
    }

    public ContactException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactException(Throwable cause) {
        super(cause);
    }

    protected ContactException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
