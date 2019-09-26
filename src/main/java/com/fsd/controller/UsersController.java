package com.fsd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.AppResponse;
import com.fsd.model.Users;
import com.fsd.repository.UsersRepository;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepository;
	private AppResponse appResponse;
	
	@GetMapping("/Users")
	public ResponseEntity<?> getAllUsers() {
		List<Users> users = usersRepository.findAll();		
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, users, "User List"));
	}
	
	@GetMapping("/sortUsers/{sort_by}")
	public ResponseEntity<?> getAllUsersInOrder(@PathVariable("sort_by") String sort_by) {
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION, sort_by);
		List<Users> users = usersRepository.findAll(sort);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, users, "User Sorted By "+sort_by));
	}
	
	@GetMapping("/User/{userid}")
	public ResponseEntity<?> getUserById(@PathVariable("userid") long userid) {
		Users users = usersRepository.getOne(userid);
		if(users!=null) {
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, users, "User Detail Of "+userid));
		}
		else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "User detail not found for "+userid));
	}
	
	@GetMapping("/searchUser/{firstname}")
	public ResponseEntity<?> searchByFirstName(@PathVariable("firstname") String firstname) {
		List<Users> users = usersRepository.findByFirstName(firstname);		
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, users, "User Searched By "+firstname));
	}

	@GetMapping("/deleteUser/{userid}")
	public ResponseEntity<?> deleteUser(@PathVariable("userid") long userid) {
		Users user = usersRepository.getOne(userid);
		if(user!=null) {
			usersRepository.delete(user);
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, null, "User detail of "+userid+ "deleted."));		
		}else {
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Problem deleting detail of "+userid));					
		}			
	}

	@PostMapping("/addUser")
	public ResponseEntity<?> saveUser(@Valid @RequestBody Users user) {
		usersRepository.save(user);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, user, "User detail added."));					
	}
	
	@PostMapping("/updateUser/{userid}")
	public ResponseEntity<?> updateUser(@Valid @PathVariable("userid") long userid, @Valid @RequestBody Users userDetail) {
		Users user = usersRepository.getOne(userid);
		if(user!=null) {
			user.setFirstname(userDetail.getFirstname());
			user.setLastname(userDetail.getLastname());
			user.setEmployeeid(userDetail.getEmployeeid());
			usersRepository.save(user);
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, user, "User detail of "+userid+ " updated."));
		}else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, user, "Problem updating User detail of "+userid));
		}
}
