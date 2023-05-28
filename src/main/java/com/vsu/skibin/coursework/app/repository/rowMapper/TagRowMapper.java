package com.vsu.skibin.coursework.app.repository.rowMapper;

import com.vsu.skibin.coursework.app.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Tag(rs.getLong("id"),
                rs.getString("title"));
    }
}
