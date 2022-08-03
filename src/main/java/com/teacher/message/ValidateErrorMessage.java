package com.teacher.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateErrorMessage {

    private HttpStatus httpStatus;

    private String message;
}
