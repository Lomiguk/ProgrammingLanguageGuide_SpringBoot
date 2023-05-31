package com.vsu.skibin.coursework.app.repository.dao;

import com.vsu.skibin.coursework.app.api.data.request.tag.AddTagRequest;
import com.vsu.skibin.coursework.app.api.data.request.tag.UpdateTagRequest;
import com.vsu.skibin.coursework.app.entity.Tag;
import com.vsu.skibin.coursework.app.repository.rowMapper.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Component
@Repository
public class TagDAO {
    private final String GET_TAG_QUERY = "SELECT * FROM tag WHERE id = ?;";
    private final String INSERT_TAG_QUERY = "INSERT INTO tag (title) VALUES (?);";
    private final String UPDATE_TAG_QUERY = "UPDATE tag SET title = ? WHERE id = ?;";
    private final String DELETE_TAG_QUERY = "DELETE FROM tag WHERE id = ?;";
    private final String ATTACH_TAG_TO_THE_ARTICLE = "INSERT INTO article_tag VALUES (?, ?);";
    private final String GET_ID_BY_TITLE = "SELECT id FROM tag WHERE title = ?;";
    private final String REMOVE_TAG_FROM_THE_ARTICLE = "DELETE FROM article_tag WHERE article_id = ? AND tag_id = ?;";
    private final String GET_TAG_FROM_ARTICLE = "SELECT T.* FROM tag AS T JOIN article_tag AS AT ON T.id = AT.tag_id WHERE AT.article_id = ?";
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public TagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Tag getTagInfo(Long id) {
        return jdbcTemplate.query(GET_TAG_QUERY, new TagRowMapper(), id).stream().findAny().orElse(null);
    }

    public int addTag(AddTagRequest request) {
        return jdbcTemplate.update(INSERT_TAG_QUERY, request.getTitle());
    }

    public int updateTag(Long id, UpdateTagRequest request) {
        return jdbcTemplate.update(UPDATE_TAG_QUERY, request.getTitle(), id);
    }

    public int deleteTag(Long id) {
        return jdbcTemplate.update(DELETE_TAG_QUERY, id);
    }

    public int addTagToTheArticle(Long articleId, Long id) {
        return jdbcTemplate.update(ATTACH_TAG_TO_THE_ARTICLE, articleId, id);
    }

    public Long getIdByTitle(String title) {
        return jdbcTemplate.queryForObject(GET_ID_BY_TITLE, Long.class, title);
    }

    public int removeTagFromTheArticle(Long articleId, Long id) {
        return jdbcTemplate.update(REMOVE_TAG_FROM_THE_ARTICLE, articleId, id);
    }

    public Collection<Tag> getTagFromArticle(Long articleId) {
        return jdbcTemplate.query(GET_TAG_FROM_ARTICLE, new TagRowMapper(),articleId);
    }
}
