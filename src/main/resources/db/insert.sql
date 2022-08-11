
INSERT INTO contact(street_number, street_name, city, post_zip_code, state_province, country)
VALUES ('1', 'Osanyin Street Alagomeji, Yaba', 'Lagos Island', '100111', 'Lagos', 'Nigeria'),
       ('202', 'Amuwo Odofin Housing Estate, Mile 2','Amuwo Odofin', '100111', 'Lagos', 'Nigeria');

INSERT INTO qualification(subject,degree_title ,class_of_degree, school, start_date,end_date,contact_id)
VALUES ('Yoruba','Bachelor of Art' ,'Pass', 'Lagos State University, Ojo','2015-09-01','2020-07-31', 1),
       ('Economics', 'Bechelor of Science','2nd Class Upper','University of Lagos','2015-09-01','2020-07-31',2);

INSERT INTO work_experience(company,position,status,start_date,end_date)
VALUES ('Babington Macaulay Junior Seminary','Teacher','YES','2015-09-01',null),
       ('Dansol High School', 'Head Teacher','YES','2015-09-01',null);


