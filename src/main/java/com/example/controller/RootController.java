package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Tag;
import com.example.entity.TagEntity;
import com.example.entity.Toukou;
import com.example.repository.TagEntityRepository;
import com.example.repository.TagRepository;
import com.example.repository.ToukouRepository;

@Controller
public class RootController {
	@Autowired
	ToukouRepository repository;
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TagEntityRepository tagERepository;
	
	/*記事登録*/
	@RequestMapping("toukouPage")
	public String toukou() {
		return "toukou";
	}
	
	/*記事登録処理*/
	@PostMapping("toukou")
	public String toukou(Model model,
					@RequestParam String mainTitle,
					@RequestParam String tag,
					@RequestParam String text) {
		/*日時も併せてDBへ登録*/
		/*登録用のToukouインスタンス生成*/
		Toukou toukou = new Toukou();
		toukou.setMaintitle(mainTitle);
		toukou.setTag(tag);
		toukou.setText(text);
		
		/*現在日時を取得*/
		Date date = new Date();
		SimpleDateFormat format= new SimpleDateFormat("yyyy/MM/dd");
		String registDate = format.format(date);
		toukou.setDate(registDate);
		
		int nextId = (int)repository.count()+1;
		Integer artNum;
		for(int i=0;i<tag.length()-1;) {
			
			if(tag.substring(i).indexOf(':')==-1) {
				break;
			}
			else {
				artNum = Integer.parseInt(tag.substring(i,tag.substring(i).indexOf(':')+i));
				i+=tag.substring(i).indexOf(':')+1;
				/*Tagインスタンスを生成*/
				Tag tagEntity = new Tag((Integer)nextId,artNum);
				/*DB保存*/
				tagRepository.save(tagEntity);
			}
		}
		
		repository.save(toukou);
		
		return "redirect:rootPage";
	}
	/*タグ管理ページ*/
	@RequestMapping("configTag")
	public String registTag(Model model) {
		Iterable<TagEntity> allTags = tagERepository.findAll();
		model.addAttribute("allTags",allTags);
		return "configTag";
	}
	/*タグ追加*/
	@PostMapping("addTag")
	public String addTag(@RequestParam String name) {
		TagEntity tagE = new TagEntity();
		tagE.setName(name);
		tagERepository.save(tagE);
		/*かぶりのあった場合の処理*/
		return "redirect:configTag";
	}
	
	/*管理者ホーム*/
	@RequestMapping("rootPage")
	public String root() {
		return "root";
	}
	/*画像登録*/
	@RequestMapping("imageToukou")
	public String imageToukou() {
		return "upload";
	}
}
