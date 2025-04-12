	package com.kschool.Service;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	import com.kschool.Entity.Student;
	import com.kschool.Entity.Teacher;
	import com.kschool.Repository.BatchRepository;
	import com.kschool.Repository.StudentRepository;
	import com.kschool.Repository.TeacherRepository;
	
	import java.util.List;
	
	@Service
	public class TeacherService {
	
	    @Autowired
	    private TeacherRepository teacherRepository;
	    
	    @Autowired
	    private BatchRepository batchRepository;
	
	    public void addTeacher(Teacher teacher) {
	        teacherRepository.save(teacher);
	    }
	
	    public List<Teacher> getAllTeachers() {
	        return teacherRepository.findAll();
	    }
	
	    public Teacher getTeacherById(Long id) {
	        return teacherRepository.findById(id).orElse(null);
	    }
	
	    @Autowired
	    private StudentRepository studentRepository;
	
	    public List<Student> getStudentsByTeacherId(Long teacherId) {
	        return studentRepository.findByBatchTeacherId(teacherId);
	    }
	    
	    
	    public void updateTeacher(Long id, Teacher teacher) {
	        System.out.println("Debug: Entered updateTeacher method in TeacherService");
	        System.out.println("Debug: Teacher ID to update: " + id);
	        System.out.println("Debug: Teacher data to update: " + teacher);
	
	        // Fetch existing teacher from the database
	        Teacher existingTeacher = teacherRepository.findById(id).orElse(null);
	        System.out.println("Existing teacher fetched from db: " + existingTeacher);
	
	        if (existingTeacher != null) {
	            teacher.setId(id);
	            System.out.println("Teacher ID set to: " + teacher.getId());
	
	            teacherRepository.save(teacher);
	            System.out.println("Teacher updated and saved to the database");
	        } else {
	            System.out.println("Teacher not found with ID: " + id);
	        }
	    }
	
	    public void deleteTeacher(Long id) {
	        teacherRepository.deleteById(id);
	    }
	    public boolean isTeacherAssignedToBatch(Long teacherId) {
	        return batchRepository.existsByTeacherId(teacherId); 
	    }
	    
	    public Teacher verifyTeacher(String email, String password) {
	        return teacherRepository.findByEmailAndPassword(email, password);
	    }
	    
	    public long getTotalTeachers() {
	        return teacherRepository.count(); // Use Spring Data JPA's count() method
	    }
	}
	    
	    
	
