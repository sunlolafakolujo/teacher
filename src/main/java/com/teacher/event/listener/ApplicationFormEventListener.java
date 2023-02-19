package com.teacher.event.listener;

import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.repository.ApplicationFormRepository;
import com.teacher.appteacher.model.Teacher;
import com.teacher.appteacher.repository.TeacherRepository;
import com.teacher.appuser.model.AppUser;
import com.teacher.emailconfig.EmailConfiguration;
import com.teacher.event.ApplicationFormEvent;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import com.teacher.vacancy.service.VacancyService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ApplicationFormEventListener implements ApplicationListener<ApplicationFormEvent> {
    @Autowired
    private EmailConfiguration emailConfiguration;
    @Autowired
    private ApplicationFormRepository applicationFormRepository;
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private TeacherRepository teacherRepository;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationFormEvent event) {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPassword(emailConfiguration.getPassword());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setHost(emailConfiguration.getHost());
        AppUser appUser= event.getAppUser();
        Vacancy vacancy=vacancyService.findVacancyByJobCode(appUser.getVacancy().getJobCode());
        SimpleMailMessage message=emailNotificationToApplicant(appUser,vacancy);
        mailSender.send(message);
    }

    private SimpleMailMessage emailNotificationToApplicant(AppUser appUser,Vacancy vacancy) throws VacancyNotFoundException {
        Vacancy v=vacancyService.findVacancyByJobCode(vacancy.getJobCode());
        Teacher teacher=teacherRepository.findTeacherByEmailOrUsernameOrPhoneOrUserId(appUser);
        ApplicationForm applicationForm=applicationFormRepository.findByTeacher(teacher);
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        String subject=v.getJobTitle();
        String to=applicationForm.getTeacher().getAppUser().getEmail();
        String message=vacancy.getMessageToApplicant();
        String from=v.getAppUser().getEmail();
        String text="Dear "+teacher.getFirstName()+" "+teacher.getLastName()+ ",\n\n"+message;

        mailMessage.setSubject(subject);
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setText(text);
        return mailMessage;
    }
}
