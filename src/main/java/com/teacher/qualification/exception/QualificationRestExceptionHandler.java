package com.teacher.qualification.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ResponseStatus
@ControllerAdvice
public class QualificationRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QualificationException.class)
    public ResponseEntity<ValidateErrorMessage> qualificationExceptionHandler(QualificationException qualificationException){

        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND, qualificationException.getMessage());

        return new ResponseEntity<>(vem, HttpStatus.NOT_FOUND);
    }
}
