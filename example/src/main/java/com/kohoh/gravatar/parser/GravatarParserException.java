package com.kohoh.gravatar.parser;

/**
 * Created by kohoh on 14-8-17.
 */
public class GravatarParserException extends Exception {

    public GravatarParserException() {
        super();
    }

    public GravatarParserException(String message) {
        super(message);
    }

    public GravatarParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public GravatarParserException(Throwable cause) {
        super(cause);
    }
}
