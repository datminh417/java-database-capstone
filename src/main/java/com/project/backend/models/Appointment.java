package com.project.backend.models; // Đảm bảo package này khớp với package của Doctor.java

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent; // Dùng cho validation
import jakarta.validation.constraints.NotNull; // Dùng cho validation

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments") // Ánh xạ tới bảng appointments trong DB
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId; 

    // Tiêu chí 1: Mối quan hệ @ManyToOne với Doctor
    @ManyToOne 
    @JoinColumn(name = "doctor_id", nullable = false) // Khóa ngoại tới bảng Doctors
    private Doctor doctor; 

    // Tiêu chí 1: Mối quan hệ @ManyToOne với Patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false) // Khóa ngoại tới bảng Patients
    private Patient patient; 

    // Tiêu chí 2: appointmentTime kiểu LocalDateTime và Validation
    @NotNull(message = "Appointment time is required.")
    @FutureOrPresent(message = "Appointment must be scheduled for the present or future.")
    private LocalDateTime appointmentTime; 

    private String reason;
    private String status;
    
    // Constructors, Getters, and Setters...
    // ...
}
