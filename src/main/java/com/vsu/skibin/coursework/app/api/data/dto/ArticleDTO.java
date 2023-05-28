package com.vsu.skibin.coursework.app.api.data.dto;

import com.vsu.skibin.coursework.app.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private String title;
    private Long authorId;
    private Timestamp postDate;
    private String content;
    private Integer readCount;

    public ArticleDTO(Article article) {
        this.title = article.getTitle();
        this.authorId = article.getAuthorId();
        this.postDate = article.getPostDate();
        this.content = article.getContent();
        this.readCount = article.getReadCount();
    }
}
