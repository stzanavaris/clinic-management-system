package org.example.clinicmanagementsystem.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.clinicmanagementsystem.model.Patient;
import org.example.clinicmanagementsystem.util.FileManager;

public class PatientController {

    @FXML
    private TextField pNameField, pAgeField, pPhoneField;

    @FXML
    private TableView<Patient> pTable;

    @FXML
    private TableColumn<Patient, String> pNameCol, pPhoneCol;

    @FXML
    private TableColumn<Patient, Integer> pAgeCol;

    private ObservableList<Patient> patients;
    private Patient selectedPatient;


    @FXML
    public void initialize() {
        setupTable();
    }


    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
        pTable.setItems(patients);
    }


    private void setupTable() {

        pNameCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getName()
                )
        );

        pAgeCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        d.getValue().getAge()
                )
        );

        pPhoneCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getPhone()
                )
        );

        pTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, val) -> selectPatient(val));
    }


    // =========================
    // ADD PATIENT
    // =========================

    @FXML
    public void handleAddPatient() {

        String name = pNameField.getText().trim();
        String ageText = pAgeField.getText().trim();
        String phone = pPhoneField.getText().trim();


        // Check name
        if (name.isEmpty()) {
            showAlert("Error", "Name cannot be empty!");
            return;
        }


        // Check age field
        if (ageText.isEmpty()) {
            showAlert("Error", "Age cannot be empty!");
            return;
        }


        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Age must be a valid number!");
            return;
        }


        // Check age range
        if (age < 1 || age > 120) {
            showAlert("Error", "Age must be between 1 and 120!");
            return;
        }


        // Check phone
        if (phone.isEmpty()) {
            showAlert("Error", "Phone number cannot be empty!");
            return;
        }


        // Check for duplicate patient
        if (patientAlreadyExists(name, age, phone, null)) {
            showAlert(
                    "Duplicate Patient",
                    "A patient with the same name, age and phone number already exists!"
            );
            return;
        }


        Patient patient = new Patient(
                java.util.UUID.randomUUID().toString(),
                name,
                age,
                phone
        );


        patients.add(patient);

        FileManager.savePatients(patients);

        handleClearPatient();

        showAlert(
                "Success",
                "Patient added successfully!"
        );
    }


    // =========================
    // UPDATE PATIENT
    // =========================

    @FXML
    public void handleUpdatePatient() {

        if (selectedPatient == null) {
            showAlert(
                    "Info",
                    "Select a patient from the table to update."
            );
            return;
        }


        String name = pNameField.getText().trim();
        String ageText = pAgeField.getText().trim();
        String phone = pPhoneField.getText().trim();


        // Check name
        if (name.isEmpty()) {
            showAlert("Error", "Name cannot be empty!");
            return;
        }


        // Check age
        if (ageText.isEmpty()) {
            showAlert("Error", "Age cannot be empty!");
            return;
        }


        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Age must be a valid number!");
            return;
        }


        // Check age range
        if (age < 1 || age > 120) {
            showAlert("Error", "Age must be between 1 and 120!");
            return;
        }


        // Check phone
        if (phone.isEmpty()) {
            showAlert("Error", "Phone number cannot be empty!");
            return;
        }


        // Check duplicates except the patient being edited
        if (patientAlreadyExists(name, age, phone, selectedPatient)) {
            showAlert(
                    "Duplicate Patient",
                    "Another patient with the same name, age and phone number already exists!"
            );
            return;
        }


        int index = patients.indexOf(selectedPatient);


        Patient updatedPatient = new Patient(
                selectedPatient.getId(),
                name,
                age,
                phone
        );


        patients.set(index, updatedPatient);

        FileManager.savePatients(patients);

        handleClearPatient();

        showAlert(
                "Success",
                "Patient updated successfully!"
        );
    }


    // =========================
    // DELETE PATIENT
    // =========================

    @FXML
    public void handleDeletePatient() {

        if (selectedPatient == null) {
            showAlert(
                    "Info",
                    "Select a patient from the table to delete."
            );
            return;
        }


        patients.remove(selectedPatient);

        FileManager.savePatients(patients);

        handleClearPatient();

        showAlert(
                "Success",
                "Patient deleted successfully!"
        );
    }


    // =========================
    // CLEAR
    // =========================

    @FXML
    public void handleClearPatient() {

        pTable.getSelectionModel().clearSelection();

        clearPatientFields();
    }


    // =========================
    // SELECT PATIENT
    // =========================

    private void selectPatient(Patient patient) {

        if (patient == null) {
            return;
        }


        selectedPatient = patient;

        pNameField.setText(patient.getName());

        pAgeField.setText(
                String.valueOf(patient.getAge())
        );

        pPhoneField.setText(patient.getPhone());
    }


    // =========================
    // DUPLICATE CHECK
    // =========================

    private boolean patientAlreadyExists(
            String name,
            int age,
            String phone,
            Patient patientToIgnore
    ) {

        for (Patient patient : patients) {

            // Ignore the currently selected patient during update
            if (patient == patientToIgnore) {
                continue;
            }


            boolean sameName =
                    patient.getName().trim().equalsIgnoreCase(name);

            boolean sameAge =
                    patient.getAge() == age;

            boolean samePhone =
                    patient.getPhone().trim().equalsIgnoreCase(phone);


            if (sameName && sameAge && samePhone) {
                return true;
            }
        }


        return false;
    }


    // =========================
    // CLEAR FIELDS
    // =========================

    private void clearPatientFields() {

        pNameField.clear();

        pAgeField.clear();

        pPhoneField.clear();

        selectedPatient = null;
    }


    // =========================
    // ALERT
    // =========================

    private void showAlert(String title, String message) {

        Alert.AlertType alertType;

        if (title.equals("Error") || title.equals("Duplicate Patient")) {
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