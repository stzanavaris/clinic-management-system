package org.example.clinicmanagementsystem.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.clinicmanagementsystem.model.Appointment;
import org.example.clinicmanagementsystem.model.Doctor;
import org.example.clinicmanagementsystem.model.Patient;
import org.example.clinicmanagementsystem.util.FileManager;

public class DataService {
    private static final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private static final ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList<Patient> getPatients() {
        return patients;
    }

    public static ObservableList<Doctor> getDoctors() {
        return doctors;
    }

    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public static void loadAllData() {
        patients.setAll(FileManager.loadPatients());
        doctors.setAll(FileManager.loadDoctors());
        appointments.setAll(FileManager.loadAppointments(patients, doctors));
    }
}
