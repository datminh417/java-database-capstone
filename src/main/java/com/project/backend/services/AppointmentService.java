package com.project.backend.services; // Đảm bảo package này đúng

import com.project.backend.models.Appointment;
import com.project.backend.repositories.AppointmentRepository; // Sẽ tạo ở Question 7
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository; // Tiêm Repository

    // TIÊU CHÍ 1: Implement phương thức booking (lưu cuộc hẹn)
    @Transactional
    public Appointment bookAppointment(Appointment newAppointment) {
        // [Logic nghiệp vụ như kiểm tra trùng lặp có thể ở đây]
        return appointmentRepository.save(newAppointment);
    }

    // TIÊU CHÍ 2: Định nghĩa phương thức truy xuất cuộc hẹn của Doctor theo ngày
    public List<Appointment> getDoctorAppointmentsByDate(Long doctorId, LocalDate date) {
        // Tính toán range thời gian
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        
        // Gọi phương thức từ Repository (phương thức này sẽ được định nghĩa ở Q7)
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
            doctorId, startOfDay, endOfDay
        );
    }
}
