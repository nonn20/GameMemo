package com.example;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.form.AppConfig;

@SpringBootApplication
public class GameMemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameMemoApplication.class, args);
	}
	
	@Bean//@Beanアノテーションを付けたメソッドを定義しておくことでSpring Frameworkがオブジェクトを登録して後で使える
	public AppConfig appConfig() {
		
		// 起動時のディレクトリをAppConfigのimageDirフィールドに保持しておく
		File imageDir = new File("images");
		imageDir = imageDir.getAbsoluteFile();
		
		// imagesフォルダがなかったら作成する
		if(!imageDir.exists()) {
			imageDir.mkdir();
		}
		AppConfig appConfig = new AppConfig();
		appConfig.setImageDir(imageDir);
		return appConfig;
	}
}
