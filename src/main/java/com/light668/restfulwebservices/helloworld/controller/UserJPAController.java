package com.light668.restfulwebservices.helloworld.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.light668.restfulwebservices.helloworld.model.Post;
import com.light668.restfulwebservices.helloworld.model.User;
import com.light668.restfulwebservices.helloworld.repositories.PostRepository;
import com.light668.restfulwebservices.helloworld.repositories.UserRepository;
import com.light668.restfulwebservices.helloworld.user.UserNotFoundException;

@RestController
public class UserJPAController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@GetMapping(path = "/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping(path = "/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		} else {
			EntityModel<User> model = new EntityModel<>(user.get());
			WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
			model.add(linkTo.withRel("all-users"));
			return model;
		}
	}

	@PostMapping(path = "/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/user_id}")
				.buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/users/{user_id}")
	public void deleteUser(@PathVariable int user_id) {
		Optional<User> user = userRepository.findById(user_id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + user_id);
		} else {
			userRepository.deleteById(user_id);
		}

	}

	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("id-" + id);
		} else {
			return optionalUser.get().getPosts();
		}
	}

	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@Valid @RequestBody Post post, @PathVariable int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("id-" + id);
		}
		User user = optionalUser.get();
		post.setUser(user);
		postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
