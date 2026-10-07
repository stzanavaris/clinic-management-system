package org.example.clinicmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.clinicmanagementsystem.model.Appointment;
import org.example.clinicmanagementsystem.model.Doctor;
import org.example.clinicmanagementsystem.model.Patient;
import org.example.clinicmanagementsystem.util.FileManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AppointmentController {

    @FXML
    private ComboBox<Patient> appPatientBox;

    @FXML
    private ComboBox<Doctor> appDoctorBox;

    @FXML
    private DatePicker appDatePicker;

    @FXML
    private ComboBox<String> appTimeBox;

    @FXML
    private TableView<Appointment> appTable;

    @FXML
    private TableColumn<Appointment, String> appPatientCol;

    @FXML
    private TableColumn<Appointment, String> appDoctorCol;

    @FXML
    private TableColumn<Appointment, String> appTimeCol;

    private ObservableList<Appointment> appointments;
    private ObservableList<Patient> patients;
    private ObservableList<Doctor> doctors;


    @FXML
    public void initialize() {

        setupTable();

        setupTimeBox();

        setupComboBoxDisplay();
    }


    public void setData(
            ObservableList<Appointment> appointments,
            ObservableList<Patient> patients,
            ObservableList<Doctor> doctors
    ) {

        this.appointments = appointments;
        this.patients = patients;
        this.doctors = doctors;

        appTable.setItems(appointments);

        appPatientBox.setItems(patients);

        appDoctorBox.setItems(doctors);
    }


    // =========================
    // COMBO BOX DISPLAY
    // =========================

    private void setupComboBoxDisplay() {

        /*
         * PATIENT COMBO BOX
         */

        appPatientBox.setButtonCell(new ListCell<Patient>() {

            @Override
            protected void updateItem(Patient patient, boolean empty) {

                super.updateItem(patient, empty);

                if (empty || patient == null) {
                    setText("Select Patient");
                } else {
                    setText(patient.getName());
                }
            }
        });


        /*
         * DOCTOR COMBO BOX
         */

        appDoctorBox.setButtonCell(new ListCell<Doctor>() {

            @Override
            protected void updateItem(Doctor doctor, boolean empty) {

                super.updateItem(doctor, empty);

                if (empty || doctor == null) {
                    setText("Select Doctor");
                } else {
                    setText(doctor.getName());
                }
            }
        });


        /*
         * TIME COMBO BOX
         */

        appTimeBox.setButtonCell(new ListCell<String>() {

            @Override
            protected void updateItem(String time, boolean empty) {

                super.updateItem(time, empty);

                if (empty || time == null) {
                    setText("Select Time");
                } else {
                    setText(time);
                }
            }
        });
    }


    // =========================
    // TABLE SETUP
    // =========================

    private void setupTable() {

        appPatientCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getPatient().getName()
                )
        );

        appDoctorCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getDoctor().getName()
                )
        );

        appTimeCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue()
                                .getDateTime()
                                .format(
                                        DateTimeFormatter.ofPattern(
                                                "yyyy-MM-dd HH:mm"
                                        )
                                )
                )
        );
    }


    // =========================
    // TIME OPTIONS
    // =========================

    private void setupTimeBox() {

        ObservableList<String> times =
                FXCollections.observableArrayList();

        for (int hour = 9; hour <= 17; hour++) {

            times.add(String.format("%02d:00", hour));
            times.add(String.format("%02d:15", hour));
            times.add(String.format("%02d:30", hour));
            times.add(String.format("%02d:45", hour));
        }

        appTimeBox.setItems(times);
    }


    // =========================
    // ADD APPOINTMENT
    // =========================

    @FXML
    public void handleAddAppointment() {

        Patient patient = appPatientBox.getValue();

        Doctor doctor = appDoctorBox.getValue();

        LocalDate date = appDatePicker.getValue();

        String timeString = appTimeBox.getValue();


        if (patient == null ||
                doctor == null ||
                date == null ||
                timeString == null) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "All fields are required!"
            );

            return;
        }


        LocalTime time;

        try {

            time = LocalTime.parse(timeString);

        } catch (Exception e) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Invalid appointment time!"
            );

            return;
        }


        LocalDateTime appointmentDateTime =
                LocalDateTime.of(date, time);


        // Prevent appointments in the past
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Date",
                    "Cannot book appointments in the past!"
            );

            return;
        }


        // =========================
        // CONFLICT CHECK
        // =========================

        for (Appointment appointment : appointments) {

            if (appointment
                    .getDateTime()
                    .equals(appointmentDateTime)) {


                // Doctor conflict
                if (appointment
                        .getDoctor()
                        .getId()
                        .equals(doctor.getId())) {

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Doctor Unavailable",
                            "This doctor already has an appointment at this time!"
                    );

                    return;
                }


                // Patient conflict
                if (appointment
                        .getPatient()
                        .getId()
                        .equals(patient.getId())) {

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Patient Unavailable",
                            "This patient already has an appointment at this time!"
                    );

                    return;
                }
            }
        }


        Appointment appointment =
                new Appointment(
                        patient,
                        doctor,
                        appointmentDateTime
                );


        appointments.add(appointment);

        FileManager.saveAppointments(appointments);

        handleClearAppointment();


        showAlert(
                Alert.AlertType.INFORMATION,
                "Success",
                "Appointment booked successfully!"
        );
    }


    // =========================
    // DELETE APPOINTMENT
    // =========================

    @FXML
    public void handleDeleteAppointment() {

        Appointment selectedAppointment =
                appTable.getSelectionModel().getSelectedItem();


        if (selectedAppointment == null) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "No Appointment Selected",
                    "Select an appointment from the table to delete."
            );

            return;
        }


        Alert confirmation =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmation.setTitle("Delete Appointment");

        confirmation.setHeaderText(null);

        confirmation.setContentText(
                "Are you sure you want to delete this appointment?"
        );


        Optional<ButtonType> result =
                confirmation.showAndWait();


        if (result.isPresent() &&
                result.get() == ButtonType.OK) {

            appointments.remove(selectedAppointment);

            FileManager.saveAppointments(appointments);

            handleClearAppointment();


            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Appointment deleted successfully!"
            );
        }
    }


    // =========================
    // CLEAR FORM
    // =========================

    @FXML
    public void handleClearAppointment() {

        appPatientBox.getSelectionModel().clearSelection();

        appDoctorBox.getSelectionModel().clearSelection();

        appTimeBox.getSelectionModel().clearSelection();

        appPatientBox.setValue(null);

        appDoctorBox.setValue(null);

        appTimeBox.setValue(null);

        appDatePicker.setValue(null);

        appTable.getSelectionModel().clearSelection();
    }


    // =========================
    // ALERT
    // =========================

    private void showAlert(
            Alert.AlertType alertType,
            String title,
            String message
    ) {

        Alert alert = new Alert(alertType);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }
}