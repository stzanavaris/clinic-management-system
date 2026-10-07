package org.example.clinicmanagementsystem.util;

import org.example.clinicmanagementsystem.model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManager {

    private static final String PATIENTS_FILE = "patients.txt";
    private static final String DOCTORS_FILE = "doctors.txt";
    private static final String APPOINTMENTS_FILE = "appointments.txt";

    public static void savePatients(List<Patient> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENTS_FILE))) {
            for (Patient p : patients) {
                writer.write(p.getId() + ";" + p.getName() + ";" + p.getAge() + ";" + p.getPhone());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Patient> loadPatients() {
        List<Patient> list = new ArrayList<>();
        File file = new File(PATIENTS_FILE);
        if (!file.exists()) {
            list.add(new Patient(UUID.randomUUID().toString(), "John Doe", 34, "555-0101"));
            list.add(new Patient(UUID.randomUUID().toString(), "Maria Garcia", 28, "555-0102"));
            list.add(new Patient(UUID.randomUUID().toString(), "Robert Smith", 45, "555-0103"));
            savePatients(list);
            return list;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 4) {
                    list.add(new Patient(data[0], data[1], Integer.parseInt(data[2]), data[3]));
                }
            }
        } catch (IOException e) {}
        return list;
    }

    public static void saveDoctors(List<Doctor> doctors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCTORS_FILE))) {
            for (Doctor d : doctors) {
                writer.write(d.getId() + ";" + d.getName() + ";" + d.getSpecialty());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Doctor> loadDoctors() {
        List<Doctor> list = new ArrayList<>();
        File file = new File(DOCTORS_FILE);
        if (!file.exists()) {
            list.add(new Doctor(UUID.randomUUID().toString(), "Dr. Alice Winston", "Cardiology"));
            list.add(new Doctor(UUID.randomUUID().toString(), "Dr. Mark Sloan", "Neurology"));
            list.add(new Doctor(UUID.randomUUID().toString(), "Dr. Gregory House", "Diagnostics"));
            saveDoctors(list);
            return list;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(DOCTORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 3) {
                    list.add(new Doctor(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {}
        return list;
    }

    public static void saveAppointments(List<Appointment> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE))) {
            for (Appointment a : appointments) {
                writer.write(a.getPatient().getId() + ";" + a.getDoctor().getId() + ";" + a.getDateTime().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Appointment> loadAppointments(List<Patient> patients, List<Doctor> doctors) {
        List<Appointment> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 3) {
                    Patient patient = patients.stream().filter(p -> p.getId().equals(data[0])).findFirst().orElse(null);
                    Doctor doctor = doctors.stream().filter(d -> d.getId().equals(data[1])).findFirst().orElse(null);
                    if (patient != null && doctor != null) {
                        list.add(new Appointment(patient, doctor, LocalDateTime.parse(data[2])));
                    }
                }
            }
        } catch (IOException e) {}
        return list;
    }
}