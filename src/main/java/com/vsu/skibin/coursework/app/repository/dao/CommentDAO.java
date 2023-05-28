package com.vsu.skibin.coursework.app.repository.dao;

import com.vsu.skibin.coursework.app.api.data.request.comment.AddCommentRequest;
import com.vsu.skibin.coursework.app.api.data.request.comment.UpdateCommentRequest;
import com.vsu.skibin.coursework.app.entity.Comment;
import com.vsu.skibin.coursework.app.repository.rowMapper.CommentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Component
@Repository
public class CommentDAO {
    private String GET_COMMENT_QUERY = "SELECT * FROM comment WHERE article_id = ? AND id = ?;";
    private String INSERT_COMMENT_QUERY = "INSERT INTO comment (author_id, article_id, content, post_date) VALUES (?, ?, ?, ?)";
    private String PATCH_COMMENT_QUERY = "UPDATE comment SET content = ? WHERE id = ? AND article_id = ?;";
    private String DELETE_COMMENT_QUERY = "DELETE FROM comment WHERE id = ? AND article_id = ?;";
    private String GET_COMMENTS_BY_ARTICLE_QUERY = "SELECT * FROM comment WHERE article_id = ? ORDER BY post_date LIMIT ? OFFSET ?;";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Comment getComment(Long articleId, Long commentId) {
        return jdbcTemplate.query(GET_COMMENT_QUERY, new CommentRowMapper(), articleId, commentId)
                .stream()
                .findAny()
                .orElse(null);
    }

    public int insertComment(Long articleId, AddCommentRequest request) {
        return jdbcTemplate.update(INSERT_COMMENT_QUERY, request.getAuthorId(),
                articleId,
                request.getContent(),
                request.getTimestamp());
    }

    public int patchComment(Long articleId, Long commentId, UpdateCommentRequest request) {
        return jdbcTemplate.update(PATCH_COMMENT_QUERY, request.getContent(), commentId, articleId);
    }

    public int deleteComment(Long articleId, Long commentId) {
        return jdbcTemplate.update(DELETE_COMMENT_QUERY, commentId, articleId);
    }

    public Collection<Comment> getComments(Long articleId, Integer limit, Integer offset) {
        return jdbcTemplate.query(GET_COMMENTS_BY_ARTICLE_QUERY, new CommentRowMapper(), articleId, limit, offset);
    }
}
