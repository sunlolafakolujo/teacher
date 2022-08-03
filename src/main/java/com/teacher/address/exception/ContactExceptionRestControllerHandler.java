package com.teacher.address.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class ContactExceptionRestControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ContactException.class)
    public ResponseEntity<ValidateErrorMessage> contactExceptionRestController(ContactException contactException){

        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND, contactException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vem);
    }


}
