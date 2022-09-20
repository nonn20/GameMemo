package com.example.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.entity.Tag;

public interface TagRepository extends CrudRepository<Tag,Integer>{
	void deleteByArtid(Integer id);
	
	Integer countByTag(Integer tag);
	
	@Query("select artid from tag t where t.tag= :tagid")
	Iterable<Integer> findArtIdBytagid(@Param("tagid") int tagid);
}
