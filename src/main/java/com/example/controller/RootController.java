package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.TagEntity;
import com.example.repository.TagEntityRepository;
import com.example.repository.TagRepository;
import com.example.repository.ToukouRepository;
import com.example.service.ArtRegistService;
import com.example.service.TagRegistService;

@Controller
public class RootController {
	
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TagEntityRepository tagERepository;
	
	@Autowired
	ToukouRepository repository;
	
	@Autowired
	ArtRegistService artRegistService;
	
	@Autowired
	TagRegistService tagRegistService;
	
	/*記事登録*/
	@RequestMapping("toukouPage")
	public String toukou(@ModelAttribute("caution") String caution) {
		return "toukou";
	}
	
	/*記事登録処理*/
	@PostMapping("toukou")
	public String toukou(Model model,
					@RequestParam String mainTitle,
					@RequestParam String tag,
					@RequestParam String text,
					RedirectAttributes redirectAttributes) {
		
		int errorNum = artRegistService.toukouTagRegist(tag);
		if(errorNum != 0) {
			redirectAttributes.addAttribute("caution", "指定したタグは存在しません: "+errorNum);
			return "redirect:toukouPage";
		}
		else {
			artRegistService.toukouRegist(mainTitle,tag,text);
		}
		return "redirect:rootPage";
	}
	
	/*タグ管理ページ*/
	@RequestMapping("configTag")
	public String registTag(Model model,@ModelAttribute("caution") String caution) {
		Iterable<TagEntity> allTags = tagERepository.findAll();
		model.addAttribute("allTags",allTags);
		return "configTag";
	}
	/*タグ追加*/
	@PostMapping("addTag")
	public String addTag(@RequestParam String name,RedirectAttributes redirectAttributes) {
		
		/*追加しようとしているタグの名前かぶりがないか確認*/
		if(!tagRegistService.existSameTag(name)) {
			redirectAttributes.addAttribute("caution","そのタグはすでに登録されています。");
			return "redirect:configTag";
		}
		redirectAttributes.addAttribute("caution","タグを追加しました");
		return "redirect:configTag";
	}
	
	/*タグ消去*/
	@GetMapping("removeTag")
	public String removeTag(@RequestParam int id,RedirectAttributes redirectAttributes) {
		if(tagERepository.existsById(id)) {
			tagERepository.deleteById(id);
			redirectAttributes.addAttribute("caution", "指定したタグを削除しました");
		}
		else {
			redirectAttributes.addAttribute("caution","エラー:指定したタグが見つかりません");
		}
		
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
