package com.vsu.skibin.coursework.app.api.data.request.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddArticleRequest {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 20)
    private String title;
    @NotNull
    private Timestamp date;
    @NotNull
    private String content;
}
