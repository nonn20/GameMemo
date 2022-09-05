package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Toukou;
import com.example.repository.ToukouRepository;

@Controller
public class HomeController {
	@Autowired
	ToukouRepository repository;
	
	@RequestMapping("home")
	public String home(Model model) {
		model.addAttribute("artList", repository.findAll());
		return "home";
	}
	
	@GetMapping("art")
	public String art(Model model,@RequestParam int id) {
		Toukou art = null;
		if(repository.findById(id).isPresent()) {
			art = repository.findById(id).get();
		}
		model.addAttribute("artContent",art);
		return "art";
	}
	
}
