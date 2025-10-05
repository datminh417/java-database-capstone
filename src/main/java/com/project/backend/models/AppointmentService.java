package com.project.backend.services; // Package thường là 'services'

import com.project.backend.models.Appointment;
import com.project.backend.repositories.AppointmentRepository; // Giả sử có AppointmentRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    // Tiêm Repository vào để tương tác với DB
    @Autowired
    private AppointmentRepository appointmentRepository;

    // Tiêu chí 1: Implement phương thức booking (lưu)
    @Transactional
    public Appointment bookAppointment(Appointment newAppointment) {
        // Thêm logic kiểm tra tính hợp lệ (ví dụ: giờ đã bị đặt chưa) tại đây
        // ... Logic kiểm tra ...

        // Lưu cuộc hẹn vào cơ sở dữ liệu
        return appointmentRepository.save(newAppointment);
    }

    // Tiêu chí 2: Phương thức truy xuất cuộc hẹn của Doctor theo ngày
    public List<Appointment> getDoctorAppointmentsByDate(Long doctorId, LocalDate date) {
        // Phương thức này sẽ gọi một phương thức tùy chỉnh từ Repository
        // Ví dụ: findByDoctorIdAndAppointmentDate
        
        // Cần chuyển đổi LocalDate thành range (start of day, end of day) nếu dùng LocalDateTime
        // Giả định Repository có thể xử lý việc tìm kiếm theo ID và ngày
        return appointmentRepository.findByDoctorIdAndDate(doctorId, date);
    }
}
