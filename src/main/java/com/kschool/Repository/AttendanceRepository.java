package com.kschool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kschool.Entity.Attendance;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	List<Attendance> findByStudentIdOrderByDateDesc(Long studentId);

    
    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date); // New method
}
