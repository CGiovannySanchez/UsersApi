package com.giovanni.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giovanni.api.entities.User;
import com.giovanni.api.services.IUserService;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
	
	@Autowired
	private IUserService iUserService;
	
	//Create a new USER
	@PostMapping
	public ResponseEntity<?> create(@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(iUserService.save(user));		
	}
	
	//Read an user
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id){
		Optional<User> oUser = iUserService.finfById(id);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oUser);
	}
	
	//Update an user
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User userDetails, @PathVariable(value = "id") Long userId){
		Optional<User> user = iUserService.finfById(userId);
		
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		user.get().setName(userDetails.getName());
		user.get().setLastName(userDetails.getLastName());
		user.get().setEmail(userDetails.getEmail());
		user.get().setEnabled(userDetails.getEnabled());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(iUserService.save(user.get()));
		
	}
	
	
	//Delete an user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId){
		if(!iUserService.finfById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		iUserService.deleteById(userId);
		return ResponseEntity.ok().build();	
	
	}
	
	//Read all
	@GetMapping
	public List<User> readAll(){
		List<User> users = StreamSupport
				.stream(iUserService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return users;
	}

}
