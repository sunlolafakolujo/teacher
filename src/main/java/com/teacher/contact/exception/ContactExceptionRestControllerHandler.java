package com.teacher.contact.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ContactExceptionRestControllerHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> contactExceptionRestController(
            WebRequest wr,ContactNotFoundException contactException){

        ValidateErrorMessage vem=new ValidateErrorMessage(NOT_FOUND,
                contactException.getMessage(),
                wr.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, BAD_REQUEST);
    }


}
