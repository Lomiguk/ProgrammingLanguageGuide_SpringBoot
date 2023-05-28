package com.vsu.skibin.coursework.app.api.data.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCommentRequest {
    @NotNull
    @NotBlank
    private String content;
}
