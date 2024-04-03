package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dto.UserDTO;
import com.app.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		UserDTO createdUser = userService.createUser(userDTO);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
		UserDTO updatedUser = userService.updateUser(userDTO, userId);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@GetMapping("/emailAndPassword")
	public ResponseEntity<UserDTO> getUserByEmailAndPassword(@RequestParam String email,
			@RequestParam String password) {
		UserDTO user = userService.getUserByEmailAndPassword(email, password);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
		UserDTO user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
		userService.deleteUserById(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
