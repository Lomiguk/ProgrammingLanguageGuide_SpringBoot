package com.vsu.skibin.coursework.app.repository.rowMapper;

import com.vsu.skibin.coursework.app.entity.Article;
import com.vsu.skibin.coursework.app.exception.exception.rowMapper.RowMapException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) {
        try{
            return Article.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .postDate(rs.getTimestamp("post_date"))
                    .content(rs.getString("content"))
                    .readCount(rs.getInt("read_count"))
                    .build();
        } catch (SQLException e) {
            throw new RowMapException("ArticleDTO map exception");
        }
    }
}
