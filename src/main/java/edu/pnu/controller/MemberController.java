package edu.pnu.controller;

import java.util.List;

import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.MemberDto;
import edu.pnu.exception.MemberNotFoundException;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	
	public final MemberService memberService;
	
	//모든 회원 정보 출력
	@GetMapping("/user/list")
	public List<Member> list(){
		return memberService.getMember();
	}
	
	//회원가입
	@PostMapping("/user/join")
	public ResponseEntity<?> join(@RequestBody MemberDto memberDto) throws AccessException {
		memberService.join(memberDto);
		return ResponseEntity.ok().build();
	}
	
	//회원정보 수정
	 @PutMapping("/user/update/{username}")
    public ResponseEntity<String> updateMember(@PathVariable String username, @RequestBody Member updatedMember) {
        try {
            Member updated = memberService.update(username, updatedMember);
            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 회원을 찾을 수 없습니다.");
        }	
    }
	 
	 //회원정보 삭제
	 @DeleteMapping("/user/delete/{username}")
	 public ResponseEntity<String> deleteMember(@PathVariable String username){
		 try {
			 Member deleted = memberService.delete(username);
			 return ResponseEntity.ok("회원 정보가 성공적으로 삭제되었습니다.");
		 }catch(MemberNotFoundException e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 회원을 찾을 수 없습니다.");
		 }
	 }
	 
	 //username 중복체크
	 @GetMapping("/user/{username}/check")
	 public ResponseEntity<Boolean> checkUsername(@PathVariable String username){
		 return ResponseEntity.ok(memberService.checkUsername(username));
	 }
	 
	 @GetMapping("/auth")
	 public void getAuth(@AuthenticationPrincipal User user) {
		 System.out.println("User :" + user);
	 }
}
