package com.teacher.applicationform.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApplicationFormNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus
    @ExceptionHandler
    public ResponseEntity<ValidateErrorMessage> applicationFormExceptionHandler(ApplicationFormNotFoundException anf,
                                                                                WebRequest request){
        ValidateErrorMessage vem=new ValidateErrorMessage(NOT_FOUND,
                anf.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, BAD_REQUEST);

    }
}
