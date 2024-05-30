show databases;

create database hospital;
USE hospital;

CREATE TABLE patient(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(2) NOT NULL
);

CREATE TABLE doctor(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    specialization VARCHAR(50)
);

CREATE TABLE appoinments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES patient (id),
    FOREIGN KEY (doctor_id)
        REFERENCES doctor (id)
);

USE hospital;
SELECT * FROM patient;
SELECT * FROM doctor;
SELECT * FROM appoinments;

