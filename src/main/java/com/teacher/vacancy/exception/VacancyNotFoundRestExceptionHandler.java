package com.teacher.vacancy.exception;

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
public class VacancyNotFoundRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(VacancyNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> vacancyNotFoundException(VacancyNotFoundException vacancyNotFoundException,
                                                                         WebRequest webRequest){

        ValidateErrorMessage vem=new ValidateErrorMessage(NOT_FOUND,
                vacancyNotFoundException.getMessage(),
                webRequest.getDescription(false),
                new Date());

        return new ResponseEntity<>(vem, BAD_REQUEST);

    }
}
