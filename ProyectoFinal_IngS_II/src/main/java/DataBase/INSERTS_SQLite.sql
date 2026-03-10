INSERT INTO ADMIN (USERADMIN, PASSADMIN) 
VALUES ('super_admin', 'Hash_Secure_99*');

INSERT INTO USERS (CEDUSER, PASSUSER, NAMEUSER, LASTNAMEUSER, ROLEUSER, QUESTUSER, ANSWERUSER)
VALUES 
(1010202030, '123', 'Carlos', 'Mendoza', 'Doctor', 'Mascota?', 'Firulais'),
(1032456789, '456', 'Ana', 'García', 'Doctor', 'Ciudad?', 'Bogota'),
(1088123456, '123', 'Andrea', 'Díaz', 'Doctor', 'Color?', 'Morado'),
(1075982341, '789', 'Luis', 'Pérez', 'Patient', 'Color?', 'Azul');


INSERT INTO PROFESSIONAL (CODUSER, GENPROF, PHONEPROF, TYPEPROF, SPECIALITYPROF, ATTENTIONINTERVAL)
VALUES 
(1, 'Male', '555', 'Doctor', 'Neural_Therapy', 30),
(2, 'Female', '666', 'Therapist', 'Chiropractor', 45),
(3, 'Female', '777', 'Doctor', 'Physiotherapy', 60);


INSERT INTO PATIENT (IDPATIENT, NAMEPATIENT, LASTNAMEPATIENT, PHONE_PATIENT, DATE_BIRTH_PATIENT, GENDER_PATIENT)
VALUES 
(1075982341, 'Luis', 'Pérez', 300123, '1990-05-15', 'Male'),
(1000456789, 'Marta', 'Rodríguez', 300456, '1985-11-20', 'Female'),
(1077554433, 'Jorge', 'Ruiz', 300789, '2000-01-10', 'Male');


INSERT INTO APPOINTMENT (CODPROF, CODPATIENT, DATEAPP, TIMEAPP, STATUSAPP)
VALUES 
(1, 1, '2026-03-20', '08:00', 'Scheduled'),
(2, 2, '2026-03-21', '10:30', 'Scheduled'),
(3, 3, '2026-03-22', '14:00', 'Scheduled');