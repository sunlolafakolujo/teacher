package com.teacher.validator.password;

import com.teacher.appuser.model.AppUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        AppUser user = (AppUser) obj;
//        return user.getPassword().equals(user.getMatchingPassword());
        return false;
    }
}
