package com.light668.restfulwebservices.helloworld.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.light668.restfulwebservices.helloworld.model.Post;

@Service
public class PostDaoService {

	private static List<Post> posts = new ArrayList<>();

	private static int postsCount = 3;

	static {
		posts.add(new Post(1, "text1", new Date()));
		posts.add(new Post(2, "text2", new Date()));
		posts.add(new Post(3, "text3", new Date()));
	}

	public List<Post> findAll() {
		return posts;
	}

	public Post save(Post Post) {
		if (Post.getId() == null) {
			Post.setId(++postsCount);
		}
		posts.add(Post);
		return Post;
	}

	public Post findOne(int id) {
		for (Post Post : posts) {
			if (Post.getId() == id) {
				return Post;
			}
		}
		return null;
	}
}
