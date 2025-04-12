package com.kschool.Repository;

import com.kschool.Entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByTeacherId(Long teacherId);
    boolean existsByTeacherId(Long teacherId);
    
    @Query("SELECT COUNT(b) > 0 FROM Batch b WHERE b.batchcode = :batchcode AND b.teacher.id <> :teacherId")
    boolean existsByBatchcodeAndDifferentTeacher(String batchcode, Long teacherId);
    
    List<Batch> findByBatchcodeContainingIgnoreCase(String batchcode);
}
