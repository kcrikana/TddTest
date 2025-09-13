package com.example.board.repository;

import com.example.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

// jpa jpql로 fetch join 추가
public interface BoardRepository extends JpaRepository<Board, Long> {



}
