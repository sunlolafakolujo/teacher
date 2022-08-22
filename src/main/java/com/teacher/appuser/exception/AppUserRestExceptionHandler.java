package com.teacher.appuser.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
public class AppUserRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> appUserExceptionHandler(AppUserNotFoundException appUserException, WebRequest wr ){

        ValidateErrorMessage vem= new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                appUserException.getMessage(),
                wr.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, HttpStatus.OK);

    }
}
