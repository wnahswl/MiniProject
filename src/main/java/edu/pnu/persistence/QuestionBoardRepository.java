package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.QuestionBoard;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Integer>{

}
