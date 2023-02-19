package com.teacher.event;

import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.appuser.model.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ApplicationFormEvent extends ApplicationEvent {
    private AppUser appUser;

    public ApplicationFormEvent(AppUser appUser) {
        super(appUser);
        this.appUser=appUser;
    }
}
