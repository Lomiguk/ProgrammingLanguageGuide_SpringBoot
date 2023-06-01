package com.vsu.skibin.coursework.app.repository.rowMapper;

import com.vsu.skibin.coursework.app.entity.Profile;
import com.vsu.skibin.coursework.app.exception.exception.rowMapper.RowMapException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {
    @Override
    public Profile mapRow(ResultSet rs, int rowNum) {
        try {
            return Profile.builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .login(rs.getString("login"))
                    .isAuthor(rs.getBoolean("is_author"))
                    .password(rs.getLong("password"))
                    .build();
        } catch (SQLException e) {
            throw new RowMapException("ProfileDTO map exception");
        }
    }
}
