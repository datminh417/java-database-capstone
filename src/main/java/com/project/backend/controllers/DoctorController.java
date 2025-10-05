package com.project.backend.controllers; 

import com.project.backend.services.DoctorService; // Giả định có DoctorService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService; 

    // Tiêu chí 1 & 2: GET endpoint, Tham số động, Xác thực, ResponseEntity
    @GetMapping("/{doctorId}/availability") // Tham số động {doctorId}
    public ResponseEntity<?> getDoctorAvailability(
        @PathVariable Long doctorId,
        // Tham số động (Query Param) cho ngày
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestHeader(name = "Authorization", required = false) String token // Giả định xác thực
    ) {
        // Kiểm tra Token (Tiêu chí 2: Xác thực Token)
        if (token == null || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>("Unauthorized access. Token required.", HttpStatus.UNAUTHORIZED);
        }

        try {
            // Lấy thông tin lịch rảnh từ Service
            // Phương thức này là giả định, bạn có thể thay đổi tùy thuộc vào DoctorService của bạn
            List<LocalTime> availableTimes = doctorService.findAvailability(doctorId, date);

            // Trả về phản hồi có cấu trúc (Tiêu chí 2: ResponseEntity)
            return new ResponseEntity<>(availableTimes, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
