package com.kschool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kschool.Entity.Student;
import com.kschool.Entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	
	 Teacher findByEmailAndPassword(String email, String password);
	 List<Teacher> findByNameContainingIgnoreCase(String name);
}

