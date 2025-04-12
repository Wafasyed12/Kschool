package com.kschool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.kschool.Service.StudentService;
import com.kschool.Service.TeacherStudentService;
import com.kschool.Entity.Batch;
import com.kschool.Entity.Student;

@Controller
public class TeacherStudentController {

	@Autowired
	private TeacherStudentService teacherStudentService;

	@GetMapping("/teacher/students/{batchId}")
	public String viewStudents(@PathVariable Long batchId, HttpSession session, Model model) {
		Long teacherId = (Long) session.getAttribute("TeacherId");
		if (teacherId == null) {
			return "redirect:/teacher/login";
		}

		// Fetch the teacher's name (assuming you have a method to get the teacher's
		// name by ID)
		String teacherName = teacherStudentService.getTeacherNameById(teacherId);

		// Fetch students for the batch
		List<Student> students = teacherStudentService.getStudentsByBatch(batchId);

		// Add data to the model
		model.addAttribute("students", students);
		model.addAttribute("batchId", batchId);
		model.addAttribute("teacherName", teacherName); 

		return "teacher-student-list";
	}
	
	 
}
