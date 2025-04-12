package com.kschool.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kschool.Entity.Batch;
import com.kschool.Entity.Student;
import com.kschool.Entity.User;

public interface StudentRepository extends JpaRepository <Student, Long>{
	  List<Student> findByBatchId(Long batchId);
	List<Student> findByNameContainingIgnoreCase(String name);
	   List<Student> findByBatchTeacherId(Long teacherId);
	   @Query("SELECT s FROM Student s JOIN s.batch b WHERE b.teacher.id = :teacherId")
	   List<Student> findStudentsByTeacherId(@Param("teacherId") Long teacherId);
List<Student> findByBatch(Batch batch);
	   

}

