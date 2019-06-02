package com.pmt.addfriend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pmt.addfriend.models.User;
import com.pmt.addfriend.service.UserService;

@Controller
public class PageController {
	@Autowired
	private UserService userService;
	@Autowired 
	private ServletContext context;

	@GetMapping(path = { "/", "/home", "/index", "/friends" })
	public String home(Model model) {
		model.addAttribute("title", "Home");
		model.addAttribute("users", userService.findAll());
		model.addAttribute("user", new User());
		return "index";
	}
	
	@GetMapping(path = "/show-user/{id}")
	public String showEditUser(@PathVariable String id,Model model) {
		model.addAttribute("title", "Edit User");
		model.addAttribute("users", userService.findAll());
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "index";
	}

	@GetMapping(path = "/search")
	public String search(@RequestParam(name = "q", required = false) String q, Model model) {
		model.addAttribute("title", "Search");
		model.addAttribute("userClickSearchFriends", true);
		model.addAttribute("users", userService.findByName(q));
		return "index";
	}

	@PostMapping(path = "/add-new")
	public String addUser(@ModelAttribute(value = "user") User user) {
		userService.update(user);
		return "redirect:/home?operation=user";
	}

	@DeleteMapping(path = "/delete-user/{id}")
	@ResponseBody
	public boolean deleteUser(@PathVariable String id) {
		return userService.deleteById(id);
	}
	
	@GetMapping(path="/export-csv")
	public void exportCSV(HttpServletRequest req, HttpServletResponse res) {
		List<User> users= userService.findAll();
		String filename = userService.exportCSV(users, context);
		if(!filename.equals("Failure")) {
			String fullPath = req.getServletContext().getRealPath("/resources/reports/"+filename);
			
			fileDownload(fullPath,res,filename);
		}
	}


	private void fileDownload(String fullPath, HttpServletResponse res, String filename) {
		File file = new File(fullPath);
		final int BUFFER_SIZE =4096;
		if(file.exists()) {
			try {
				FileInputStream inStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				res.setContentType(mimeType);
				res.setHeader("content-disposition", "attachment; filename="+filename);
				OutputStream outputStream = res.getOutputStream();
				byte[] bufffer = new byte[BUFFER_SIZE];
				int byteRead = -1;
				while((byteRead=inStream.read(bufffer))!=-1){
					outputStream.write(bufffer,0,byteRead);
				}
				inStream.close();
				outputStream.close();
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
