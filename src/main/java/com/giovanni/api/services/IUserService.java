package com.giovanni.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.giovanni.api.entities.User;

public interface IUserService {
	
	public Iterable<User> findAll();
	
	public Page<User> findAll(Pageable pageable);
	
	public Optional<User> finfById(Long id);
	
	public User save(User user);
	
	public void deleteById(Long id);

}
