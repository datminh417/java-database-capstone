package com.project.backend.controllers; // Cùng package với DoctorController

import com.project.backend.models.Prescription; // Cần có Entity Prescription
import com.project.backend.services.PrescriptionService; // Cần có Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // Import cho validation

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService; 

    // Tiêu chí 1 & 2: POST endpoint, Validation Body, Xác thực Token (giả định), ResponseEntity
    @PostMapping
    public ResponseEntity<?> createPrescription(
        // Tiêu chí 1: Xác thực Token qua Header
        @RequestHeader(name = "Authorization", required = false) String token,
        
        // Tiêu chí 1: Validating Request Body
        @Valid @RequestBody Prescription prescriptionRequest 
    ) {
        // 1. Xác thực Token (Giả định logic xác thực)
        if (token == null || !token.startsWith("Bearer ")) {
            // Tiêu chí 2: Trả về lỗi có cấu trúc
            return new ResponseEntity<>("Authorization failed: Missing or invalid token.", HttpStatus.UNAUTHORIZED);
        }

        try {
            // Lưu đơn thuốc qua Service
            Prescription savedPrescription = prescriptionService.savePrescription(prescriptionRequest);

            // Tiêu chí 2: Trả về thành công có cấu trúc
            return new ResponseEntity<>(savedPrescription, HttpStatus.CREATED);

        } catch (Exception e) {
            // Xử lý lỗi (ví dụ: lỗi DB, dữ liệu không hợp lệ)
            // Tiêu chí 2: Trả về lỗi có cấu trúc
            return new ResponseEntity<>("Error saving prescription: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
