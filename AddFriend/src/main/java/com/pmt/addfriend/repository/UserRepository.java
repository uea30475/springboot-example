package com.pmt.addfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pmt.addfriend.models.User;

public interface UserRepository extends CrudRepository<User, String> {
	
	List<User> findByFirstNameContaining(String fullName);
	

}
