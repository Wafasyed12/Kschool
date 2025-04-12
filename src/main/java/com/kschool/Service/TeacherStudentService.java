package com.kschool.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.kschool.Entity.Batch;
import com.kschool.Entity.Student;
import com.kschool.Entity.Teacher;
import com.kschool.Repository.BatchRepository;
import com.kschool.Repository.StudentRepository;
import com.kschool.Repository.TeacherRepository;

@Service
public class TeacherStudentService {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private BatchRepository batchRepository;
    
    public List<Student> getStudentsByBatch(Long batchId) {
        return studentRepository.findByBatchId(batchId);
    }
    
    public List<Batch> getBatchesByTeacherId(Long teacherId) {
        return batchRepository.findByTeacherId(teacherId);
    }
    
    public String getTeacherNameById(Long teacherId) {
        // Assuming you have a TeacherRepository to fetch the teacher's name
        return teacherRepository.findById(teacherId)
                                .map(Teacher::getName) // Assuming the Teacher entity has a getName() method
                                .orElse("Teacher"); // Default name if not found
    }
    public List<Student> getStudentsByTeacherId(Long teacherId) {
        return studentRepository.findStudentsByTeacherId(teacherId);
    }

}
