package com.vsu.skibin.coursework.app.exception.exceptionHandler.tag;

import com.vsu.skibin.coursework.app.exception.exception.tag.FindTheTagException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class TagExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FindTheTagException.class)
    public final ResponseEntity<Object> handlerFindTheTagException(FindTheTagException e) {
        logger.warn("Couldn't found a tag");
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
