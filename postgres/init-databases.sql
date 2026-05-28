-- Script de inicialización de PostgreSQL
-- Crea las tres bases de datos requeridas por los microservicios

\c postgres;

-- auth-service DB
SELECT 'CREATE DATABASE auth_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'auth_db')\gexec
GRANT ALL PRIVILEGES ON DATABASE auth_db TO "piedraAzul";

-- people-service DB
SELECT 'CREATE DATABASE people_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'people_db')\gexec
GRANT ALL PRIVILEGES ON DATABASE people_db TO "piedraAzul";

-- appointment-service DB
SELECT 'CREATE DATABASE appointment_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'appointment_db')\gexec
GRANT ALL PRIVILEGES ON DATABASE appointment_db TO "piedraAzul";
