package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Users;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

	@Autowired
	UsersService service;

	@PostMapping("/registration")
	public String addUsers(@ModelAttribute Users user) {
		boolean userStatus = service.emailExists(user.getEmail());

		if (userStatus == false) {
			service.addUser(user);
			System.out.println("New user added");
		} else {
			System.out.println("User with this email already exisits");
		}

		return "home";
	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {
		if (service.validateUser(email, password) == true) {
			String role = service.getRole(email);

			session.setAttribute("email", email);

			if (role.equals("admin")) {
				return "adminHome";
			} else {
				return "customerHome";
			}
		} else {
			return "login";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

}

/*
 * public String addUsers(@RequestParam("username") String
 * username, @RequestParam("email") String email,
 * 
 * @RequestParam("password") String password, @RequestParam("gender") String
 * gender,
 * 
 * @RequestParam("role") String role, @RequestParam("address") String address) {
 * 
 * System.out.println("[username=" + username + ", email=" + email +
 * ", password=" + password + ", gender=" + gender + ", role=" + role +
 * ", address=" + address + "]");
 * 
 * return "home"; }
 * 
 */
