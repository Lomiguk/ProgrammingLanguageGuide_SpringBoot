package com.vsu.skibin.coursework.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Profile {
    private Long id;
    private String email;
    private String login;
    private Long password;
    private Boolean isAuthor;
}
