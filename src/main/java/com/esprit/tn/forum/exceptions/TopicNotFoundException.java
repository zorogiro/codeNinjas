package com.esprit.tn.forum.exceptions;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String message) {
        super(message);
    }
}
