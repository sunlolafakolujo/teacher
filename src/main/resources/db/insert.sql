
INSERT INTO contact(street_number, street_name, city, post_zip_code, state_province, country)
VALUES ('1', 'Osanyin Street Alagomeji, Yaba', 'Lagos Island', '100111', 'Lagos', 'Nigeria'),
       ('202', 'Amuwo Odofin Housing Estate, Mile 2','Amuwo Odofin', '100111', 'Lagos', 'Nigeria');

INSERT INTO app_User(user_id,user_type,title, first_name,last_name,gender,date_of_birth, age, school_name,username,password,
                    email, phone, pic_url, is_enabled,profession,employer,employer_address,web_site, means_of_identification,means_of_identification_ref_number,
                    means_of_identification_issue_date, means_of_identification_expiry_date, rc_number)
VALUES('SCH-xxxx1','SCHOOL','NOT_APPLICABLE','','','NOT_APPLICABLE',null,null,'Mind Builders School','mindbuild',
        '$2a$12$M/RlIBtWPXniWg/SAsEdBuh/EXlUHvcda2rALWn5KtHvTFHElW85y','mbs@gmail.com','09087537751','xxxxx',TRUE,'','',
        '','www.xxxxx.com','NIN','145567','2019-04-21','2023-04-20','xc-1234'),
        ('PAR-xxx1','PARENT','MRS','Kemi','Adebiyi','FEMALE',null,null,'','kemi_ade',
        '$2a$12$M/RlIBtWPXniWg/SAsEdBuh/EXlUHvcda2rALWn5KtHvTFHElW85y','kemiadebiyi@yahoo.com','08097643551','xcghkop',TRUE,
        'Accountant','Price Water House','KM100 Ikorodu Road','','PASSPORT','A0567881','2019-04-21','2023-04-20',''),
        ('TCH-xxx1','TEACHER','MRS','Adeola','Adeniyi','FEMALE','1988-02-10',34,'','adeOla_ade',
        '$2a$12$M/RlIBtWPXniWg/SAsEdBuh/EXlUHvcda2rALWn5KtHvTFHElW85y','aa@yahoo.com','08097643451','xcghkop',TRUE,'',
        '','','','PASSPORT','B0567892','2019-04-21','2023-04-20','');

INSERT INTO user_role(role_name)
VALUES ('TEACHER'),('SCHOOL'),('PARENT'),('ADMIN');

INSERT INTO qualification(subject,degree_title ,class_of_degree, institution_name,current_qualification, start_date,end_date,institution_address)
VALUES ('Yoruba','Bachelor of Art' ,'Pass', 'Lagos State University, Ojo','NO','2015-09-01','2020-07-31', '234 Lagos-Badagry Expressway'),
       ('Economics', 'Bechelor of Science','2nd Class Upper','University of Lagos','NO','2015-09-01','2020-07-31','1 Akoka Road');

INSERT INTO work_experience(company,position,current_work,start_date,end_date)
VALUES ('Babington Macaulay Junior Seminary','Teacher','YES','2015-09-01',null),
       ('Dansol High School', 'Head Teacher','YES','2015-09-01',null);

INSERT INTO referee(title,first_name,last_name,email,phone,reference_letter_url)
VALUES ('MR','Toye','Okedara','tokedara@yahoo,com','09087654223','https://resources.workable.com/wp-content/uploads/2016/02/screencapture-resources-workable-employee-reference-letter-2021-04-01-15_38_11.png'),
       ('MR','Tokunbo', 'Akeju','ta@gmail.com','07086545617','https://resumegenius.com/letter-of-recommendation');

INSERT INTO vacancy(published_date, closing_date, job_title, job_location, about_us, job_type, job_schedule,
                    key_responsibility, skill_requirement, qualification, benefit, job_detail,message_to_applicant,app_user_id)
VALUES ('2022-10-10','2022-10-17','Physic Teacher','Lagos','gwhsqol','FULL_TIME','Monday-Friday, 7:00AM-4:00PM',
        'xxxxxx','yyyyyyyy','hhhhhhhhhhh','uuuuuuuuu','qujgdqwsip','We received you resume and our resource department will contact you.',1),
       ('2022-10-10','2022-10-17','Chemistry Teacher','Lagos','hggghhhj','FULL_TIME',
       'Monday-Friday, 7:00AM-4:00PM','xxxxxxkkkk','yyyyyyyyaaaaaa','bbbbhhhhhhhhhhh','ccccccuuuuuuuuu','AVWSVWOUWOQGOIW',
       'We received you resume and our resource department will contact you.',2);

INSERT INTO application_form(first_name,last_name,phone,email,location,resume_Url,cover_letter_url,vacancy_id)
VALUES ('Lowo','Efunkoya','09087545664','lowoefun@yahoo.com','Lagos, Nigeria','https://www.coolfreecv.com/',
        'https://venngage.com/blog/cover-letter-template/',1),
        ('Laolu','Akins','09187545664','lakins@yahoo.com','Lagos, Nigeria','https://www.coolfreecv.com/',
        'https://venngage.com/blog/cover-letter-template/',2);