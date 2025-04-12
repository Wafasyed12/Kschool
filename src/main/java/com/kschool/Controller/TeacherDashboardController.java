package com.kschool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.kschool.Service.BatchService;
import com.kschool.Service.TeacherDashboardService;
import com.kschool.Entity.Batch;

@Controller
public class TeacherDashboardController {

	 @Autowired
	    private TeacherDashboardService teacherDashboardService;

	    @GetMapping("/teacher/dashboard")
	    public String showDashboard(HttpSession session, Model model) {
	        Long teacherId = (Long) session.getAttribute("TeacherId");
	        String userEmail = (String) session.getAttribute("LoggedInUser");

	        if (teacherId == null) {
	            return "redirect:/teacher/login";
	        }

	        // Fetch the teacher's name or email
	        String teacherName = teacherDashboardService.getTeacherNameById(teacherId); // Assuming you have this method
	        model.addAttribute("teacherName", teacherName); // Add the teacher's name to the model

	        List<Batch> assignedBatches = teacherDashboardService.getBatchesByTeacher(teacherId);
	        model.addAttribute("batches", assignedBatches);
	        model.addAttribute("userEmail", userEmail); // Add user email to the model

	        return "teacher-dashboard";
	    }
	}