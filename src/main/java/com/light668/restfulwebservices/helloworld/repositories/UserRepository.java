package com.light668.restfulwebservices.helloworld.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.light668.restfulwebservices.helloworld.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
