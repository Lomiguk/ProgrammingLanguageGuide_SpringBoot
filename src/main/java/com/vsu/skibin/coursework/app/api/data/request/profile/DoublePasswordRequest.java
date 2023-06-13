package com.vsu.skibin.coursework.app.api.data.request.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoublePasswordRequest {
    @NotNull(message = "password can't be null")
    @Size(min = 5, message = "min password size - 5")
    String oldPassword;
    @NotNull(message = "password can't be null")
    @Size(min = 5, message = "min password size - 5")
    String newPassword;
}
