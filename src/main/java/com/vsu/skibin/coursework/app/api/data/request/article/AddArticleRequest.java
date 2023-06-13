package com.vsu.skibin.coursework.app.api.data.request.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddArticleRequest {
    @NotNull(message = "title can't be Null")
    @NotBlank(message = "title can't be blank")
    @Size(min = 2, max = 120, message = "title size (2 - 120)")
    private String title;
    @NotNull(message = "date can't be null")
    private Timestamp date;
    @NotNull(message = "content can't be null")
    private String content;
}
