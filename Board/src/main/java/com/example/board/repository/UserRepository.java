package com.example.board.repository;

import com.example.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


// jpa jpql로 fetch join 추가
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.email = :email")
	User findByEmail(String email);

}
