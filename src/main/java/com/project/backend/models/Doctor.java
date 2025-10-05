package com.project.backend.models; // Đảm bảo package này khớp với đường dẫn

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {

    // Tiêu chí 1: Khóa chính
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId; 

    private String fullName;
    private String specialization;
    
    // Tiêu chí 2: availableTimes field
    @OneToMany(mappedBy = "doctor") 
    private List<TimeSlot> availableTimes; 

    // Constructors và Getters/Setters phải được thêm vào đây
    
}
