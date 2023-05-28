package com.vsu.skibin.coursework.app.exception.profile;

public class SubscribeOnNonExistentProfile extends RuntimeException{
    public SubscribeOnNonExistentProfile(String message) {
        super(message);
    }
}
