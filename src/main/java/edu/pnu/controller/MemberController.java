package edu.pnu.controller;

import java.nio.file.AccessDeniedException;

import java.util.List;

import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.MemberDto;
import edu.pnu.exception.MemberNotFoundException;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;
//URL 프리픽스 고정
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class MemberController {
	
	public final MemberService memberService;
	
	//모든 회원 정보 출력
	@GetMapping("/list")
	public List<Member> list(){
		return memberService.getMember();
	}
	
	//회원가입
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody MemberDto memberDto) throws AccessException {
		memberService.join(memberDto);
		return ResponseEntity.ok("회원가입 성공");
	}
	
	//회원정보 수정
	 @PutMapping("/update/{username}")
    public ResponseEntity<String> updateMember(@PathVariable String username, @RequestBody Member updatedMember) throws AccessDeniedException {
        try {
            memberService.update(username, updatedMember);
            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 회원을 찾을 수 없습니다.");
        }	
    }
	 
	 //회원정보 삭제
	 @DeleteMapping("/delete/{username}")
	 public ResponseEntity<String> deleteMember(@PathVariable String username){
		 try {
			  memberService.delete(username);
			 return ResponseEntity.ok("회원 정보가 성공적으로 삭제되었습니다.");
		 }catch(MemberNotFoundException e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 회원을 찾을 수 없습니다.");
		 }
	 }
	 
	 //username 중복체크
	 @GetMapping("/{username}/check")
	 public ResponseEntity<Boolean> checkUsername(@PathVariable String username){
		 return ResponseEntity.ok(memberService.checkUsername(username));
	 }
	 
	 //권한수정
	 @PutMapping("/updateRole/{username}")
	 public ResponseEntity<String> updateRoleMember(@PathVariable String username, @RequestBody Member updateMember){
		 try {
			 memberService.updateRole(username, updateMember);
			 return ResponseEntity.ok("권한 수정이 완료되었습니다.");
		 }catch(MemberNotFoundException e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 회원을 찾을 수 없습니다.");
		 }
	 }
	 
}
