package org.example.clinicmanagementsystem.util;

import org.example.clinicmanagementsystem.model.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    @Test
    public void testSaveAndLoadPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("1", "John Doe", 30, "1234567890"));
        
        FileManager.savePatients(patients);
        List<Patient> loaded = FileManager.loadPatients();
        
        assertEquals(1, loaded.size());
        assertEquals("John Doe", loaded.get(0).getName());
        
        new File("patients.txt").delete();
    }

    @Test
    public void testSaveAndLoadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("1", "Dr. Smith", "Cardiology"));
        
        FileManager.saveDoctors(doctors);
        List<Doctor> loaded = FileManager.loadDoctors();
        
        assertEquals(1, loaded.size());
        assertEquals("Dr. Smith", loaded.get(0).getName());
        assertEquals("Cardiology", loaded.get(0).getSpecialty());
        
        new File("doctors.txt").delete();
    }

    @Test
    public void testSaveAndLoadAppointments() {
        List<Patient> patients = new ArrayList<>();
        Patient p = new Patient("p1", "John", 30, "123");
        patients.add(p);
        
        List<Doctor> doctors = new ArrayList<>();
        Doctor d = new Doctor("d1", "Smith", "Surgeon");
        doctors.add(d);
        
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(p, d, LocalDate.of(2023, 10, 1)));
        
        FileManager.saveAppointments(appointments);
        List<Appointment> loaded = FileManager.loadAppointments(patients, doctors);
        
        assertEquals(1, loaded.size());
        assertEquals("p1", loaded.get(0).getPatient().getId());
        assertEquals("d1", loaded.get(0).getDoctor().getId());
        assertEquals(LocalDate.of(2023, 10, 1), loaded.get(0).getDate());
        
        new File("patients.txt").delete();
        new File("doctors.txt").delete();
        new File("appointments.txt").delete();
    }
}