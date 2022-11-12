package com.teacher.event;

import com.teacher.applicationform.model.ApplicationForm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ApplicationFormEvent extends ApplicationEvent {
    private ApplicationForm applicationForm;

    public ApplicationFormEvent(ApplicationForm applicationForm) {
        super(applicationForm);
        this.applicationForm=applicationForm;
    }
}
