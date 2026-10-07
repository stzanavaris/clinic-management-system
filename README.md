# Clinic Management System

A JavaFX desktop application for managing patients, doctors, and clinic appointments.

## Overview

The Clinic Management System is a desktop application developed in JavaFX as part of my Computer Science studies.

The application provides a simple interface for managing clinic information and helps prevent common data and scheduling problems.

## Features

- Add, edit, and manage patients
- Add, edit, and manage doctors
- Create and manage appointments
- Prevent duplicate patient and doctor records
- Validate user input
- Detect appointment scheduling conflicts
- Store data using local text files
- JavaFX graphical user interface
- FXML-based views
- Unit testing for utility functionality

## Technologies

- Java
- JavaFX
- FXML
- CSS
- Maven
- JUnit

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── org/example/clinicmanagementsystem/
│   │       ├── controller/
│   │       │   ├── AppointmentController.java
│   │       │   ├── DoctorController.java
│   │       │   ├── MainController.java
│   │       │   └── PatientController.java
│   │       │
│   │       ├── main/
│   │       │   └── MainApp.java
│   │       │
│   │       ├── model/
│   │       │   ├── Appointment.java
│   │       │   ├── Doctor.java
│   │       │   └── Patient.java
│   │       │
│   │       ├── service/
│   │       │   └── DataService.java
│   │       │
│   │       └── util/
│   │           └── FileManager.java
│   │
│   └── resources/
│       └── org/example/clinicmanagementsystem/
│           ├── appointment-view.fxml
│           ├── doctor-view.fxml
│           ├── main.fxml
│           ├── patient-view.fxml
│           └── style.css
│
└── test/
    └── java/
        └── org/example/clinicmanagementsystem/
            └── util/
                └── FileManagerTest.java
```

## Data Storage

The application uses local text files for storing:

- Patients
- Doctors
- Appointments

The files are:

```text
patients.txt
doctors.txt
appointments.txt
```

## Running the Project

### Requirements

- Java JDK
- IntelliJ IDEA
- Maven

### Run with IntelliJ IDEA

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Make sure the project is using a compatible JDK.
4. Load the Maven project.
5. Run `MainApp.java`.

### Clone the Repository

```bash
git clone https://github.com/stzanavaris/clinic-management-system.git
```

## Testing

The project includes JUnit tests for utility functionality.

## What I Learned

Through this project I gained practical experience with:

- Object-Oriented Programming
- JavaFX application development
- FXML
- File-based data storage
- Input validation
- Error handling
- Appointment scheduling logic
- Unit testing
- Maven project management

## Author

**Stavros Tzanavaris**

Computer Science Student
