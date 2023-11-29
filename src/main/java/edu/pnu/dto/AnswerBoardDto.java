package edu.pnu.dto;

import java.util.Date;

import edu.pnu.domain.AnswerBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerBoardDto {
	private String content;
	private Date createDate;
	
	@Builder
	public AnswerBoardDto(String content) {
		this.content = content;
	}
	
	public AnswerBoard toEntity() {
		return AnswerBoard.builder()
				.content(content)
				.createDate(new Date())
				.build();
	}
	

}
