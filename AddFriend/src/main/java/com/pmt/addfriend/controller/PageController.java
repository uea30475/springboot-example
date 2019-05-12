package com.pmt.addfriend.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pmt.addfriend.exception.ResourceNotFoundException;
import com.pmt.addfriend.models.User;
import com.pmt.addfriend.repository.UserRepository;
import com.pmt.addfriend.service.UserService;

@Controller
public class PageController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@GetMapping(path= {"/","/home","/index","/friends"})
	public String home(Model model)  {
		User user = userService.findById("123");
		model.addAttribute("title","Home");
		model.addAttribute("userClickListFriends",true);
		model.addAttribute("friends",user.getFriends());
		return "index";
	}
	
	@GetMapping(path= "/get/friends")
	@ResponseBody
	public ResponseEntity<Set<User>>  getfriends()  {
		User user = userService.findById("123");
		ResponseEntity<Set<User>> responseEntity = new ResponseEntity<Set<User>>(user.getFriends(), HttpStatus.OK);
		return responseEntity;
	}
	
	@PutMapping(path= "/delete/friend/{friendId}")
	@ResponseBody
	public ResponseEntity<?>deletefriends(@PathVariable String friendId)  {
		//User user = userService.findById("123");
		String id_me ="123";
		if(!userRepository.existsById(id_me) ||!userRepository.existsById(friendId)) {
			throw new ResourceNotFoundException("User "  + " not found");
		}
		return userRepository.findById("123").map(user->{
			Set<User>friends=user.getFriends();
			User friendDelete = friends.stream().filter(friend->friend.getId().equals(friendId)).findFirst().get();
			friends.remove(friendDelete);
			userRepository.save(user);
			return ResponseEntity.ok().build();
			
		}).orElseThrow(()->new ResourceNotFoundException("User "  + " not found"));
	}
	
	@GetMapping(path= "/search")
	public String search(@RequestParam(name="q", required=false) String q,Model model)  {		
		model.addAttribute("title","Search");
		model.addAttribute("userClickSearchFriends",true);
		model.addAttribute("users",userService.findByName(q));
		return "index";
	}
	
	@GetMapping(path= "/friends-request")
	public String friends_request(Model model)  {		
		model.addAttribute("title","Friend Request");
		model.addAttribute("userClickFriendRequest",true);
		return "index";
	}
	
	@GetMapping(path= {"/users"}) // Map ONLY GET Requests
	@ResponseBody
	public String users(Model model)  {
		return userService.findAll().toString();
	}

}
