package com.example.board.repository;

import com.example.board.domain.Member;
import com.example.board.domain.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


// jpa jpql로 fetch join 추가
public interface MemberRepository extends JpaRepository<Member, Long> {


	Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
	Optional<Member> findByRefreshToken(String refreshToken);

}
