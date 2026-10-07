package org.example.clinicmanagementsystem.model;

public class Patient {

    private String id;
    private String name;
    private int age;
    private String phone;

    public Patient(String id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}