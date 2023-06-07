package com.vsu.skibin.coursework.app.api.data.request.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {
    @NotNull(message = "Empty login")
    @Size(min = 5, max = 15, message = "Login size (5 - 15)")
    private String login;
    @NotNull(message = "Empty password")
    @Size(min = 5, message = "Password size biggest then 5")
    private String password;
}
