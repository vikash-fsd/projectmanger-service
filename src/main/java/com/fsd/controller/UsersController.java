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
	
	@GetMapping("/User/{userid}")
	public ResponseEntity<Users> getUserById(@PathVariable("userid") long userid) {
		Users users = usersRepository.getOne(userid);
		if(users!=null)
			return ResponseEntity.ok().body(users);
		else
			throw new ResourceNotFoundException("User", "ID", userid);
	}
	
	@GetMapping("/searchUser/{firstname}")
	public ResponseEntity<List<Users>> searchByFirstName(@PathVariable("firstname") String firstname) {
		List<Users> users = usersRepository.findByFirstName(firstname);
		return ResponseEntity.ok().body(users);
	}

	@DeleteMapping("/deleteUser/{userid}")
	public ResponseEntity<?> deleteUser(@PathVariable("userid") long userid) {
		Users user = usersRepository.getOne(userid);
		if(user!=null) {
			usersRepository.delete(user);
			return ResponseEntity.ok().body("User with ID " + userid + " has been deleted.");
		
		}else
			throw new ResourceNotFoundException("User", "ID", userid);
	}

	@PostMapping("/addUser")
	public ResponseEntity<?> saveUser(@Valid @RequestBody Users user) {
		usersRepository.save(user);
		return ResponseEntity.accepted().body("User detail has been saved.");
	}
	
	@PutMapping("/updateUser/{userid}")
	public ResponseEntity<?> updateUser(@Valid @PathVariable("userid") long userid, @Valid @RequestBody Users userDetail) {
		Users user = usersRepository.getOne(userid);
		if(user!=null) {
			user.setFirstname(userDetail.getFirstname());
			user.setLastname(userDetail.getLastname());
			user.setEmployeeid(userDetail.getEmployeeid());
			usersRepository.save(user);
			return ResponseEntity.ok().body("User with ID " + userid + " has been updated.");
		
		}else
			throw new ResourceNotFoundException("User", "ID", userid);
	}
}
