INSERT INTO ADMIN (USERADMIN, PASSADMIN) 
VALUES ('super_admin', 'Hash_Secure_99*');

INSERT INTO USERS (CEDUSER, PASSUSER, NAMEUSER, LASTNAMEUSER, ROLEUSER, QUESTUSER, ANSWERUSER)
VALUES 
(101020, '123', 'Carlos', 'Mendoza', 'Doctor', 'Mascota?', 'Firulais'),
(202030, '456', 'Ana', 'García', 'Scheduler', 'Ciudad?', 'Bogota'),
(303040, '789', 'Luis', 'Pérez', 'Patient', 'Color?', 'Azul');


INSERT INTO PROFESSIONAL (CODUSER, GENPROF, PHONEPROF, TYPEPROF, SPECIALITYPROF, ATTENTIONINTERVAL)
VALUES 
(1, 'Male', '555', 'Doctor', 'Neural_Therapy', 30),
(1, 'Male', '666', 'Therapist', 'Chiropractor', 45),
(1, 'Male', '777', 'Doctor', 'Physiotherapy', 60);


INSERT INTO PATIENT (IDPATIENT, NAMEPATIENT, LASTNAMEPATIENT, PHONE_PATIENT, DATE_BIRTH_PATIENT, GENDER_PATIENT)
VALUES 
(100, 'Luis', 'Pérez', 300123, '1990-05-15', 'Male'),
(200, 'Marta', 'Rodríguez', 300456, '1985-11-20', 'Female'),
(300, 'Jorge', 'Ruiz', 300789, '2000-01-10', 'Male');


INSERT INTO APPOINTMENT (CODPROF, CODPATIENT, DATEAPP, TIMEAPP, STATUSAPP)
VALUES 
(1, 1, '2026-03-10', '08:00', 'Scheduled'),
(2, 2, '2026-03-11', '10:30', 'Scheduled'),
(3, 3, '2026-03-12', '14:00', 'Scheduled');