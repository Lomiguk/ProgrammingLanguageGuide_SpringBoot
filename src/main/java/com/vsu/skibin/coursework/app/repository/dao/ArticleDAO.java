package com.vsu.skibin.coursework.app.repository.dao;

import com.vsu.skibin.coursework.app.entity.Article;
import com.vsu.skibin.coursework.app.repository.rowMapper.ArticleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

@Component
@Repository
public class ArticleDAO {
    private final String GET_ARTICLE_QUERY = "SELECT * FROM article WHERE id = ?;";
    private final String ADD_ARTICLE_QUERY = "INSERT INTO article (author_id, title, post_date, content, read_count) VALUES (?, ?, ?, ?, 0);";
    private final String UPDATE_ARTICLE_QUERY = "UPDATE article SET title = ?, content = ? WHERE id = ?;";
    private final String DELETE_ARTICLE_QUERY = "DELETE FROM article WHERE id = ?;";
    private final String INC_COUNT_QUERY = "UPDATE article SET read_count = read_count + 1 WHERE id = ?;";
    private final String GET_ALL_ARTICLE_WITH_PAGINATION = "SELECT * FROM article ORDER BY post_date LIMIT ? OFFSET ?;";
    private final String GET_SUBSCRIBED_ARTICLE_WITH_PAGINATION = "SELECT * FROM article AS A JOIN subscribe AS S ON A.author_id = S.author_id WHERE S.subscriber_id = ? ORDER BY post_date LIMIT ? OFFSET ?;";
    private final String GET_ARTICLES_BY_TAG_ID = "SELECT A.* FROM article AS A JOIN article_tag AS AT ON A.id = AT.article_id WHERE AT.tag_id = ?;";
    private final String GET_ARTICLE_BY_AUTHOR_TITLE_DATE = "SELECT * FROM article WHERE author_id = ? AND title = ? AND post_date = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Article getArticle(Long id) {
        return jdbcTemplate.query(GET_ARTICLE_QUERY, new ArticleRowMapper(), id).stream().findAny().orElse(null);
    }

    public Article getArticle(Long authorId, String title, Timestamp date) {
        return jdbcTemplate.query(GET_ARTICLE_BY_AUTHOR_TITLE_DATE, new ArticleRowMapper(), authorId, title, date).stream().findAny().orElse(null);
    }

    public void addArticle(Long authorId, String title, Timestamp date, String content) {
        jdbcTemplate.update(ADD_ARTICLE_QUERY, authorId, title, date, content);
    }

    public void updateArticle(Long id, String tittle, String content) {
        jdbcTemplate.update(UPDATE_ARTICLE_QUERY, tittle, content, id);
    }

    public int deleteArticle(Long id) {
        return jdbcTemplate.update(DELETE_ARTICLE_QUERY, id);
    }

    public int incReadCount(Long id) {
        return jdbcTemplate.update(INC_COUNT_QUERY, id);
    }

    public Collection<Article> getAllArticlesWithPagination(Integer limit, Integer offset) {
        return jdbcTemplate.query(GET_ALL_ARTICLE_WITH_PAGINATION, new ArticleRowMapper(), limit, offset);
    }

    public Collection<Article> getSubscriptionsArticlesWithPagination(Long subscriberId, Integer limit, Integer offset) {
        return jdbcTemplate.query(GET_SUBSCRIBED_ARTICLE_WITH_PAGINATION,
                new ArticleRowMapper(),
                subscriberId, limit, offset);
    }

    public Collection<Article> getArticlesByTagId(Long id) {
        return jdbcTemplate.query(GET_ARTICLES_BY_TAG_ID, new ArticleRowMapper(), id);
    }
}
