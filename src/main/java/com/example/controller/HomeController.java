 package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Toukou;
import com.example.repository.ToukouRepository;
import com.example.service.ArtRegistService;
import com.example.service.IndexService;

@Controller
public class HomeController {
	@Autowired
	ToukouRepository repository;
	
	@Autowired
	IndexService indexService;
	
	@Autowired
	ArtRegistService artRegistService;
	
	@RequestMapping("home")
	public String home(Model model) {
		model.addAttribute("artList", artRegistService.getRecentArt());
		model.addAttribute("tagIndex", indexService.getTagIndex());
		return "home";
	}
	
	@GetMapping("art")
	public String art(Model model,@RequestParam int id) {
		Toukou art = null;
		if(repository.findById(id).isPresent()) {
			art = repository.findById(id).get();
			model.addAttribute("tagList", artRegistService.getTagByArtId(id));
		}
		model.addAttribute("artContent",art);
		return "art";
	}
	
	@RequestMapping("search/{tagid}")
	public String selectFunction(Model model,@PathVariable int tagid) {
		model.addAttribute("artList", artRegistService.getToukouBytagid(tagid));
		return "search";
	}
	
	@RequestMapping("test")
	public String asd() {
		return "test";
	}
	
}
