package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AnswerBoard;

public interface AnswerRepository extends JpaRepository<AnswerBoard, Integer>{

//	List<AnswerBoard> findByBoardId(int questionId);

}
