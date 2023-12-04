package edu.pnu.dto;

import java.util.Date;

import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerBoardDto {
	private long id;
	private String content;
	private Date createDate;
	private Member writer;

	public AnswerBoardDto(AnswerBoard answer) {
		this.id = answer.getId();
		this.content = answer.getContent();
		this.createDate = answer.getCreateDate();
		this.writer = answer.getMember();
	}

	

}
