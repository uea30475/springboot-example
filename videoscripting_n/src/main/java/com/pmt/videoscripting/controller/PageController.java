package com.pmt.videoscripting.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmt.videoscripting.models.Media;
import com.pmt.videoscripting.models.MediaHref;
import com.pmt.videoscripting.repository.MediaRepository;
import com.pmt.videoscripting.utils.FileUploadUtil;

@Controller
public class PageController {

	@Autowired
	MediaRepository mediaRepository;

	@RequestMapping(value = { "/", "/index", "/home" })
	public String index(HttpServletRequest req,Model model) throws IOException {
		 	model.addAttribute("userClickHomePage", true);
		 	model.addAttribute("medias",mediaRepository.findAll());
//		    model.addAttribute("course", courseName);
//		    model.addAttribute("mediaHref", mediaHref);
		    return "index";
	}
	
	@RequestMapping(value = { "/watch",  })
	public String index(@RequestParam(name="v",required=true)String code,HttpServletRequest req,Model model) throws IOException {
		    Media media = mediaRepository.findByCode(code);
		    if(media!=null) {
			    String courseName = media.getCourse().getName();
			    String path_folder =req.getServletContext().getRealPath("/videos/"+courseName+"/"+media.getCode()+"/vtt");
			    List<Path> ls = FileUploadUtil.listSourceFiles(Paths.get(path_folder),"*.{vtt}");
			    MediaHref mediaHref = new MediaHref();
			    mediaHref.setCode(media.getCode());
			    for (Path path : ls) {
			    	 mediaHref.addListVttFile(path.getFileName().toString());
				}
			    model.addAttribute("userClickVideoPage", true);
			    model.addAttribute("course", courseName);
			    model.addAttribute("mediaHref", mediaHref);
			    return "index";
		    }
		    
		    return "error";
	}

}
