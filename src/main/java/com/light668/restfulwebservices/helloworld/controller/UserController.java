package com.light668.restfulwebservices.helloworld.controller;

import java.net.URI;
import java.util.List;

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

import com.light668.restfulwebservices.helloworld.model.User;
import com.light668.restfulwebservices.helloworld.services.UserDaoService;
import com.light668.restfulwebservices.helloworld.user.UserNotFoundException;

@RestController
public class UserController {

	@Autowired
	UserDaoService userService;

	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers() {
		return userService.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = userService.findOne(id);
		if (user == null)
			throw new UserNotFoundException("id-" + id);
		EntityModel<User> model = new EntityModel<>(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkTo.withRel("all-users"));
		return model;
	}

	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/user_id}")
				.buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{user_id}")
	public void deleteUser(@PathVariable int user_id) {
		User user = userService.deleteById(user_id);

		if (user == null)
			throw new UserNotFoundException("id-" + user_id);
	}
}
