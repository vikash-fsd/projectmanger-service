package com.fsd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.exception.ResourceNotFoundException;
import com.fsd.model.Users;
import com.fsd.repository.UsersRepository;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepository;
	
	@GetMapping("/Users")
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> users = usersRepository.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping("/sortUsers/{sort_by}")
	public ResponseEntity<List<Users>> getAllUsersInOrder(@PathVariable("sort_by") String sort_by) {
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION, sort_by);
		List<Users> users = usersRepository.findAll(sort);
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping("/Users/{user_id}")
	public ResponseEntity<Users> getUserById(@PathVariable("user_id") long user_id) {
		Users users = usersRepository.getOne(user_id);
		if(users!=null)
			return ResponseEntity.ok().body(users);
		else
			throw new ResourceNotFoundException("User", "ID", user_id);
	}
	
	@GetMapping("/searchUser/{first_name}")
	public ResponseEntity<List<Users>> searchByFirstName(@PathVariable("first_name") String first_name) {
		List<Users> users = usersRepository.findByFirstName(first_name);
		return ResponseEntity.ok().body(users);
	}

	@DeleteMapping("/deleteUser/{user_id}")
	public ResponseEntity<?> deleteUser(@PathVariable("user_id") long user_id) {
		Users user = usersRepository.getOne(user_id);
		if(user!=null) {
			usersRepository.delete(user);
			return ResponseEntity.ok().body("User with ID " + user_id + " has been deleted.");
		
		}else
			throw new ResourceNotFoundException("User", "ID", user_id);
	}

	@PostMapping("/addUser")
	public ResponseEntity<?> saveUser(@Valid @RequestBody Users user) {
		usersRepository.save(user);
		return ResponseEntity.ok().body("User detail has been saved.");
	}
	
	@PutMapping("/updateUser/{user_id}")
	public ResponseEntity<?> updateUser(@Valid @PathVariable("user_id") long user_id, @Valid @RequestBody Users userDetail) {
		Users user = usersRepository.getOne(user_id);
		if(user!=null) {
			user.setFirst_name(userDetail.getFirst_name());
			user.setLast_name(userDetail.getLast_name());
			user.setEmployee_id(userDetail.getEmployee_id());
			usersRepository.save(user);
			return ResponseEntity.ok().body("User with ID " + user_id + " has been updated.");
		
		}else
			throw new ResourceNotFoundException("User", "ID", user_id);
	}
}
