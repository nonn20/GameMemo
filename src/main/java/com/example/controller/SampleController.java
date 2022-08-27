package com.example.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.form.AppConfig;
 
@Controller
public class SampleController {
	@Autowired
	private AppConfig appConfig;
	
	@PostMapping("/upload")
	public String upload(@RequestParam MultipartFile file, @RequestParam("name") String fileName,Model model) {
		if(file.isEmpty()) {
			model.addAttribute("error", "ファイルを指定してください");
			return "index";
		}
		File dest = new File(appConfig.getImageDir(),fileName+".jpg");
		try {
			file.transferTo(dest); //表示される修正候補の「try/catchで囲む」を選択
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return "home";
		
	}
 
}