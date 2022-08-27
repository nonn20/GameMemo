package com.example.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TagEntity {
	@Id
	private Integer id;
	private String name;
}
