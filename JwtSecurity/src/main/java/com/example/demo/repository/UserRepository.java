package com.example.demo.repository;

import java.util.Optional;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>  {

	public Optional<User> findByEmail(String email);
}