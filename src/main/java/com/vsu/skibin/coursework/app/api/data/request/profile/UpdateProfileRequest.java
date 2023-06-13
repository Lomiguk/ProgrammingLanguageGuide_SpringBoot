package com.vsu.skibin.coursework.app.api.data.request.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @Size(min = 5, max = 15, message = "login size 5 - 15")
    private String login;
    @Email(message = "wrong email")
    private String email;
    private Boolean isAuthor;
    @Size(min = 5, message = "min password size 5")
    private Long password;
}
