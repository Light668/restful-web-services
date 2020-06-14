package com.light668.restfulwebservices.helloworld.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.light668.restfulwebservices.helloworld.model.User;

import javassist.NotFoundException;

@Service
public class UserDaoService {

	private static List<User> users = new ArrayList<>();

	private static int usersCount = 3;

	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
	}

	// publicList<User> findAll();
	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}

	public User findOne(int id) throws NotFoundException {
			for (User user : users) {
				if (user.getId() == id) {
					return user;
				}
			}
			throw new NotFoundException("User  with id: " + id + " not found.");
	}
}
