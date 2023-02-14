package com.teacher.verificationtoken.exception;

import com.teacher.errormessage.ValidateErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class VerificationTokenNotFoundRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(VerificationTokeNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> verificationTokenException(VerificationTokeNotFoundException exception, WebRequest request){

        ValidateErrorMessage vem=new ValidateErrorMessage(NOT_FOUND,
                exception.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, BAD_REQUEST);

    }
}
