package com.vsu.skibin.coursework.app.controller.comment;

import com.vsu.skibin.coursework.app.api.data.dto.CommentDTO;
import com.vsu.skibin.coursework.app.api.data.request.comment.AddCommentRequest;
import com.vsu.skibin.coursework.app.api.data.request.comment.UpdateCommentRequest;
import com.vsu.skibin.coursework.app.service.CommentService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/article/{articleId}/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = {"", "/"})
    public Collection<CommentDTO> getComments(@PathVariable("articleId") Long articleId,
                                              @PathParam("limit") Integer limit,
                                              @PathParam("offset") Integer offset) {
        return commentService.getComments(articleId, limit, offset);
    }

    @GetMapping("/{commentId}")
    public CommentDTO getComment(@PathVariable("articleId") Long articleId,
                                 @PathVariable("commentId") Long commentId) {
        return commentService.getComment(articleId, commentId);
    }

    @PostMapping(value = {"", "/"})
    public int addComment(@PathVariable("articleId") Long articleId,
                          @Valid @RequestBody AddCommentRequest request) {
        return commentService.addComment(articleId, request);
    }

    @PatchMapping("/{commentId}")
    public int patchComment(@PathVariable("articleId") Long articleId,
                            @PathVariable("commentId") Long commentId,
                            @Valid @RequestBody UpdateCommentRequest request) {
        return commentService.patchComment(articleId, commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public int deleteComment(@PathVariable("articleId") Long articleId,
                             @PathVariable("commentId") Long commentId) {
        return commentService.deleteComment(articleId, commentId);
    }
}
