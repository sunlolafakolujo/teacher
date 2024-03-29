package com.teacher.appuser.exception;

import com.teacher.errormessage.ValidateErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class AppUserRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> appUserExceptionHandler(AppUserNotFoundException appUserException, WebRequest wr ){

        ValidateErrorMessage vem= new ValidateErrorMessage(NOT_FOUND,
                appUserException.getMessage(),
                wr.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, BAD_REQUEST);

    }
}
