package edu.pnu.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.dto.QuestionBoardDto;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionBoardRepository questionBoardRepository;
	private final MemberRepository memberRepository;

	private Member getCurrentMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null; // 현재 인증된 사용자가 없는 경우
		}

		User user = (User) authentication.getPrincipal();
		return memberRepository.findById(user.getUsername()).orElseThrow();

	}

	// 모든 질문 게시판 출력
	public List<QuestionBoard> getList() {
		return questionBoardRepository.findAll();
	}

	public QuestionBoard getQuestion(Integer id) {
		Optional<QuestionBoard> question = this.questionBoardRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotfoundException("question not found");
		}
	}

	// 게시판등록
	public ResponseEntity<?> writePost(QuestionBoardDto boardDto) {
		// 현재 사용자의 정보를 가져와서 QuestionBoard에 설정
		Member currentMember = getCurrentMember();
		if (currentMember == null) {
			// 현재 사용자가 인증되지 않았을 경우
			return ResponseEntity.status(401).build();
		}

		QuestionBoard board = new QuestionBoard();
		board.setTitle(boardDto.getTitle());
		board.setContent(boardDto.getContent());
		board.setCreateDate(new Date());

		// 사용자 정보를 QuestionBoard에 설정
		board.setMember(currentMember);

		questionBoardRepository.save(board);

		return ResponseEntity.ok().build();
	}

	// 게시판 삭제
	public void deleteQuestionBoard(Integer questionId) {
		Optional<QuestionBoard> optionalQuestion = questionBoardRepository.findById(questionId);
		Member currentMember = getCurrentMember();
		if (optionalQuestion.isPresent()) {
			QuestionBoard question = optionalQuestion.get();

			// 현재 사용자가 작성자인지 확인
			if (question.getMember().getUsername().equals(currentMember)) {
				questionBoardRepository.delete(question);
			} else {
				// 작성자가 아닌 경우
				throw new AccessDeniedException("can't delete");
			}
		} else {
			// 질문이 없는 경우
			throw new DataNotfoundException("Question not found");
		}
	}
}
