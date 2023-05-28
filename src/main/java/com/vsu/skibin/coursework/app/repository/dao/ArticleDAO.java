package com.vsu.skibin.coursework.app.repository.dao;

import com.vsu.skibin.coursework.app.entity.Article;
import com.vsu.skibin.coursework.app.repository.rowMapper.ArticleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Component
@Repository
public class ArticleDAO {
    private final String GET_ARTICLE_QUERY = "SELECT * FROM article WHERE id = ?;";
    private final String ADD_ARTICLE_QUERY = "INSERT INTO article (author_id, title, post_date, content, read_count) VALUES (?, ?, ?, ?, 0);";
    private final String UPDATE_ARTICLE_QUERY = "UPDATE article SET title = ?, content = ? WHERE id = ?;";
    private final String DELETE_ARTICLE_QUERY = "DELETE FROM article WHERE id = ?;";
    private final String INC_COUNT_QUERY = "UPDATE article SET read_count = read_count + 1 WHERE id = ?;";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Article getArticle(Long id) {
        return jdbcTemplate.query(GET_ARTICLE_QUERY, new ArticleRowMapper(), id).stream().findAny().orElse(null);
    }

    public int addArticle(Long authorId, String title, Timestamp date, String content) {
        return jdbcTemplate.update(ADD_ARTICLE_QUERY, authorId, title, date, content);
    }

    public int updateArticle(Long id, String tittle, String content) {
        return jdbcTemplate.update(UPDATE_ARTICLE_QUERY, tittle, content, id);
    }
    public int deleteArticle(Long id) {
        return jdbcTemplate.update(DELETE_ARTICLE_QUERY, id);
    }
    public int incReadCount(Long id) {
        return jdbcTemplate.update(INC_COUNT_QUERY, id);
    }
}
