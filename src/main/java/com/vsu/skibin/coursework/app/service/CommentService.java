package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.CommentDTO;
import com.vsu.skibin.coursework.app.api.data.request.comment.AddCommentRequest;
import com.vsu.skibin.coursework.app.api.data.request.comment.UpdateCommentRequest;
import com.vsu.skibin.coursework.app.entity.Comment;
import com.vsu.skibin.coursework.app.repository.dao.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CommentService {
    private final CommentDAO commentDAO;
    @Autowired
    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }
    public CommentDTO getComment(Long articleId, Long commentId) {
        Comment comment = commentDAO.getComment(articleId, commentId);
        return new CommentDTO(comment);
    }
    public Collection<CommentDTO> getComments(Long articleId, Integer limit, Integer offset) {
        Collection<Comment> comments = commentDAO.getComments(articleId, limit, offset);
        Collection<CommentDTO> resultComments = new ArrayList<>();
        for (Comment comment: comments) {
            resultComments.add(new CommentDTO(comment));
        }
        return resultComments;
    }
    public int addComment(Long articleId, AddCommentRequest request){
        return commentDAO.insertComment(articleId, request);
    }
    public int patchComment(Long articleId, Long commentId, UpdateCommentRequest request) {
        return commentDAO.patchComment(articleId, commentId, request);
    }

    public int deleteComment(Long articleId, Long commentId) {
        return commentDAO.deleteComment(articleId, commentId);
    }


}
