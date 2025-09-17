package com.example.board.repository;

import com.example.board.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


// jpa jpql로 fetch join 추가
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("select m from Member m where m.email = :email")
	Optional<Member> findByEmail(String email);

}
