package com.teacher.qualification.exception;

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
public class QualificationRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(QualificationNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> qualificationExceptionHandler(
            QualificationNotFoundException qualificationException, WebRequest wr){

        ValidateErrorMessage vem=new ValidateErrorMessage(NOT_FOUND,
                qualificationException.getMessage(),
                wr.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, BAD_REQUEST);
    }
}
