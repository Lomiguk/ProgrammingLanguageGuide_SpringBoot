package com.vsu.skibin.coursework.app.api.data.request.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotNull
    @Size(min = 5, max = 15)
    private String login;
    @Email
    private String email;
    @NotNull
    @Size(min = 5)
    private String password;
    @NotNull
    @Size(min = 5)
    private String passwordRepeat;
}
