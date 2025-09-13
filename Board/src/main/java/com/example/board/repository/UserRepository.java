package com.example.board.repository;

import com.example.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


// jpa jpql로 fetch join 추가
public interface UserRepository extends JpaRepository<User, Long> {

}
