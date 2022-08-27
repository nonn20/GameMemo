package com.example.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Toukou {
	@Id
	private Integer id;
	private String maintitle;
	private String tag;
	private String text;
	private String date;
}
