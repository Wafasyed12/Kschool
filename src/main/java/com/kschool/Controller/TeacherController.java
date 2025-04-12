package com.kschool.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kschool.Entity.Student;
import com.kschool.Entity.Teacher;
import com.kschool.Repository.TeacherRepository;
import com.kschool.Service.TeacherService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class TeacherController{

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private TeacherRepository teacherRepository;

	@GetMapping("/teachers/add")
	public String showAddTeacherPage(HttpSession session, Model model) {
		String userEmail = (String) session.getAttribute("LoggedInUser");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("teacher", new Teacher());
		return "add-teacher";
	}

	@PostMapping("/teachers/add")
	public String addTeacher(@Valid Teacher teacher) {
		 System.out.println("Password before saving: " + teacher.getPassword());
		teacherService.addTeacher(teacher);
		return "redirect:/teachers/view";
	}

	@GetMapping("/teachers/view")
	public String viewTeachers(HttpSession session, Model model) {
		String userEmail = (String) session.getAttribute("LoggedInUser");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("teachers", teacherService.getAllTeachers());
		return "view-teacher";
	}

	@GetMapping("/teachers/search")
	public String searchTeachers(@RequestParam("name") String name, Model model) {
		List<Teacher> teachers = teacherRepository.findByNameContainingIgnoreCase(name);
		model.addAttribute("teachers", teachers);
		return "view-teacher"; // Ensure this matches your HTML file name
	}

	@PostMapping("/teachers/updateteacher")
	public String showUpdateTeacherPage(@RequestParam String id, HttpSession session, Model model) {
		System.out.println("Teacher id received from request: " + id);

		Teacher teacher = teacherService.getTeacherById(Long.parseLong(id));
		System.out.println("Fetched teacher from database: " + teacher);

		if (teacher == null) {
			System.out.println("Teacher not found, redirecting to /teachers/view");
			return "redirect:/teachers/view";
		}

		String userEmail = (String) session.getAttribute("LoggedInUser");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("teacher", teacher);

		System.out.println("Teacher added to model, rendering update-teacher page");
		return "update-teacher";
	}

	@PostMapping("/teachers/update")
	public String updateTeacher(@ModelAttribute("teacher") Teacher teacher) {
		teacherService.updateTeacher(teacher.getId(), teacher);
		return "redirect:/teachers/view";
	}

	@GetMapping("/teachers/delete/{id}")
	public String deleteTeacher(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		Teacher teacher = teacherService.getTeacherById(id);

		if (teacher != null) {
			boolean isAssignedToBatch = teacherService.isTeacherAssignedToBatch(id);

			if (isAssignedToBatch) {
				redirectAttributes.addFlashAttribute("error",
						"This teacher is assigned to a batch. You cannot delete it.");
			} else {
				teacherService.deleteTeacher(id);
				redirectAttributes.addFlashAttribute("success",
						"Teacher " + teacher.getName() + " has been deleted successfully.");
			}
		}

		return "redirect:/teachers/view";
	}

	

	  

	@GetMapping("/teachers/total")
	@ResponseBody
	public long getTotalTeachers() {
	    return teacherService.getTotalTeachers();
	}
	}

