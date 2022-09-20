package com.example.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.TagEntity;
import com.example.repository.TagEntityRepository;
import com.example.repository.TagRepository;

@Service
public class IndexService {
	@Autowired
	TagEntityRepository tagERepository;
	
	@Autowired
	TagRepository tagRepository;
	
	public Map<Integer[],String> getTagIndex() {
		Iterable<TagEntity> tagList = tagERepository.findAll();	
		Map<Integer[],String> tagValue = new LinkedHashMap<>();/*integerの0がtagId,1が記事の数、Stringがタグの名前*/
		for(TagEntity tag: tagList) {
			Integer[] tagParam = new Integer[2];
			tagParam[1] = tagRepository.countByTag(tag.getId());
			tagParam[0] = tag.getId();
			tagValue.put(tagParam, tag.getName());
		}
		return tagValue;
	}
	
	
}
