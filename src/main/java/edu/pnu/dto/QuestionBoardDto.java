package edu.pnu.dto;

import java.util.Date;

import edu.pnu.domain.QuestionBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionBoardDto {
	private String title;
	private String content;
	private Date createDate;
	
	@Builder
	public QuestionBoardDto(String title,String content) {
		this.title = title;
		this.content = content;
	}
	
	public QuestionBoard toEntity() {
		return QuestionBoard.builder()
				.QTitle(title)
				.Qcontent(content)
				.createDate(new Date())
				.build();
	}

}
