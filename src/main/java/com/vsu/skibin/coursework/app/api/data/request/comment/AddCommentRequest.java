package com.vsu.skibin.coursework.app.api.data.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddCommentRequest {
    @NotNull(message = "authorId can't be null")
    private Long authorId;
    @NotNull(message = "content can't be null")
    @NotBlank(message = "content can't be blank")
    private String content;
    @NotNull(message = "timestamp can't be null")
    private Timestamp timestamp;
}
