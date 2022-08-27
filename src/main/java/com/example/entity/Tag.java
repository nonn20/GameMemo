package com.example.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Tag {
	@Id
	private Integer id;
	private Integer artid;
	private Integer tag;
	
	public Tag(Integer artid,Integer tag) {
		this.artid = artid;
		this.tag = tag;
	}
}
