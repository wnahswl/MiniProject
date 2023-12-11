package edu.pnu.service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.expression.AccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	// 현재 사용자 출력 메소드
	private Member getCurrentMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null; // 현재 인증된 사용자가 없는 경우
		}
		User user = (User) authentication.getPrincipal();
		return memberRepository.findById(user.getUsername()).orElseThrow();
	}

	// 사용자가 ADMIN일 경우
	public String GetRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		// 사용자를 찾지 못할 경우
		if (authentication == null || !authentication.isAuthenticated()) {
			System.out.println("로그인을 해주세요");
		}
		List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
		String rolesToString = authorities.stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(", "));

		if (rolesToString.equals("ROLE_ADMIN")) {
			return rolesToString;
		}

		System.out.println("User" + username + "has roles: " + rolesToString);
		return null;
	}

	// 모든 회원 목록 출력
	public List<Member> getMember() {
		return memberRepository.findAll();
	}

	// 회원가입
	public Member join(MemberDto memberDto) throws AccessException {
		Member member = new Member();
		Optional<Member> optMember = memberRepository.findByUsername(memberDto.getUsername());
		if (optMember.isEmpty()) {
			// 회원가입은 UserId,비밀번호,2차 비밀번호,이름이 있어야함
			if (memberDto.getPassword().equals(memberDto.getCheckPassword())) {
				member.setUsername(memberDto.getUsername());
				member.setPassword(encoder.encode(memberDto.getPassword()));
				member.setNickname(memberDto.getNickname());
				member.setRole(Role.ROLE_USER);
				member.setRegiDate(new Date());
			} else {
				throw new AccessException("패스워드가 일치하지 않습니다");
			}
		} else {
			throw new AccessException("존재하는 회원입니다.");
		}
		return memberRepository.save(member);
	}

	// 회원수정
	public ResponseEntity<?> update(String username, Member updateMember) throws AccessDeniedException {
		
		Member currentMember = getCurrentMember();
		String getRole = GetRole();
		if(currentMember == null) {
			return ResponseEntity.status(401).build();
		}
		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		// null이 아닐시
		if (optionalMember.isPresent()) {
			Member existingMember = optionalMember.get();
			if(existingMember.getUsername().equals(currentMember.getUsername()) ||getRole.equals("ROLE_ADMIN") ) {
			// 아이디 변경은 불가능 이름과 비밀번호만 변경가능
			existingMember.setNickname(updateMember.getNickname());
			existingMember.setPassword(updateMember.getPassword());
			memberRepository.save(existingMember);
			return ResponseEntity.ok("회원정보 수정 성공");
			// null일 경우
			}else {
				throw new AccessDeniedException("사용자 본인 혹은 관리자가 아닙니다. 회원수정 불가능");
			}
		} else {
			throw new MemberNotFoundException("해당하는 회원을 찾을 수 없습니다.");
		}
	}

	// 회원 삭제
	public Member delete(String username) {
		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		// null이 아니면
		if (optionalMember.isPresent()) {
			// 삭제한 멤버가 뭔지 알려줄 객체 생성
			Member existingMember = optionalMember.get();
			memberRepository.delete(existingMember);
			return existingMember;
		} else {
			throw new MemberNotFoundException("해당 회원을 찾을 수 없습니다.");
		}
	}
	
	//권한 수정
	public Member updateRole(String username, Member updateMember) {
		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		
		if(optionalMember.isPresent()) {
			Member updateMemberRole = optionalMember.get();
			updateMemberRole.setRole(updateMember.getRole());
			return memberRepository.save(updateMemberRole);
		}else {
			throw new MemberNotFoundException("권한 수정이 불가능합니다.");
		}
	}

	// 아이디 중복체크
	public boolean checkUsername(String username) {
		return memberRepository.existsByUsername(username);
	}
}
