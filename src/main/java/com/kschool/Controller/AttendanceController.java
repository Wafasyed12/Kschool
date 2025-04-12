package com.kschool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kschool.Entity.Attendance;
import com.kschool.Service.AttendanceService;
import com.kschool.Service.StudentService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private StudentService studentService;

    @PostMapping("/teacher/attendance")
    public String markAttendance(@RequestParam Long studentId, 
                                 @RequestParam Long batchId, 
                                 @RequestParam String status,
                                 @RequestParam String date, 
                                 RedirectAttributes redirectAttributes) {
        LocalDate attendanceDate = LocalDate.parse(date);
        attendanceService.markAttendance(studentId, batchId, status, attendanceDate);

        redirectAttributes.addFlashAttribute("message", "Attendance marked successfully!");
        return "redirect:/teacher/students/" + batchId;
    }
    
    
    @GetMapping("/teacher/student-history/{studentId}")
    public String viewStudentAttendanceHistory(@PathVariable Long studentId, Model model) {
        List<Attendance> attendanceList = attendanceService.getStudentAttendance(studentId);
        String studentName = studentService.getStudentNameById(studentId);

        // Get the latest attendance date (first item in the sorted list)
        if (!attendanceList.isEmpty()) {
            LocalDate latestAttendanceDate = attendanceList.get(0).getDate();
            model.addAttribute("latestAttendanceDate", latestAttendanceDate);
        }

        model.addAttribute("studentName", studentName);
        model.addAttribute("attendanceList", attendanceList);

        return "student-history";
    }
    
}
