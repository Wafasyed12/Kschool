package com.kschool.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.kschool.Entity.Teacher;
import com.kschool.Repository.TeacherRepository;

@Service
public class TeacherAuthService {

	@Autowired
	private TeacherRepository teacherRepository;

	public Teacher verifyTeacher(String email, String password) {
		return teacherRepository.findByEmailAndPassword(email, password);
	}
}
