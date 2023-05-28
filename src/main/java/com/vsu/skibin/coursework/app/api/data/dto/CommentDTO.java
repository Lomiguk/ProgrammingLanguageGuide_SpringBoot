package com.vsu.skibin.coursework.app.api.data.dto;

import com.vsu.skibin.coursework.app.entity.Comment;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDTO {
    private Long articleId;
    private Long authorId;
    private String content;
    private Timestamp timestamp;

    public CommentDTO(Comment comment) {
        this.articleId = comment.getArticleId();
        this.authorId = comment.getAuthorId();
        this.content = comment.getContent();
        this.timestamp = comment.getPostDate();
    }
}
