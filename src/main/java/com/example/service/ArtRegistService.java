package com.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Tag;
import com.example.entity.TagEntity;
import com.example.entity.TagValuedToukou;
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
	public Toukou toukouRegist(Toukou toukou) {
		/*日時も併せてDBへ登録*/
		/*登録用のToukouインスタンス生成*/
		
		/*現在日時を取得*/
		Date date = new Date();
		SimpleDateFormat format= new SimpleDateFormat("yyyy/MM/dd");
		String registDate = format.format(date);
		toukou.setDate(registDate);
		toukou.setLastupdate(registDate);
		
		return repository.save(toukou);
	}
	
	/*idも取得し、編集（削除→追加）*/
	public Toukou toukouEdit(Toukou toukou) {
		/*以前の記事を削除する*/
		Toukou result = null;
		if(repository.existsById(toukou.getId())) {
			repository.deleteById(toukou.getId());
			tagRepository.deleteByArtid(toukou.getId());
			/*idを初期化*/
			toukou.setId(null);
			/*現在日時を取得し最終更新日を更新*/
			Date lastupdate = new Date();
			SimpleDateFormat format= new SimpleDateFormat("yyyy/MM/dd");
			String registDate = format.format(lastupdate);
			toukou.setLastupdate(registDate);
			result = repository.save(toukou);
		}
		return result;
	}
	
	/*tagテーブルに追加*/
	public int toukouTagRegist(String tag) {
		/*タグを要素ごとに分ける*/
		int nextId = (int)repository.getMaxId()+1;
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
	
	/*最新6件の記事を取得*/
	public TagValuedToukou[] getRecentArt() {
		 TagValuedToukou[] artlist = new TagValuedToukou[6];/*tagメンバがTagEntityの型*/
		 /*最新6件の記事Idを取得*/
		Iterable<Integer> list = repository.getRecentArtId();
		int[] idlist = new int[6];
		int i = 0;
		for(int id:list) {
			idlist[i] = id;
			if(i==5) {
				break;
			}
			i++;
		}
		/*記事Idからそれの記事についたタグを取得*/
		for(int j=0;j<6;j++) {
			Toukou art = repository.findById(idlist[j]).get();/*タグから記事を取得*/
			artlist[j]= new TagValuedToukou();
			artlist[j].tranceFromToukou(art);/*記事の情報をtagの型を変えたentityの配列に格納(移す)*/
			artlist[j].setTag(getTagByArtId(artlist[j].getId()));/*記事Idからそれの記事についたタグを取得*/
		}
		return artlist;
	}
	
	/*指定した記事IDからタグの名前を取ってくる*/
	public Iterable<TagEntity> getTagByArtId(Integer artid){
		Iterable<Integer> tagIdList = tagRepository.findTagByArtid(artid);
		Iterable<TagEntity> tagList = tagERepository.findAllById(tagIdList);
		return tagList;
	}
	
	/*指定したタグの付く記事を取ってくる*/
	public Iterable<Toukou> getToukouBytagid(int tagid){
		Iterable<Integer> artidByArtid = tagRepository.findArtIdBytagid(tagid);
		return repository.findAllById(artidByArtid);
	}
}
