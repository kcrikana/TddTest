package com.example.board.repository;

import com.example.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.hibernate.annotations.Fetch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// jpa jpql로 fetch join 추가
public interface BoardRepository extends JpaRepository<Board, Long> {

	
	// 페이징 처리
	@Query("select b from Board b where b.member.id = :id")
	Page<Board> findByMemberId(Pageable pageable, Long id);

	// 페이징 처리
	@Query("select b from Board b join fetch b.member")
	Page<Board> findAll(Pageable pageable);


	@Query("select b from Board b join fetch b.member")
	List<Board> findAllBoardList();

	@Query("select b from Board b join fetch b.member u where b.id = :id")
	Optional<Board> findOneBoardById(Long id);
}
