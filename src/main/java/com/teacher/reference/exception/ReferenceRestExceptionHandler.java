package com.teacher.reference.exception;

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
public class ReferenceRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(ReferenceNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> referenceNotFound(ReferenceNotFoundException referenceNotFoundException, WebRequest wr){

        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                referenceNotFoundException.getMessage(),
                wr.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, HttpStatus.OK);
    }
}
