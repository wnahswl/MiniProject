package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

//	List<AnswerBoard> findByBoardId(int questionId);

}
