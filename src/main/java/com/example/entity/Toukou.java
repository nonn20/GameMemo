package com.example.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Toukou {
	@Id
	private Integer id;
	private String maintitle;
	private String tag;
	private String text;
	private String lastupdate;
	private String date;
	private String samuneimg;
}
