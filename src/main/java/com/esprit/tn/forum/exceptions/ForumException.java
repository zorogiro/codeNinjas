package com.esprit.tn.forum.exceptions;


public class ForumException extends RuntimeException {
    public ForumException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public ForumException(String exMessage) {
        super(exMessage);
    }
}
