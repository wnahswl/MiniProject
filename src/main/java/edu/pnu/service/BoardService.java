package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.dto.BoardDto;
import edu.pnu.exception.DataNotfoundException;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardRepository questionBoardRepository;
	private final MemberRepository memberRepository;

	// 현재 사용자 출력 메소드
	private Member getCurrentMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null; // 현재 인증된 사용자가 없는 경우
		}
		User user = (User) authentication.getPrincipal();
		return memberRepository.findById(user.getUsername()).orElseThrow();
	}
	
	//사용자가 ADMIN일 경우
	public String GetRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		if(authentication == null || !authentication.isAuthenticated()) {
			System.out.println("로그인을 해주세요");
		}
		List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
		String rolesToString = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(", "));
		
		if(rolesToString.equals("ROLE_ADMIN")) {
			return rolesToString;
		}
		
		System.out.println("User" + username + "has roles: " + rolesToString);
		return null;
	}

	// 모든 게시판 출력
	public List<BoardDto> getList() {
		List<Board> questionBoards = questionBoardRepository.findAll();
		List<BoardDto> questionBoardDtos = new ArrayList<>();

		for (Board questionBoard : questionBoards) {
			String memberUsername = questionBoard.getMember().getUsername();
			BoardDto dto = new BoardDto(questionBoard.getId(), questionBoard.getTitle(),
					questionBoard.getContent(), questionBoard.getCreateDate(), memberUsername,questionBoard.getView());
			questionBoardDtos.add(dto);
		}

		return questionBoardDtos;
	}
    //특정 게시판 출력
	public BoardDto getQuestion(Integer id) {
		Optional<Board> findBoard = this.questionBoardRepository.findById(id);
		if (findBoard.isPresent()) {
			Board board = findBoard.get();
			BoardDto dto = new BoardDto(board.getId(),board.getTitle(),board.getContent(),
					board.getCreateDate(),board.getMember().getUsername(),board.getView()+1);
			board.setView(dto.getView());
			questionBoardRepository.save(board);
			return dto;
		} else {
			throw new DataNotfoundException("question not found");
		}
	}

	// 게시판등록
	public ResponseEntity<?> writePost(@RequestBody Board boardDto) {
		// 현재 사용자의 정보를 가져와서 QuestionBoard에 설정
		Member currentMember = getCurrentMember();
		if (currentMember == null) {
			// 현재 사용자가 인증되지 않았을 경우
			return ResponseEntity.status(401).build();
		}

		Board board = new Board();
		board.setTitle(boardDto.getTitle());
		System.out.println("dsadsasadsadsadad" + board.getTitle());
		board.setContent(boardDto.getContent());
		board.setCreateDate(new Date());
		//조회수 초기값을 1로 수정
		board.setView((board.getView() == null ? 0 : board.getView()) + 1);
		// 사용자 정보를 QuestionBoard에 설정
		board.setMember(currentMember);

		questionBoardRepository.save(board);

		return ResponseEntity.ok().build();
	}
	
	//게시판 수정
	public ResponseEntity<String> update(BoardDto boardDto,Integer id){
		
		Member currentMember = getCurrentMember();
		String getRole = GetRole();
		if(currentMember == null) {
			return ResponseEntity.status(401).build();
		}
		Optional<Board> updatedBoard = questionBoardRepository.findById(id);
		Board board = updatedBoard.get();
		//수정가능한건 본인 혹은 ROLE_ADMIN만 가능
		if(board.getMember().getUsername().equals(currentMember.getUsername())||getRole.equals("ROLE_ADMIN") ) {
		board.setTitle(boardDto.getTitle());
		board.setContent(boardDto.getContent());
		questionBoardRepository.save(board);
		return ResponseEntity.ok("업데이트 성공");
		}else {
			throw new AccessDeniedException("작성자 혹은 관리자가 아닙니다.");
		}
	}

	// 게시판 삭제
	public void deleteQuestionBoard(Integer questionId) {
		Optional<Board> optionalQuestion = questionBoardRepository.findById(questionId);
		Member currentMember = getCurrentMember();
		String getRole = GetRole();
		if (optionalQuestion.isPresent()) {
			Board question = optionalQuestion.get();

			// 현재 사용자가 작성자 혹은 ROLE_ADMIN인지 확인
			if (question.getMember().getUsername().equals(currentMember.getUsername())|| getRole.equals("ROLE_ADMIN")) {
				questionBoardRepository.delete(question);
			} else {
				// 작성자가 아닌 경우
				throw new AccessDeniedException("작성자가 아닙니다. 권한 없음, 삭제 불가능.");
			}
		} else {
			// 질문이 없는 경우
			throw new DataNotfoundException("해당 게시글을 찾을 수 없습니다.");
		}
	}
	
	public Page<Board> pagingBoard(Pageable pageable){
		return questionBoardRepository.findAll(pageable);
	}
}
