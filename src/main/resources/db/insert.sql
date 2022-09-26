
INSERT INTO contact(street_number, street_name, city, post_zip_code, state_province, country)
VALUES ('1', 'Osanyin Street Alagomeji, Yaba', 'Lagos Island', '100111', 'Lagos', 'Nigeria'),
       ('202', 'Amuwo Odofin Housing Estate, Mile 2','Amuwo Odofin', '100111', 'Lagos', 'Nigeria');

INSERT INTO qualification(subject,degree_title ,class_of_degree, school_name,current_qualification, start_date,end_date,institution_address)
VALUES ('Yoruba','Bachelor of Art' ,'Pass', 'Lagos State University, Ojo','NO','2015-09-01','2020-07-31', '234 Lagos-Badagry Expressway'),
       ('Economics', 'Bechelor of Science','2nd Class Upper','University of Lagos','NO','2015-09-01','2020-07-31','1 Akoka Road');

INSERT INTO work_experience(company,position,current_work,start_date,end_date)
VALUES ('Babington Macaulay Junior Seminary','Teacher','YES','2015-09-01',null),
       ('Dansol High School', 'Head Teacher','YES','2015-09-01',null);

INSERT INTO referee(first_name,last_name,email,phone,reference_letter)
VALUES ('Toye','Okedara','tokedara@yahoo,com','09087654223','https://resources.workable.com/wp-content/uploads/2016/02/screencapture-resources-workable-employee-reference-letter-2021-04-01-15_38_11.png'),
       ('Tokunbo', 'Akeju','ta@gmail.com','07086545617','https://resumegenius.com/letter-of-recommendation');

INSERT INTO user_role(role_name)
VALUES ('TEACHER'),('SCHOOL'),('PARENT'),('ADMIN');

