package com.vsu.skibin.coursework.app.api.data.request.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchArticleRequest {
    @NotNull
    @Size(min = 2, max = 20)
    private String tittle;
    @NotNull
    private String content;
}
