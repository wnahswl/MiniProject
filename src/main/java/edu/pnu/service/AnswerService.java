package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.dto.AnswerBoardDto;
import edu.pnu.persistence.AnswerRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;
	private final QuestionBoardRepository boardRepository;
	private final MemberRepository memberRepository;

	private Member getCurrentMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null; // 현재 인증된 사용자가 없는 경우
		}

		User user = (User) authentication.getPrincipal();
		return memberRepository.findById(user.getUsername()).orElseThrow();

	}

	// 모든 답변 댓글 출력
	public List<AnswerBoard> getList() {
		return answerRepository.findAll();
	}

	public ResponseEntity<?> addComment(Integer questionId, AnswerBoardDto dto) {
		AnswerBoard comment = new AnswerBoard();
		comment.setContent(dto.getContent());
		//게시판 번호로 게시글 찾기
		Optional<QuestionBoard> optBoard = boardRepository.findById(questionId);
		QuestionBoard board = optBoard.orElseThrow();
			
		comment.setQuestion(board);
		comment.setMember(getCurrentMember());
		answerRepository.save(comment);
		return ResponseEntity.ok().build();
	}

}
