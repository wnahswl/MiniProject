package edu.pnu.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
import edu.pnu.service.CustomPageRequest;
import lombok.RequiredArgsConstructor;

//URL 프리픽스 고정
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
	
	private final BoardService questionService;
	
	@GetMapping("/list")
	public List<BoardDto> list(){
		return questionService.getList();
	}
	
	@GetMapping(value = "/detail/{id}")
	public BoardDto detail( @PathVariable("id")Integer id) {
		return questionService.getQuestion(id);
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody Board board) {
		questionService.writePost(board);
		return ResponseEntity.ok("create successfully");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable("id") Integer id,@RequestBody BoardDto board) {
		questionService.update(board, id);
		return ResponseEntity.ok("update successfully");
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteQuestionBoard(@PathVariable Integer id){
		questionService.deleteQuestionBoard(id);
		return ResponseEntity.ok("deleted successfully");
	}
	
	@GetMapping("/paging")
	public ResponseEntity<Page<Board>> getwithPaging(
			@RequestParam(defaultValue = "1")int page,
			@RequestParam(defaultValue = "10")int size,
			@RequestParam(defaultValue = "id")String sortBy){
		 CustomPageRequest pageable = new CustomPageRequest(page, size, Sort.by(sortBy));
		Page<Board> boardList = questionService.pagingBoard(pageable);
		return ResponseEntity.ok(boardList);
	}

}
