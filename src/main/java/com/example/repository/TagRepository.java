package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Tag;

public interface TagRepository extends CrudRepository<Tag,Integer>{
	void deleteByArtid(Integer id);
	
	Integer countByTag(Integer tag);
}
