package org.team.gstreet.member.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.team.gstreet.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
	@EntityGraph(attributePaths = "roleSet")
	@Query("select m from Member m where m.mid = :mid")
	Optional<Member> getMemberEager(String mid);
}
