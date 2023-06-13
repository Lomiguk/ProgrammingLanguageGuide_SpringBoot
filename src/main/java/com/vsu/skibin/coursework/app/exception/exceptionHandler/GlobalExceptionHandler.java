package com.vsu.skibin.coursework.app.exception.exceptionHandler;

import com.vsu.skibin.coursework.app.exception.exception.global.WrongIdValueException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidErrors(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(WrongIdValueException.class)
    public final ResponseEntity<Object> handlerWrongIdValueException(WrongIdValueException e) {
        logger.error("Couldn't find the article");
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}