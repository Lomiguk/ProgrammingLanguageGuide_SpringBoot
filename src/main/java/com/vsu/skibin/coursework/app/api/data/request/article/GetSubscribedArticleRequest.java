package com.vsu.skibin.coursework.app.api.data.request.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GetSubscribedArticleRequest {
    @NotNull(message = "subscriberId can't be null")
    @Positive(message = "subscriberId can't negative")
    private Long subscriberId;
}
