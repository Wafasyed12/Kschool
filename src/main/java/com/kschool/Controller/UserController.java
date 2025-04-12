package com.kschool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import com.kschool.Service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String email,
			@RequestParam String password) {
		userService.registerUser(username, email, password);
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session,
			Model model) {
		String message = userService.verifyUser(email, password);
		if (message.equals("success")) {
			session.setAttribute("LoggedInUser", email);
//			System.out.println(email);
			model.addAttribute("userEmail", email);

			return "dashboard";
		} else {
			return "login";
		}
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

}
