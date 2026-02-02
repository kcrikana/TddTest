package com.example.board.repository;

import com.example.board.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// 페이징 처리 -> 아직 사용 안함
	@Query("select c from Comment c where c.board.id = :id")
	Page<Comment> findByMemberId(Pageable pageable, Long id);

	// 페이징 처리 -> 아직 사용 안함
	@Query("select c from Comment c join fetch c.board")
	Page<Comment> findAllCommentListPage(Pageable pageable);

	List<Comment> findByBoardIdOrderByCreatedAtAsc(Long boardId);


	@Query("select c from Comment c join fetch c.board where c.id = :id")
	Optional<Comment> findOneCommentById(@Param("id") Long id);

}
