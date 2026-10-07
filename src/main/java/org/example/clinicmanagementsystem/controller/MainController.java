package org.example.clinicmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.example.clinicmanagementsystem.model.*;
import org.example.clinicmanagementsystem.util.FileManager;

public class MainController {

    @FXML private PatientController patientViewController;
    @FXML private DoctorController doctorViewController;
    @FXML private AppointmentController appointmentViewController;

    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadData();
        passDataToSubControllers();
    }

    private void loadData() {
        patients.setAll(FileManager.loadPatients());
        doctors.setAll(FileManager.loadDoctors());
        appointments.setAll(FileManager.loadAppointments(patients, doctors));
    }

    private void passDataToSubControllers() {
        if (patientViewController != null) {
            patientViewController.setPatients(patients);
        }
        if (doctorViewController != null) {
            doctorViewController.setDoctors(doctors);
        }
        if (appointmentViewController != null) {
            appointmentViewController.setData(appointments, patients, doctors);
        }
    }
}
