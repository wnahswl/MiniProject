package edu.pnu.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.expression.AccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.dto.MemberDto;
import edu.pnu.exception.MemberNotFoundException;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memRepo;
	private final PasswordEncoder encoder;
	  

	// 모든 회원 목록 출력
	public List<Member> getMember() {
		return memRepo.findAll();
	}

	// 회원가입
	public Member join(MemberDto memberDto) throws AccessException {
		Member member = new Member();
		Optional<Member>optMember = memRepo.findByUsername(memberDto.getUsername());
		if(optMember.isEmpty()) {
			if(memberDto.getPassword().equals(memberDto.getCheckPassword())) {
				member.setUsername(memberDto.getUsername());
				member.setPassword(encoder.encode(memberDto.getPassword()));
				member.setNickname(memberDto.getNickname());
				member.setRole(Role.ROLE_USER);
				member.setRegiDate(new Date());
			}else {
				throw new AccessException("패스워드가 일치하지 않습니다");
			}
		}else {
			throw new AccessException("존재하는 회원입니다.");
		}
		return memRepo.save(member);
	}
	
	//회원수정
	public Member update(String username, Member updateMember) {
		//해당 username의 정보를 Optional 타입의 객체에 저장
		Optional<Member> optionalMember = memRepo.findByUsername(username);
		//null이 아닐시
		if (optionalMember.isPresent()) {
			Member existingMember = optionalMember.get();
			
			existingMember.setNickname(updateMember.getNickname());
		
			
			//아이디 변경은 불가능
			existingMember.setNickname(updateMember.getNickname());
			

			return memRepo.save(existingMember);
			//null일 경우
		} else {
			throw new MemberNotFoundException("해당하는 회원을 찾을 수 없습니다.");
		}
	}
	
	//회원 삭제
	public Member delete(String username) {
		 Optional<Member> optionalMember= memRepo.findByUsername(username);
		 //null이 아니면
		 if(optionalMember.isPresent()) {
			 
			 //삭제한 멤버가 뭔지 알려줄 객체 생성
			 Member existingMember = optionalMember.get();
			 memRepo.delete(existingMember);
			 return existingMember;
		 }else {
			 throw new MemberNotFoundException("해당 회원을 찾을 수 없습니다.");
		 }
	}
	
	//아이디 중복체크
	public boolean checkUsername(String username) {
		return memRepo.existsByUsername(username);
	}
}
