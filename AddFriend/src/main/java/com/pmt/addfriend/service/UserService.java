package com.pmt.addfriend.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
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

  public List<User> findByName(String fullName) {
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

  public User findById(String id) {
    User user = userRepository.findById(id).get();
    return user;
  }

  public boolean update(User user) {
    try {
      return userRepository.save(user) != null;
    } catch (Exception e) {
      // TODO: handle exception
    }
    return false;

  }

  public boolean deleteById(String id) {
    try {
      userRepository.deleteById(id);;
      return true;
    } catch (Exception e) {
      return false;
    }
    

  }
  
  public String exportCSV(List<User>users, ServletContext context) {
	  String filePath = context.getRealPath("resources/reports");
	  String filename ="users_"+ new Date().getTime() +".csv";
	  boolean exists = new File(filePath).exists();
	  System.out.println(filename);
	  if(!exists) {
		  new File(filePath).mkdirs();
	  }
	  File file = new File(filePath+"/"+File.separator+filename);
	  try {
		FileWriter fileWriter = new FileWriter(file);
		CSVWriter writer = new CSVWriter(fileWriter);
		List<String[]> data= new ArrayList<>();
		data.add(new String[] {"Name","Gender","Birth Date","Phone","Email"});
		for (User user : users) {
			data.add(new String[] {user.getFirstName(),user.getGender(),""+user.getBirthDate(),user.getPhone(),user.getEmail()});
			
		}
		writer.writeAll(data);
		writer.close();
		return filename;
	} catch (Exception e) {
		e.printStackTrace();
		return "Failure";
	}
	  
  }



}
