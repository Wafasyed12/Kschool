package com.kschool.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.kschool.Entity.Student;
import com.kschool.Repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	

    

	public void addStudent(Student student) {
		
		studentRepository.save(student);
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}



	public Student getStudentById(Long id) {
		return studentRepository.findById(id).orElse(null);
	}

	public void updateStudent(Long id, Student student) {
		System.out.println("Debug: Entered updateStudent method in StudentService");
		System.out.println("Debug: Student ID to update: " + id);
		System.out.println("Debug: Student data to update: " + student);

		// Fetch existing student from db
		Student existingStudent = studentRepository.findById(id).orElse(null);
		System.out.println("existing student fetched from db: " + existingStudent);

		if (existingStudent != null) {
			// Set the ID of the student to be updated
			student.setId(id);
			System.out.println("student id set to: " + student.getId());

			studentRepository.save(student);
			System.out.println("student updated and saved to the db");
		} else {
			System.out.println("student not found with id: " + id);
		}
	}

	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}

	public String getStudentNameById(Long studentId) {
		return studentRepository.findById(studentId).map(Student::getName).orElse("Unknown Student");
	}
	 public long getTotalStudents() {
	        return studentRepository.count(); 
	    }
}