/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.util;

public class ClaimException extends RuntimeException {

    public ClaimException() {
    }

    public ClaimException(String message) {
        super(message);
    }

    public ClaimException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClaimException(Throwable cause) {
        super(cause);
    }

    public ClaimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
