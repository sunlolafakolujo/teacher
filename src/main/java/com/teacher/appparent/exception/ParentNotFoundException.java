package com.teacher.appparent.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ParentNotFoundException extends RuntimeException{
    public ParentNotFoundException() {
        super();
    }

    public ParentNotFoundException(String message) {
        super(message);
    }

    public ParentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParentNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ParentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
