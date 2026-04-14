INSERT INTO USERS (CEDUSER, PASSUSER, NAMEUSER, LASTNAMEUSER, ROLEUSER, QUESTUSER, ANSWERUSER)
VALUES 
(101010, '123', 'Carlos', 'Mendoza', 'Professional', 'Mascota?', 'Firulais'),
(1032456789, '456', 'Ana', 'García', 'Professional', 'Ciudad?', 'Bogota'),
(1088123456, '123', 'Andrea', 'Díaz', 'Professional', 'Color?', 'Morado'),
(121212, '123', 'Luis', 'Pérez', 'Patient', 'Color?', 'Azul');
(131313, '123', 'Libardo', 'Pantoja', 'Admin', 'Color?', 'Azul');


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
(1, 1, '2026-03-30', '08:00', 'Created'),
(2, 2, '2026-03-30', '10:45', 'Created'),
(3, 3, '2026-03-30', '13:00', 'Created');