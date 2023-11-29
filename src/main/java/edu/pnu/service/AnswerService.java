package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.AnswerBoard;
import edu.pnu.dto.AnswerBoardDto;
import edu.pnu.persistence.AnswerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public Integer create(AnswerBoardDto answerBoardDto) {
		return answerRepository.save(answerBoardDto.toEntity()).getId();
	}
	
	//모든 답변 댓글 출력
	public List<AnswerBoard> getList(){
		return answerRepository.findAll();
	}

}
