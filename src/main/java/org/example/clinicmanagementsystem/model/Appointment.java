package org.example.clinicmanagementsystem.model;

import java.time.LocalDateTime;

public class Appointment {

    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;

    public Appointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
    }

    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public LocalDateTime getDateTime() { return dateTime; }
}