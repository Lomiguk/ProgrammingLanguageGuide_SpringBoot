package com.vsu.skibin.coursework.app.api.data.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddCommentRequest {
    @NotNull
    private Long authorId;
    @NotNull
    @NotBlank
    private String content;
    @NotNull
    private Timestamp timestamp;
}
