package com.project.backend.repositories; // Package thường là 'repositories'

import com.project.backend.models.Patient; // Import Entity Patient
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Rất nên dùng Optional cho các truy vấn trả về 0 hoặc 1 kết quả

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // TIÊU CHÍ 1: Phương thức truy xuất bệnh nhân bằng email (Derived Query)
    // Spring Data JPA sẽ tự động triển khai phương thức này
    Optional<Patient> findByEmail(String email);

    // TIÊU CHÍ 2: Phương thức truy xuất bệnh nhân bằng email HOẶC số điện thoại (Derived Query)
    Optional<Patient> findByEmailOrPhoneNumber(String email, String phoneNumber);

    /* * Bạn cũng có thể dùng Custom Query cho tiêu chí 2:
    * @Query("SELECT p FROM Patient p WHERE p.email = :email OR p.phoneNumber = :phone")
    * Optional<Patient> findByEmailOrPhoneCustom(@Param("email") String email, @Param("phone") String phone);
    */
}
