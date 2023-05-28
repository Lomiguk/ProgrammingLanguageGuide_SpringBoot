package com.vsu.skibin.coursework.app.api.data.request.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoublePasswordRequest {
    @NotNull
    @Size(min = 5)
    String oldPassword;
    @NotNull
    @Size(min = 5)
    String newPassword;
}
