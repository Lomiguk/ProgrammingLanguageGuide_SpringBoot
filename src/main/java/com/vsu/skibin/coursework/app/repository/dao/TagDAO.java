package com.vsu.skibin.coursework.app.repository.dao;

import com.vsu.skibin.coursework.app.api.data.request.tag.AddTagRequest;
import com.vsu.skibin.coursework.app.api.data.request.tag.UpdateTagRequest;
import com.vsu.skibin.coursework.app.entity.Tag;
import com.vsu.skibin.coursework.app.repository.rowMapper.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class TagDAO {
    private final String GET_TAG_QUERY = "SELECT * FROM tag WHERE id = ?;";
    private final String INSERT_TAG_QUERY = "INSERT INTO tag (title) VALUES (?);";
    private final String UPDATE_TAG_QUERY = "UPDATE tag SET title = ? WHERE id = ?;";
    private final String DELETE_TAG_QUERY = "DELETE FROM tag WHERE id = ?;";
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
}
