package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.TagEntity;
import com.example.repository.TagEntityRepository;

@Service
public class TagRegistService {
	
	@Autowired
	TagEntityRepository tagERepository;
	
	public boolean existSameTag(String name) {
		if(!tagERepository.existsByName(name)) {
			TagEntity tagE = new TagEntity();
			tagE.setName(name);
			tagERepository.save(tagE);
			return true;
		}
		return false;
	}
}
