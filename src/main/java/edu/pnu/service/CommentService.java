package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.domain.Comment;
import edu.pnu.domain.Member;
import edu.pnu.dto.CommentDto;
import edu.pnu.exception.DataNotfoundException;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.CommentRepository;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository answerRepository;
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;

	// 현재 사용자 정보 불러오기
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

	public List<CommentDto> getList() {
		List<Comment> answerBoards = answerRepository.findAll();
		// dto List 생성
		List<CommentDto> answerBoardDtos = new ArrayList<>();

		for (Comment answerBoard : answerBoards) {
			String memberUsername = answerBoard.getMember().getUsername();
			CommentDto dto = new CommentDto(answerBoard.getId(), answerBoard.getContent(), answerBoard.getCreateDate(),
					memberUsername);
			answerBoardDtos.add(dto);
		}
		return answerBoardDtos;
	}

	// boardId의 댓글만 출력
	public List<CommentDto> getComment(Integer boardId) {
		Optional<Board> optBoard = boardRepository.findById(boardId);
		if (optBoard.isPresent()) {
			Board board = optBoard.get();
			List<Comment> commentList = board.getAnswerList();
			List<CommentDto> commentListDto = new ArrayList<>();

			for (Comment comment : commentList) {
				CommentDto commentDto = new CommentDto();
				commentDto.setId(comment.getId());
				commentDto.setContent(comment.getContent());
				commentDto.setUsername(comment.getMember().getUsername());
				commentDto.setCreateDate(comment.getCreateDate());
				commentListDto.add(commentDto);
			}
			return commentListDto;
		} else {
			throw new DataNotfoundException("해당 게시글을 찾을 수 없습니다.");
		}
	}

	// 댓글 생성
	public ResponseEntity<?> addComment(Integer boardId, CommentDto dto) {
		Member currentMember = getCurrentMember();
		if(currentMember == null) {
			return ResponseEntity.status(401).build();
		}
		Comment comment = new Comment();
		//게시글 작성엔 내용만 들어가야함
		comment.setContent(dto.getContent());
		// 게시판 번호로 게시글 찾기
		Optional<Board> optBoard = boardRepository.findById(boardId);
		Board board = optBoard.orElseThrow();
		comment.setCreateDate(new Date());
		comment.setQuestion(board);
		comment.setMember(getCurrentMember());
		answerRepository.save(comment);
		return ResponseEntity.ok().build();
	}

	// 댓글 수정
	public ResponseEntity<?> updateComment(Integer id, CommentDto dto) {
		Member currentMember = getCurrentMember();
		String getRole = GetRole();
		Optional<Comment> optComment = answerRepository.findById(id);
		Comment comment = optComment.get();
		// 사용자가 작성자 본인 혹은 ROLE_ADMIN인지 확인
		if (comment.getMember().getUsername().equals(currentMember.getUsername()) || getRole.equals("ROLE_ADMIN")) {
			//수정할 내용 입력
			comment.setContent(dto.getContent());
			answerRepository.save(comment);
			return ResponseEntity.ok().build();
		} else {
			throw new AccessDeniedException("작성자 혹은 관리자가 아닙니다. 댓글 수정 불가능");
		}
	}

	// 댓글삭제
	public void deleteComment(Integer commentId) {
		Optional<Comment> optAnswer = answerRepository.findById(commentId);
		Member currentMember = getCurrentMember();
		String getRole = GetRole();
		if (optAnswer.isPresent()) {
			Comment answer = optAnswer.get();
			// 사용자가 작성자 본인 혹은 ROLE_ADMIN인지 확인
			if (answer.getMember().getUsername().equals(currentMember.getUsername()) || getRole.equals("ROLE_ADMIN")) {
				// 같을 시 삭제
				answerRepository.delete(answer);
			} else {
				throw new AccessDeniedException("작성자 본인이 아닙니다. 삭제 불가능");
			}
		} else {
			// 댓글이 없는 경우
			throw new DataNotfoundException("해당 댓글을 찾을 수 없습니다.");
		}
	}

}