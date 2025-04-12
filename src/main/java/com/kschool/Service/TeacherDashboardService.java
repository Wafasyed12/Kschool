package com.kschool.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.kschool.Entity.Batch;
import com.kschool.Entity.Teacher;
import com.kschool.Repository.BatchRepository;
import com.kschool.Repository.TeacherRepository;

@Service
public class TeacherDashboardService {

    @Autowired
    private BatchRepository batchRepository;

    public List<Batch> getBatchesByTeacher(Long teacherId) {
        return batchRepository.findByTeacherId(teacherId);
    }
    
    @Autowired
    private TeacherRepository teacherRepository;

    public String getTeacherNameById(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        return teacher != null ? teacher.getName() : "Unknown Teacher";
    }
}
