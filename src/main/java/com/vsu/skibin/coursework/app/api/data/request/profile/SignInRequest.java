package com.vsu.skibin.coursework.app.api.data.request.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {
    @NotNull
    @Size(min = 5, max = 15)
    private String login;
    @NotNull
    @Size(min = 5)
    private String password;
}
