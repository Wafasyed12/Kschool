package com.kschool.Service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kschool.Entity.Batch;
import com.kschool.Entity.Student;
import com.kschool.Repository.BatchRepository;
import com.kschool.Repository.StudentRepository;

@Service
public class BatchService {

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private StudentRepository studentRepository;

	public Batch getBatchById(Long id) {
		return batchRepository.findById(id).orElse(null);
	}

	public List<Batch> getAllBatches() {
		return batchRepository.findAll();
	}

	public boolean isOverlapping(Long teacherId, LocalTime startTime, LocalTime endTime) {
		if (startTime == null || endTime == null) {
			return false; // If either time is null, don't check for overlap
		}

		List<Batch> batches = batchRepository.findByTeacherId(teacherId);
		for (Batch batch : batches) {
			// Only proceed if both startTime and endTime are not null
			if (batch.getStartTime() != null && batch.getEndTime() != null) {
				if (startTime.isBefore(batch.getEndTime()) && endTime.isAfter(batch.getStartTime())) {
					return true; // Overlap exists
				}
			}
		}
		return false; // No overlap
	}

	public boolean addBatch(Batch batch) {
		if (isOverlapping(batch.getTeacher().getId(), batch.getStartTime(), batch.getEndTime())) {
			return false; // Prevent saving overlapping batch
		}

		boolean isBatchCodeExists = batchRepository.existsByBatchcodeAndDifferentTeacher(batch.getBatchcode(),
				batch.getTeacher().getId());
		if (isBatchCodeExists) {
			return false; 
		}

		batchRepository.save(batch);
		return true;
	}

	public boolean deleteBatch(Long batchId) {
		Batch batch = batchRepository.findById(batchId).orElse(null);

		if (batch == null) {
			return false;
		}

		List<Student> studentsInBatch = studentRepository.findByBatch(batch);
		if (!studentsInBatch.isEmpty()) {
			return false; 
		}

		batchRepository.delete(batch);
		return true; 
	}

	public List<Batch> getAllBatch() {
		return batchRepository.findAll();
	}

	public long getTotalBatches() {
		return batchRepository.count();
	}
}
