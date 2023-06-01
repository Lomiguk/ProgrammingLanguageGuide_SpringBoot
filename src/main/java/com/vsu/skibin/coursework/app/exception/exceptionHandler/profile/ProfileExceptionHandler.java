package com.vsu.skibin.coursework.app.exception.exceptionHandler.profile;

import com.vsu.skibin.coursework.app.exception.ExceptionResponse;
import com.vsu.skibin.coursework.app.exception.exception.profile.ReturnUnknownProfileException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ProfileExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String TRACE = "trace";
    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;
    @ExceptionHandler(ReturnUnknownProfileException.class)
    public final ResponseEntity<ExceptionResponse> handleReturnUnknownProfileException(ReturnUnknownProfileException exception,
                                                                             WebRequest request){
        logger.error("Was returned null profileDTO");
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e,
                                                                       WebRequest req){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details."
        );

        for (FieldError fieldError:e.getBindingResult().getFieldErrors()){
            exceptionResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(exceptionResponse);
    }*/

    private ResponseEntity<ExceptionResponse>  buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                httpStatus,
                request);
    }

    private ResponseEntity<ExceptionResponse> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        ExceptionResponse errorResponse = new ExceptionResponse(
                httpStatus.value(),
                message
        );

        if(printStackTrace && isTraceOn(request)){
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private boolean isTraceOn(WebRequest request) {
        String [] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }
}
