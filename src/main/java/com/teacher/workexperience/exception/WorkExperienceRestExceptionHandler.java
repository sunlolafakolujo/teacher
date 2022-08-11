package com.teacher.workexperience.exception;

import com.teacher.message.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class WorkExperienceRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WorkExperienceNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> workExperienceNotFoundException(WorkExperienceNotFoundException workExperienceNotFoundException){

        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND, workExperienceNotFoundException.getMessage());

        return new ResponseEntity<>(vem, HttpStatus.NOT_FOUND);
    }
}
