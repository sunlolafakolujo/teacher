package com.teacher.userrole.exception;

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
public class UserRoleNotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> userRoleValidationError(UserRoleNotFoundException userRoleNotFoundException,
                                                                        WebRequest request){
        ValidateErrorMessage ven=new ValidateErrorMessage(NOT_FOUND,
                userRoleNotFoundException.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(ven, BAD_REQUEST);
    }
}
