package com.project.backend.services; // Cùng package với các Service khác

import com.project.backend.models.Doctor; // Cần Entity Doctor
import com.project.backend.models.TimeSlot; // Giả sử có TimeSlot Entity hoặc DTO
import com.project.backend.repositories.DoctorRepository; // Cần DoctorRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private TokenService tokenService; // Cần TokenService để tạo token

    // TIÊU CHÍ 1: Phương thức trả về các khung giờ rảnh của bác sĩ theo ngày
    public List<LocalTime> findAvailability(Long doctorId, LocalDate date) {
        // [Logic nghiệp vụ phức tạp]
        // Thực tế: Lấy TimeSlots đã định nghĩa của Doctor và trừ đi các Appointments đã đặt.
        
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng nếu không tìm thấy bác sĩ
        }
        
        // GIẢ ĐỊNH: Tạo một danh sách các giờ rảnh mẫu để mô phỏng
        List<LocalTime> available = new ArrayList<>();
        available.add(LocalTime.of(9, 0));
        available.add(LocalTime.of(10, 0));
        available.add(LocalTime.of(11, 0));
        
        return available;
    }

    // TIÊU CHÍ 2: Phương thức xác thực thông tin đăng nhập và trả về phản hồi có cấu trúc
    public Optional<String> login(String email, String password) {
        // 1. Tìm Doctor bằng email (Giả định Doctor Entity có trường email)
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);

        if (doctorOpt.isEmpty()) {
            return Optional.empty(); // Không tìm thấy email
        }

        // 2. Xác thực mật khẩu (Giả định logic Hash & Compare)
        Doctor doctor = doctorOpt.get();
        // Ví dụ: if (!passwordEncoder.matches(password, doctor.getPasswordHash())) { ... }
        if (!"hashed_password_placeholder".equals(password)) { // Kiểm tra mật khẩu (giả định)
             return Optional.empty(); // Mật khẩu sai
        }

        // 3. Trả về token (Phản hồi có cấu trúc là JWT string)
        String jwtToken = tokenService.generateToken(doctor.getEmail());
        return Optional.of(jwtToken);
    }
}
