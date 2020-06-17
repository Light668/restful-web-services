package com.light668.restfulwebservices.helloworld.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 2, message = "Name should have at least two characters.")
	@ApiModelProperty(notes = "Name should have at least two characters.")
	private String name;
	
	@Past
	@ApiModelProperty(notes = "Birth should be in the past.")
	private Date birthDate;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public User(Integer id, @Size(min = 2, message = "Name should have at least two characters.") String name,
			@Past Date birthDate, List<Post> posts) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.posts = posts;
	}

	public User() {
	
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", posts=" + posts + "]";
	}

}
