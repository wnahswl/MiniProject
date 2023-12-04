package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.AnswerBoard;
import edu.pnu.dto.AnswerBoardDto;
import edu.pnu.service.AnswerService;
import lombok.RequiredArgsConstructor;

//URL 프리픽스 고정
@RequestMapping("/answer")
@RequiredArgsConstructor
@RestController
public class AnswerController {
	
	private final AnswerService answerService;
	
	//RequestParam으로 답변으로 입력한 내용을 얻음
//	@PostMapping("/create/{id}")
//	public String createAnswer(@PathVariable("id") Integer id, 
//			@RequestParam String content) {
//				return String.format("redirect:/question/detail/%s", id);
//	}
	
	@GetMapping("/list")
	public List<AnswerBoard> list(){
		return answerService.getList();
	}
	
	@PostMapping("/create/{id}/answer")
	public void createAnswer( @PathVariable Integer id,@RequestBody AnswerBoardDto dto){
		answerService.addComment(id, dto);
		
	}
	
	

}
