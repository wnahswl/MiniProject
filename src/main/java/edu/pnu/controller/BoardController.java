package edu.pnu.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.dto.BoardDto;
import edu.pnu.service.BoardService;
import lombok.RequiredArgsConstructor;

//url question 생략
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
	
	private final BoardService questionService;
	
//	@GetMapping("/list")
//	public List<QuestionBoard> list() {
//		return questionService.getList();
//	}
	
	@GetMapping("/list")
	public List<BoardDto> list(){
		return questionService.getList();
	}
	
	@GetMapping(value = "/detail/{id}")
	public BoardDto detail( @PathVariable("id")Integer id) {
		return questionService.getQuestion(id);
	}
	
	
	@PostMapping("/create")
	public void create(@RequestBody Board board) {
		System.out.println("board"+board);
		questionService.writePost(board);
	}
	
	@PutMapping("/update/{id}")
	public void update(@PathVariable("id") Integer id,@RequestBody BoardDto board) {
		questionService.update(board, id);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteQuestionBoard(@PathVariable Integer id){
		questionService.deleteQuestionBoard(id);
		return ResponseEntity.ok("deleted successfully");
	}
	
	@GetMapping("/paging")
	public ResponseEntity<Page<Board>> getwithPaging(
			@RequestParam(defaultValue = "1")int page,
			@RequestParam(defaultValue = "10")int size,
			@RequestParam(defaultValue = "id")String sortBy){
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<Board> boardList = questionService.pagingBoard(pageable);
		return ResponseEntity.ok(boardList);
	}
	
	@GetMapping("/user")
	public Object getUser() {
		return questionService.GetRole();
	}
	
	
	

}
