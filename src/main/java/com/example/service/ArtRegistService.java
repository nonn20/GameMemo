package com.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Tag;
import com.example.entity.Toukou;
import com.example.repository.TagEntityRepository;
import com.example.repository.TagRepository;
import com.example.repository.ToukouRepository;

@Service
public class ArtRegistService {
	@Autowired
	ToukouRepository repository;
	
	@Autowired
	TagEntityRepository tagERepository;
	
	@Autowired
	TagRepository tagRepository;
	
	/*toukouテーブルに追加*/
	public Toukou toukouRegist(String mainTitle,String tag,String text) {
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
		
		return repository.save(toukou);
	}
	
	/*idも取得し、編集（削除→追加）*/
	public Toukou toukouEdit(Integer id,String mainTitle,String tag,String text) {
		/*以前の記事を削除する*/
		if(repository.existsById(id)) {
			repository.deleteById(id);
		}
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
		
		return repository.save(toukou);
	}
	
	/*tagテーブルに追加*/
	public int toukouTagRegist(String tag) {
		/*タグを要素ごとに分ける*/
		int nextId = (int)repository.count()+1;
		Integer artNum;
		for(int i=0;i<tag.length()-1;) {
			/*最後の:より後ろに来た場合終了*/
			if(tag.substring(i).indexOf(':')==-1) {
				break;
			}
			else {
				artNum = Integer.parseInt(tag.substring(i,tag.substring(i).indexOf(':')+i));
				
				/*指定したタグが存在しなかった場合中断する*/
				if(!tagERepository.existsById(artNum)) {
					return artNum;
				}
				/*Tagインスタンスを生成*/
				Tag tagEntity = new Tag((Integer)nextId,artNum);
				/*DB保存*/
				tagRepository.save(tagEntity);
				i+=tag.substring(i).indexOf(':')+1;
			}
		}
		return 0;
	}
}
