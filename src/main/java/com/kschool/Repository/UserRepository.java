package com.kschool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kschool.Entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmailAndPassword(String email, String password);
	
}
