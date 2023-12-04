package edu.pnu.dto;

import java.util.Date;

import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBoardDto {
	private int id;
	private String title;
	private String content;
	private Date createDate;
	private Member member;
	
public QuestionBoardDto(QuestionBoard entity) {
	this.id = entity.getId();
	this.title = entity.getTitle();
	this.content = entity.getContent();
	this.createDate = entity.getCreateDate();
	this.member = entity.getMember();
}

}
