package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AnswerBoard;

public interface AnswerRepository extends JpaRepository<AnswerBoard, Integer>{

}
