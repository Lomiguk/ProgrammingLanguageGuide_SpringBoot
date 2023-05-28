package com.vsu.skibin.coursework.app.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Article {
    private Long id;
    private String title;
    private Long authorId;
    private Timestamp postDate;
    private String content;
    private Integer readCount;
}
