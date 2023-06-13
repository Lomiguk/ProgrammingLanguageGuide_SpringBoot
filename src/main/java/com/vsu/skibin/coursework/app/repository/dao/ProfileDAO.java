package com.vsu.skibin.coursework.app.repository.dao;

import com.vsu.skibin.coursework.app.api.data.request.profile.UpdateProfileRequest;
import com.vsu.skibin.coursework.app.entity.Profile;
import com.vsu.skibin.coursework.app.repository.rowMapper.ProfileRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Component
@Repository
public class ProfileDAO {
    private final String SIGN_IN_QUERY = "SELECT * FROM profile WHERE login LIKE ? AND password = ?;";
    private final String SIGN_UP_QUERY = "INSERT INTO profile (email, login, is_author, password) VALUES (?, ?, false, ?);";
    private final String IS_LOGIN_EXIST_QUERY = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END AS is_exist FROM profile WHERE login LIKE ?;";
    private final String GET_SUBSCRIBES_QUERY = "SELECT P.login, P.email, P.is_author FROM subscribe AS S JOIN profile AS P ON S.author_id = P.id WHERE S.subscriber_id = ?;";
    private final String GET_PROFILE_QUERY = "SELECT * FROM profile WHERE id = ?;";
    private final String CHECK_PASSWORD_QUERY = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END AS is_right FROM profile WHERE id = ? AND password = ?;";
    private final String CHANGE_PASSWORD_QUERY = "UPDATE profile SET password = ? WHERE id = ?";
    private final String CHANGE_AUTHOR_RIGHTS_QUERY = "UPDATE profile SET is_author = NOT is_author WHERE id = ?;";
    private final String GET_AUTHOR_STATUS_QUERY = "SELECT is_author FROM profile WHERE id = ?;";
    private final String GET_PROFILE_ID = "SELECT id FROM profile WHERE login LIKE ?;";
    private final String SUBSCRIBE_QUERY = "INSERT INTO subscribe VALUES (?, ?, NOW());";
    private final String UNSUBSCRIBE_QUERY = "DELETE FROM subscribe WHERE subscriber_id = ? AND author_id = ?;";
    private final String UPDATE_PROFILE_QUERY = "UPDATE profile SET login=?, email=?, is_author=?, password=? WHERE id=?;";
    private final String GET_PROFILE_BY_NAME_QUERY = "SELECT * FROM profile WHERE login = ? ORDER BY id LIMIT 1;";
    private final String CHECK_SUBSCRIBE_QUERY = "SELECT COUNT(*) > 0 FROM subscribe WHERE subscriber_id = ? AND author_id = ?;";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Profile signIn(String login, long passwordHash) {
        return jdbcTemplate.queryForObject(SIGN_IN_QUERY, new ProfileRowMapper(), login, passwordHash);
    }

    public void signUp(String login, String email, long password) {
        jdbcTemplate.update(SIGN_UP_QUERY, email, login, password);
    }

    public boolean isLoginExist(String login) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(IS_LOGIN_EXIST_QUERY, Boolean.class, login));
    }

    public Collection<Profile> getSubscribes(Long id) {
        return jdbcTemplate.query(GET_SUBSCRIBES_QUERY, new ProfileRowMapper(), id);
    }

    public Profile getProfile(Long profileId) {
        return jdbcTemplate.queryForObject(GET_PROFILE_QUERY, new ProfileRowMapper(), profileId);
    }

    public Profile getProfile(String login) {
        return jdbcTemplate.queryForObject(GET_PROFILE_BY_NAME_QUERY, new ProfileRowMapper(), login);
    }

    public boolean checkPassword(Long profileId, Long oldPassword) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(CHECK_PASSWORD_QUERY, Boolean.class, profileId, oldPassword));
    }

    public void changePassword(Long profileId, Long newPassword) {
        jdbcTemplate.update(CHANGE_PASSWORD_QUERY, newPassword, profileId);
    }

    public void becomeAnAuthor(Long profileId) {
        jdbcTemplate.update(CHANGE_AUTHOR_RIGHTS_QUERY, profileId);
    }

    public boolean isAuthor(Long profileId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(GET_AUTHOR_STATUS_QUERY, Boolean.class, profileId));
    }

    public Long getProfileId(String profileLogin) {
        return jdbcTemplate.queryForObject(GET_PROFILE_ID, Long.class, profileLogin);
    }

    public void subscribe(Long subscriberId, Long authorId) {
        jdbcTemplate.update(SUBSCRIBE_QUERY, subscriberId, authorId);
    }

    public void unsubscribe(Long profileId, Long authorId) {
        jdbcTemplate.update(UNSUBSCRIBE_QUERY, profileId, authorId);
    }

    public void updateProfile(Long profileId, UpdateProfileRequest request) {
        jdbcTemplate.update(UPDATE_PROFILE_QUERY, request.getLogin(),
                request.getEmail(),
                request.getIsAuthor(),
                request.getPassword(),
                profileId);
    }

    public Boolean checkSubscribe(Long subscriberId, Long authorId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(CHECK_SUBSCRIBE_QUERY, Boolean.class, subscriberId, authorId));
    }
}
