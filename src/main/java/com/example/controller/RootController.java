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
import com.example.entity.Toukou;
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
	
	@ModelAttribute
	Toukou setUpToukou() {
		return new Toukou();
	}
	
	/*記事登録*/
	@RequestMapping("toukouPage")
	public String toukou(@ModelAttribute("caution") String caution) {
		return "toukou";
	}
	/*記事登録処理*/
	@PostMapping("toukou")
	public String toukou(Model model,
					@ModelAttribute("toukou") Toukou form) {
		
		int errorNum = artRegistService.toukouTagRegist(form.getTag());
		if(errorNum != 0) {
			model.addAttribute("caution", "指定したタグは存在しません: "+errorNum);
			return "toukou";
		}
		else {
			artRegistService.toukouRegist(form);
		}
		return "redirect:rootPage";
	}
	/*記事削除編集ページ*/
	@RequestMapping("toukouEditPage")
	public String toukouEdit(Model model) {
		model.addAttribute("artList", repository.findAll());
		return "toukouEdit";
	}
	/*記事編集ページ*/
	@GetMapping("editArtPage")
	public String editArtPage(Model model,@RequestParam int id,@RequestParam String caution) {
		model.addAttribute("toukou", repository.findById(id).get());
		model.addAttribute("caution", caution);
		
		return "editArt";
	}
	/*記事編集処理ページ*/
	@PostMapping("editArt")
	public String editArt(@ModelAttribute("toukou") Toukou form, 
						  RedirectAttributes redirectAttributes,
						  Model model) {
		/*タグ確認*/
		int errorNum = artRegistService.toukouTagRegist(form.getTag());
		if(errorNum != 0) {
			model.addAttribute("caution", "指定したタグは存在しません: "+errorNum);
			return "editArt";
		}
		/*記事削除、登録*/
		else {
			if(null==artRegistService.toukouEdit(form)) {
				redirectAttributes.addAttribute("caution", "エラーが発生しました");
				redirectAttributes.addAttribute("id", form.getId());
				return "redirect:editArtPage";
			}
		}
		return "toukouEdit";
	}
	/*記事削除処理*/
	@GetMapping("removeArt")
	public String removeArt(@RequestParam Integer id) {
		repository.deleteById(id);
		return "redirect:toukouEditPage";
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
