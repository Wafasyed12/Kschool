package com.kschool.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kschool.Entity.Attendance;
import com.kschool.Repository.AttendanceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void markAttendance(Long studentId, Long batchId, String status, LocalDate date) {
        // Check if an attendance record already exists for the student on the given date
        Optional<Attendance> existingAttendance = attendanceRepository.findByStudentIdAndDate(studentId, date);

        if (existingAttendance.isPresent()) {
            // Update existing record instead of creating a duplicate
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(status);
            attendanceRepository.save(attendance);
        } else {
            // Create new attendance entry if not already present
            Attendance newAttendance = new Attendance();
            newAttendance.setStudentId(studentId);
            newAttendance.setBatchId(batchId);
            newAttendance.setStatus(status);
            newAttendance.setDate(date);
            attendanceRepository.save(newAttendance);
        }
    }
    
    public List<Attendance> getStudentAttendance(Long studentId) {
        return attendanceRepository.findByStudentIdOrderByDateDesc(studentId);
    }

}
