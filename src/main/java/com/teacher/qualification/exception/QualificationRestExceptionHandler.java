package com.teacher.qualification.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class QualificationRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(QualificationNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> qualificationExceptionHandler(
            QualificationNotFoundException qualificationException, WebRequest wr){

        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                qualificationException.getMessage(),
                wr.getDescription(false),
                new Date());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vem);
    }
}
