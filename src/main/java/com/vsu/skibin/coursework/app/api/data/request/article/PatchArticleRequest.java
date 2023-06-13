package com.vsu.skibin.coursework.app.api.data.request.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchArticleRequest {
    @NotNull(message = "title can't be null")
    @Size(min = 2, max = 20, message = "title size (2-20)")
    private String tittle;
    @NotNull(message = "content can't be null")
    private String content;
}
