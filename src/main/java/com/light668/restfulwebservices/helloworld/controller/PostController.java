package com.light668.restfulwebservices.helloworld.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.light668.restfulwebservices.helloworld.model.Post;
import com.light668.restfulwebservices.helloworld.services.PostDaoService;
import com.light668.restfulwebservices.helloworld.user.PostNotFoundException;
import com.light668.restfulwebservices.helloworld.user.UserNotFoundException;

@RestController
public class PostController {

	@Autowired
	PostDaoService postService;
	
	@GetMapping("/users/{user_id}/posts")
	public List<Post> retrieveAllPosts(){
		return postService.findAll();
	}
	
	@GetMapping("/users/{user_id}/posts/{post_id}")
	public Post retrievePostByUser(@PathVariable int post_id) {
		Post foundPost = postService.findOne(post_id);
		if (foundPost ==null)	
			throw new PostNotFoundException("id-"+post_id);
		return foundPost;
	}
	
	@PostMapping("/users/{user_id}/posts")
	public ResponseEntity<Object> createPost(@RequestBody Post post) {
		Post savedPost = postService.save(post);
		URI location =ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{/post_id}")
				.buildAndExpand(savedPost.getId()).toUri();
			
			return ResponseEntity.created(location).build();
	}
}
