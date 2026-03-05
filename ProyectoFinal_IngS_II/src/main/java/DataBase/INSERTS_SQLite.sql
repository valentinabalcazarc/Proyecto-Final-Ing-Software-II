INSERT INTO USERS 
(CEDUSER, PASSUSER, NAMEUSER, SECOND_NAMEUSER, LASTNAMEUSER, SECOND_LASTNAMEUSER, STATUSUSER, ROLEUSER, QUESTUSER, ANSWERUSER)
VALUES
(1058, '$2a$10$3UJdnxvdeIUxQff1r4uBQeKHIjJn2U2g2axUVPp25MD3tRGPUXpba', 'Jesus', 'Eduardo', 'Lasso', 'Muñoz', 'Active', 'Doctor', 'Pet name?', 'Rocky'),


INSERT INTO ADMIN (USERADMIN, PASSADMIN) 
VALUES ('super_admin', 'Hash_Secure_99*');

INSERT INTO USERS (CEDUSER, PASSUSER, NAMEUSER, LASTNAMEUSER, ROLEUSER, QUESTUSER, ANSWERUSER)
VALUES 
(101020, '123', 'Carlos', 'Mendoza', 'Doctor', 'Mascota?', 'Firulais'),
(202030, '456', 'Ana', 'García', 'Scheduler', 'Ciudad?', 'Bogota'),
(303040, '789', 'Luis', 'Pérez', 'Patient', 'Color?', 'Azul');


INSERT INTO PROFESSIONAL (CODUSER, GENDOC, PHONEMED, TYPEDOC, SPECIALDOC, INTVALATTDOC)
VALUES 
(1, 'Male', '555', 'Doctor', 'Neural_therapy', 30),
(1, 'Male', '666', 'Therapist', 'Chiropractor', 45),
(1, 'Male', '777', 'Doctor', 'Physiotherapy', 60);


INSERT INTO PATIENT (IDPATIENT, NAMEPATIENT, LASTNAMEPATIENT, PHONE_PATIENT, DATE_BIRTH_PATIENT, GENDER_PATIENT)
VALUES 
(100, 'Luis', 'Pérez', 300123, '1990-05-15', 'Male'),
(200, 'Marta', 'Rodríguez', 300456, '1985-11-20', 'Female'),
(300, 'Jorge', 'Ruiz', 300789, '2000-01-10', 'Male');


INSERT INTO APPOINTMENT (CODDOC, CODPATIENT, DATEAPP, DESCAPP, STATUSAPP)
VALUES 
(1, 1, '2026-03-10 08:00:00', 'Consulta inicial de terapia neural', 'Scheduled'),
(2, 2, '2026-03-11 10:30:00', 'Ajuste quiropráctico de rutina', 'Scheduled'),
(3, 3, '2026-03-12 14:00:00', 'Sesión de fisioterapia post-operatoria', 'Scheduled');