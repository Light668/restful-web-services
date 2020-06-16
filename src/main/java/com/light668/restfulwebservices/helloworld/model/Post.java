package com.light668.restfulwebservices.helloworld.model;

import java.util.Date;

public class Post {
	
	private Integer id;
	
	private String text;
	
	private Date creationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Post(Integer id, String text, Date creationDate) {
		super();
		this.id = id;
		this.text = text;
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", text=" + text + ", creationDate=" + creationDate + "]";
	}
	
}
