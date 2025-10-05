package com.project.backend.controllers; // Package thường là 'controllers'

import com.project.backend.services.DoctorService; // Giả sử có DoctorService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService; // Tầng Service sẽ xử lý logic

    // Tiêu chí 1 & 2: GET endpoint, Tham số động, Xác thực Token (giả định), ResponseEntity
    @GetMapping("/{doctorId}/availability") // Tham số động: {doctorId} và @RequestParam cho ngày
    public ResponseEntity<?> getDoctorAvailability(
        @PathVariable Long doctorId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestHeader(name = "Authorization") String token // Giả định xác thực qua header Token
    ) {
        // 1. Xác thực Token (Giả định logic xác thực)
        if (token == null || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>("Unauthorized access.", HttpStatus.UNAUTHORIZED);
        }

        try {
            // 2. Lấy thông tin lịch rảnh
            List<LocalTime> availableTimes = doctorService.findAvailability(doctorId, date);

            // 3. Trả về phản hồi có cấu trúc (ResponseEntity)
            return new ResponseEntity<>(availableTimes, HttpStatus.OK);

        } catch (Exception e) {
            // Xử lý lỗi (ví dụ: Doctor không tồn tại)
            return new ResponseEntity<>("Error retrieving availability: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
