package com.teacher.appteacher.exception;

import com.teacher.errormessage.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class TeacherNotFoundExceptionRestHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus
    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> teacherNotFoundExceptionHandler(TeacherNotFoundException tnfe,
                                                                                WebRequest request){
        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                tnfe.getMessage(),
                request.getDescription(false),
                new Date());
        return new ResponseEntity<>(vem,HttpStatus.BAD_REQUEST);
    }
}
