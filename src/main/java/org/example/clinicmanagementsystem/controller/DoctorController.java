package org.example.clinicmanagementsystem.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.clinicmanagementsystem.model.Doctor;
import org.example.clinicmanagementsystem.util.FileManager;

public class DoctorController {

    @FXML
    private TextField dNameField, dSpecField;

    @FXML
    private TableView<Doctor> dTable;

    @FXML
    private TableColumn<Doctor, String> dNameCol, dSpecCol;

    private ObservableList<Doctor> doctors;
    private Doctor selectedDoctor;


    @FXML
    public void initialize() {
        setupTable();
    }


    public void setDoctors(ObservableList<Doctor> doctors) {
        this.doctors = doctors;
        dTable.setItems(doctors);
    }


    private void setupTable() {

        dNameCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getName()
                )
        );

        dSpecCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getSpecialty()
                )
        );

        dTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, val) -> selectDoctor(val));
    }


    // =========================
    // ADD DOCTOR
    // =========================

    @FXML
    public void handleAddDoctor() {

        String name = dNameField.getText().trim();
        String specialty = dSpecField.getText().trim();


        // Check name
        if (name.isEmpty()) {
            showAlert("Error", "Name cannot be empty!");
            return;
        }


        // Check specialty
        if (specialty.isEmpty()) {
            showAlert("Error", "Specialty cannot be empty!");
            return;
        }


        // Check duplicate doctor
        if (doctorAlreadyExists(name, specialty, null)) {
            showAlert(
                    "Duplicate Doctor",
                    "A doctor with the same name and specialty already exists!"
            );
            return;
        }


        Doctor doctor = new Doctor(
                java.util.UUID.randomUUID().toString(),
                name,
                specialty
        );


        doctors.add(doctor);

        FileManager.saveDoctors(doctors);

        handleClearDoctor();

        showAlert(
                "Success",
                "Doctor added successfully!"
        );
    }


    // =========================
    // UPDATE DOCTOR
    // =========================

    @FXML
    public void handleUpdateDoctor() {

        if (selectedDoctor == null) {
            showAlert(
                    "Info",
                    "Select a doctor from the table to update."
            );
            return;
        }


        String name = dNameField.getText().trim();
        String specialty = dSpecField.getText().trim();


        // Check name
        if (name.isEmpty()) {
            showAlert("Error", "Name cannot be empty!");
            return;
        }


        // Check specialty
        if (specialty.isEmpty()) {
            showAlert("Error", "Specialty cannot be empty!");
            return;
        }


        // Check duplicates except currently selected doctor
        if (doctorAlreadyExists(name, specialty, selectedDoctor)) {
            showAlert(
                    "Duplicate Doctor",
                    "Another doctor with the same name and specialty already exists!"
            );
            return;
        }


        int index = doctors.indexOf(selectedDoctor);


        Doctor updatedDoctor = new Doctor(
                selectedDoctor.getId(),
                name,
                specialty
        );


        doctors.set(index, updatedDoctor);

        FileManager.saveDoctors(doctors);

        handleClearDoctor();

        showAlert(
                "Success",
                "Doctor updated successfully!"
        );
    }


    // =========================
    // DELETE DOCTOR
    // =========================

    @FXML
    public void handleDeleteDoctor() {

        if (selectedDoctor == null) {
            showAlert(
                    "Info",
                    "Select a doctor from the table to delete."
            );
            return;
        }


        doctors.remove(selectedDoctor);

        FileManager.saveDoctors(doctors);

        handleClearDoctor();

        showAlert(
                "Success",
                "Doctor deleted successfully!"
        );
    }


    // =========================
    // CLEAR
    // =========================

    @FXML
    public void handleClearDoctor() {

        dTable.getSelectionModel().clearSelection();

        clearDoctorFields();
    }


    // =========================
    // SELECT DOCTOR
    // =========================

    private void selectDoctor(Doctor doctor) {

        if (doctor == null) {
            return;
        }


        selectedDoctor = doctor;

        dNameField.setText(doctor.getName());

        dSpecField.setText(doctor.getSpecialty());
    }


    // =========================
    // DUPLICATE CHECK
    // =========================

    private boolean doctorAlreadyExists(
            String name,
            String specialty,
            Doctor doctorToIgnore
    ) {

        for (Doctor doctor : doctors) {

            // Ignore currently selected doctor during update
            if (doctor == doctorToIgnore) {
                continue;
            }


            boolean sameName =
                    doctor.getName()
                            .trim()
                            .equalsIgnoreCase(name);

            boolean sameSpecialty =
                    doctor.getSpecialty()
                            .trim()
                            .equalsIgnoreCase(specialty);


            if (sameName && sameSpecialty) {
                return true;
            }
        }


        return false;
    }


    // =========================
    // CLEAR FIELDS
    // =========================

    private void clearDoctorFields() {

        dNameField.clear();

        dSpecField.clear();

        selectedDoctor = null;
    }


    // =========================
    // ALERT
    // =========================

    private void showAlert(String title, String message) {

        Alert.AlertType alertType;

        if (title.equals("Error") || title.equals("Duplicate Doctor")) {
            alertType = Alert.AlertType.ERROR;
        } else {
            alertType = Alert.AlertType.INFORMATION;
        }


        Alert alert = new Alert(alertType);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }
}