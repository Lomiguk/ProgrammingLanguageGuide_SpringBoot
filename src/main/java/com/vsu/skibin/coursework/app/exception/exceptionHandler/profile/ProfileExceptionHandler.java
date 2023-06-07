package com.vsu.skibin.coursework.app.exception.exceptionHandler.profile;

import com.vsu.skibin.coursework.app.exception.exception.profile.ReturnUnknownProfileException;
import com.vsu.skibin.coursework.app.exception.exception.profile.SubscribeOnNonExistentProfile;
import com.vsu.skibin.coursework.app.exception.exception.profile.WrongOldPasswordException;
import com.vsu.skibin.coursework.app.exception.exception.profile.WrongProfileIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProfileExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ReturnUnknownProfileException.class)
    public final ResponseEntity<Object> handleReturnUnknownProfileException(ReturnUnknownProfileException e/*,
                                                                            WebRequest request*/){
        logger.error("Was returned null profileDTO");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(WrongOldPasswordException.class)
    public final ResponseEntity<Object> handlerReturnWrongOldPasswordException(WrongOldPasswordException e){
        logger.error("User wrote wrong old password at the change password operation");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(WrongProfileIdException.class)
    public final ResponseEntity<Object> handlerReturnWrongProfileIdException(WrongProfileIdException e){
        logger.error("Wrong user id");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SubscribeOnNonExistentProfile.class)
    public final ResponseEntity<Object> handlerReturnWrongProfileIdException(SubscribeOnNonExistentProfile e){
        logger.error("Trying to follow a non-existent user");
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
