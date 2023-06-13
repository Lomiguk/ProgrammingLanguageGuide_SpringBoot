package com.vsu.skibin.coursework.app.exception.exceptionHandler.article;

import com.vsu.skibin.coursework.app.exception.exception.article.NotAuthorException;
import com.vsu.skibin.coursework.app.exception.exception.article.UnknownArticleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ArticleExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotAuthorException.class)
    public final ResponseEntity<Object> handlerReturnNotAuthorException(NotAuthorException e) {
        logger.error("The user who tied create an article don't have the status of an author.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UnknownArticleException.class)
    public final ResponseEntity<Object> handlerReturnUnknownArticleException(UnknownArticleException e) {
        logger.error("Trying to interact with non-existing article.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
