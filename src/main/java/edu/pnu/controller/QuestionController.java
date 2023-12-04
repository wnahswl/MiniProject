package edu.pnu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.QuestionBoard;
import edu.pnu.dto.QuestionBoardDto;
import edu.pnu.service.QuestionService;
import lombok.RequiredArgsConstructor;

//url question 생략
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
	public List<QuestionBoard> list() {
		return questionService.getList();
	}
	
	@GetMapping(value = "/detail/{id}")
	public QuestionBoard detail( @PathVariable("id")Integer id) {
		return questionService.getQuestion(id);
	}
	
	
	@PostMapping("/create")
	public void create(@RequestBody QuestionBoardDto board) {
		 questionService.writePost(board);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteQuestionBoard(@PathVariable Integer id){
		questionService.deleteQuestionBoard(id);
		return ResponseEntity.ok("deleted successfully");
	}
	
	
	

}
