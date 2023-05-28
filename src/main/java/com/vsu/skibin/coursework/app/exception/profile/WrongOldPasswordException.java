package com.vsu.skibin.coursework.app.exception.profile;

public class WrongOldPasswordException extends RuntimeException{
    public WrongOldPasswordException(String message) {
        super(message);
    }
}
