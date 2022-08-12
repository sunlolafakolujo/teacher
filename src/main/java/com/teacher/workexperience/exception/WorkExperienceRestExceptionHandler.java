package com.teacher.workexperience.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class WorkExperienceRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WorkExperienceNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> workExperienceNotFoundException(
            WorkExperienceNotFoundException workExperienceNotFoundException, WebRequest wr){

        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                workExperienceNotFoundException.getMessage(),
                wr.getDescription(false),
                new Date());

        return ResponseEntity.internalServerError().body(vem);
    }
}
