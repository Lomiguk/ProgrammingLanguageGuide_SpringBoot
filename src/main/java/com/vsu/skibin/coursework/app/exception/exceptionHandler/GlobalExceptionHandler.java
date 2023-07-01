package com.vsu.skibin.coursework.app.exception.exceptionHandler;

import com.vsu.skibin.coursework.app.exception.exception.article.NotAuthorException;
import com.vsu.skibin.coursework.app.exception.exception.article.UnknownArticleException;
import com.vsu.skibin.coursework.app.exception.exception.global.WrongIdValueException;
import com.vsu.skibin.coursework.app.exception.exception.profile.ReturnUnknownProfileException;
import com.vsu.skibin.coursework.app.exception.exception.profile.SubscribeOnNonExistentProfile;
import com.vsu.skibin.coursework.app.exception.exception.profile.WrongOldPasswordException;
import com.vsu.skibin.coursework.app.exception.exception.profile.WrongProfileIdException;
import com.vsu.skibin.coursework.app.exception.exception.tag.FindTheTagException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(WrongIdValueException.class)
    public final ResponseEntity<Object> handlerWrongIdValueException(WrongIdValueException e) {
        logger.error("Couldn't find the article");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        logger.warn("Valid Exception");
        return ResponseEntity.badRequest().body(ex.getBody().getTitle());
    }

    // Article
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

    // Profile
    @ExceptionHandler(ReturnUnknownProfileException.class)
    public final ResponseEntity<Object> handleReturnUnknownProfileException(ReturnUnknownProfileException e/*,
                                                                            WebRequest request*/) {
        logger.error("Was returned null profileDTO");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(WrongOldPasswordException.class)
    public final ResponseEntity<Object> handlerReturnWrongOldPasswordException(WrongOldPasswordException e) {
        logger.error("User wrote wrong old password at the change password operation");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(WrongProfileIdException.class)
    public final ResponseEntity<Object> handlerReturnWrongProfileIdException(WrongProfileIdException e) {
        logger.error("Wrong user id");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SubscribeOnNonExistentProfile.class)
    public final ResponseEntity<Object> handlerReturnWrongProfileIdException(SubscribeOnNonExistentProfile e) {
        logger.error("Trying to follow a non-existent user");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // Tag
    @ExceptionHandler(FindTheTagException.class)
    public final ResponseEntity<Object> handlerFindTheTagException(FindTheTagException e) {
        logger.warn("Couldn't found a tag");
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}