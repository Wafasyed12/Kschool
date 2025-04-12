package com.kschool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import com.kschool.Service.TeacherService;
import com.kschool.Entity.Teacher;

@Controller
public class TeacherAuthController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teacher/login")
    public String showLoginPage() {
        return "teacher-login";
    }

    @PostMapping("/teacher/login")
    public String loginTeacher(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Teacher teacher = teacherService.verifyTeacher(email, password);
        if (teacher != null) { 
            session.setAttribute("LoggedInTeacher", email);
            session.setAttribute("TeacherId", teacher.getId());
            return "redirect:/teacher/dashboard";
        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "teacher-login";
        }
    }
    
    @GetMapping("/teacher/logout")
    public String logoutTeacher(HttpSession session) {
        session.invalidate();
        return "redirect:/teacher/login";
    }
}