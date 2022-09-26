package com.teacher.event;

import com.teacher.appuser.model.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class PasswordEvent extends ApplicationEvent {
    private String applicationUrl;
    private AppUser appUser;
    public PasswordEvent( String applicationUrl,AppUser appUser) {
        super(appUser);

        this.applicationUrl=applicationUrl;
        this.appUser=appUser;
    }
}
