package com.kschool.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kschool.Entity.Batch;
import com.kschool.Entity.Student;
import com.kschool.Repository.StudentRepository;
import com.kschool.Service.BatchService;
import com.kschool.Service.StudentService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private BatchService batchService;

	@GetMapping("/students/add")
	public String showAddStudentPage(HttpSession session, Model model) {
		String userEmail = (String) session.getAttribute("LoggedInUser");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("student", new Student());

		List<Batch> batches = batchService.getAllBatches();
		model.addAttribute("batches", batches);

		return "add-student";

	}

	@PostMapping("/students/add")
	public String addStudent(@Valid Student student, @RequestParam("batchId") Long batchId) {
		// Assign the selected batch to the student
		Batch batch = batchService.getBatchById(batchId);
		student.setBatch(batch);

		System.out.println("Student before saving: " + student);

		studentService.addStudent(student);
		return "redirect:/students/view";
	}

	@GetMapping("/students/view")
	public String viewStudents(HttpSession session, Model model) {
		String userEmail = (String) session.getAttribute("LoggedInUser");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("students", studentService.getAllStudents());
		return "view-student";
	}

	@GetMapping("/students/search")
	public String searchStudents(@RequestParam("name") String name, Model model) {
		List<Student> students = studentRepository.findByNameContainingIgnoreCase(name);
		model.addAttribute("students", students);
		return "view-student"; // Ensure this matches your HTML file name
	}

	@PostMapping("/students/updatestudent")
	public String showUpdateStudentPage(@RequestParam String id, HttpSession session, Model model) {
		System.out.println("student id received from request: " + id);

		Student student = studentService.getStudentById(Long.parseLong(id));
		System.out.println("fetched student from database: " + student);

		if (student == null) {
			System.out.println("student not found redirecting to /students/view");
			return "redirect:/students/view";
		}

		String userEmail = (String) session.getAttribute("LoggedInUser");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("student", student);

		System.out.println("student added to model rendering update-student page");
		return "update-student";
	}

	@PostMapping("/students/update")
	public String updateStudent(@ModelAttribute("student") Student student) {

		// Retrieve the existing student from the database
		Student existingStudent = studentService.getStudentById(student.getId());
		// Set the batch from the existing student to the updated student
		student.setBatch(existingStudent.getBatch());

		studentService.updateStudent(student.getId(), student);
		return "redirect:/students/view";
	}

	@GetMapping("/students/delete/{id}")
	public String deleteStudent(@PathVariable Long id, Model model) {
		Student student = studentService.getStudentById(id);
		if (student != null) {
			studentService.deleteStudent(id);
			model.addAttribute("message", "Student " + student.getName() + " has been deleted.");
		}
		return "redirect:/students/view";
	}

	@GetMapping("/students/dashboard")
	public String dashboard(HttpSession session, Model model) {
	    String userEmail = (String) session.getAttribute("LoggedInUser");
	    model.addAttribute("userEmail", userEmail);
	    int totalStudents = studentService.getAllStudents().size();
	    model.addAttribute("totalStudents", totalStudents);

	    return "dashboard"; 
	}

}