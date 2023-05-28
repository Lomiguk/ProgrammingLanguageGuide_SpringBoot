package com.vsu.skibin.coursework.app.exception.article;

public class UnknownArticleException extends RuntimeException {
    public UnknownArticleException(String message) {
        super(message);
    }
}
