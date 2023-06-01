package com.vsu.skibin.coursework.app.exception;

import jakarta.xml.bind.ValidationException;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
public class ExceptionResponse {

    private final int httpStatus;
    private final String message;
    private String stackTrace;
    private List<ValidationException> errors;
    public ExceptionResponse(int status, String message) {
        this.httpStatus = status;
        this.message = message;
    }

    public void addValidation(String field, String defaultMessage) {
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add(new ValidationException(field, defaultMessage));
    }
}
