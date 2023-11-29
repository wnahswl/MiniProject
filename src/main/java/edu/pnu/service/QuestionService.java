package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import edu.pnu.domain.QuestionBoard;
import edu.pnu.dto.QuestionBoardDto;
import edu.pnu.persistence.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionBoardRepository questionBoardRepository;
	
	
	//모든 질문 게시판 출력
	public List<QuestionBoard> getList(){
		return questionBoardRepository.findAll();
	}
	
	public QuestionBoard getQuestion(Integer id) {
		Optional<QuestionBoard> question = this.questionBoardRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotfoundException("question not found");
		}
	}
	//게시판 등록
	public Integer createQBoard(QuestionBoardDto questionBoardDto ) {
		return questionBoardRepository.save(questionBoardDto.toEntity()).getId();
	}
	
	//게시판 삭제
	public void deleteQuestionBoard(Integer questionId, String username) {
		Optional<QuestionBoard> optionalQuestion = questionBoardRepository.findById(questionId);
		
		if(optionalQuestion.isPresent()) {
			QuestionBoard question = optionalQuestion.get();
			
			//현재 사용자가 작성자인지 확인
			if(question.getAuthor().getUsername().equals(username)) {
				questionBoardRepository.delete(question);
			}else {
				//작성자가 아닌 경우
				throw new AccessDeniedException("can't delete");
			}
		}else {
			//질문이 없는 경우
			throw new DataNotfoundException("Question not found");
		}
	}
}
