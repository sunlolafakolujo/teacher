package com.teacher.event;

import com.teacher.appuser.model.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Setter
@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private AppUser appUser;

    private String applicationUrl;

    public RegistrationCompleteEvent(AppUser appUser, String applicationUrl) {
        super(appUser);

        this.appUser=appUser;
        this.applicationUrl=applicationUrl;
    }
}
