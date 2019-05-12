package com.pmt.addfriend.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pmt.addfriend.models.User;
import com.pmt.addfriend.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		try {
			for (User user : userRepository.findAll()) {
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public List<User> findByName( String fullName) {
		List<User> users = new ArrayList<User>();
		try {
			for (User user : userRepository.findByFirstNameContaining(fullName)) {
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public User findById( String id) {
		User user = userRepository.findById(id).get();
		return user;
	}
	
//	public User update( String id) {
//		
//		User user = null;
//		if(!userRepository.existsById(id)) {
//			//throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
//		}
//		
//		return userRepository.findById(id).map(user->{
//			
//		});
//	}
	
	

}
