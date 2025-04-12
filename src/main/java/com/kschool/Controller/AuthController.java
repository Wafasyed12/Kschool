package com.kschool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		// Invalidate the session
		session.invalidate();
		// Redirect to the login page
		return "redirect:/users/login";
	}

}