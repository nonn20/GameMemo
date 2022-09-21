package com.example.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TagValuedToukou {
	@Id
	private Integer id;
	private String maintitle;
	private Iterable<TagEntity> tag;
	private String text;
	private String lastupdate;
	private String date;
	
	public void tranceFromToukou(Toukou toukou) {
		this.id = toukou.getId();
		this.maintitle = toukou.getMaintitle();
		this.text = toukou.getText();
		this.lastupdate = toukou.getLastupdate();
		this.date = toukou.getDate();
	}

}
