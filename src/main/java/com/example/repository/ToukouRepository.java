package com.example.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.entity.Toukou;

public interface ToukouRepository extends CrudRepository<Toukou,Integer>{
	@Query("select id from toukou order by id desc")
	Iterable<Integer> getRecentArtId();
	
	@Query("select max(id) from toukou")
	Integer getMaxId();
}
