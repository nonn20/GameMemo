package com.example.controller;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.ImageForm;
import com.example.service.FileUploadService;
 
@Controller
public class SampleController {
	/*@Autowired
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
		
	}*/
	
	private final FileUploadService fileUploadService;
	
	public SampleController(
			FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

    @RequestMapping("/uploadimg")
	public String showCreateContent(@ModelAttribute("Form") ImageForm file,Model model) {
		return "imageUpload";
	}

    @PostMapping("/upload")
	public String createContent(@ModelAttribute("fileUploadForm")  ImageForm file,
			                    Model model) throws Exception{
		
		
		String imageName = null;
		LocalDateTime dateTime = LocalDateTime.now();
		//ファイルが空でない場合に、ファイルをアップロードする
		if(!Objects.isNull(file.getMultipartFile())){
			String s3Path = "gamememo-noon/" +"test";
			file.setCreateAt(dateTime);
			imageName = fileUploadService.fileUpload(file, s3Path);	
		}
		
		return "redirect:/uploadimg";
	}
 
}