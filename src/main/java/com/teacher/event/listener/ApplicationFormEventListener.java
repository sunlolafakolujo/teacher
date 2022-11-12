package com.teacher.event.listener;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.service.ApplicationFormService;
import com.teacher.emailconfig.EmailConfiguration;
import com.teacher.event.ApplicationFormEvent;
import com.teacher.vacancy.model.Vacancy;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class ApplicationFormEventListener implements ApplicationListener<ApplicationFormEvent> {
    @Autowired
    private EmailConfiguration emailConfiguration;
    @Autowired
    private ApplicationFormService applicationFormService;

    @Override
    public void onApplicationEvent(ApplicationFormEvent event) {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPassword(emailConfiguration.getPassword());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setHost(emailConfiguration.getHost());
        ApplicationForm applicationForm= event.getApplicationForm();
//        Vacancy vacancy=applicationForm.getVacancy();
        SimpleMailMessage message=emailNotificationToApplicant(applicationForm);
        mailSender.send(message);
    }

    private SimpleMailMessage emailNotificationToApplicant(ApplicationForm applicationForm) {

        SimpleMailMessage mailMessage=new SimpleMailMessage();

        String subject="Subject Teacher";
//        String subject=vacancy.getJobTitle();
        String to=applicationForm.getEmail();
//        String from=applicationForm.getVacancy().getAppUser().getEmail();
        String from="fakolujos@gmail.com";
//        String message=applicationForm.getMessageToApplicant();
        String message="Dear "+applicationForm.getFirstName()+
                ",\n\nWe received you resume and our resource department will contact you.";

        mailMessage.setSubject(subject);
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setText(message);
        return mailMessage;
    }
}
