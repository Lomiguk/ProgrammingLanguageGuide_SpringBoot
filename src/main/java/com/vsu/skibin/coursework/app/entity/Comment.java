package com.vsu.skibin.coursework.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long authorId;
    private Long articleId;
    private String content;
    private Timestamp postDate;
}
