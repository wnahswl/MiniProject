package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

	Optional<Member> findByUsername(String username);
	
	
	boolean existsByUsername(String username);

}
