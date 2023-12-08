package edu.pnu.dto;

import java.util.Date;

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
public class BoardDto {
	private Integer id;
	private String title;
	private String content;
	private Date createDate;
	private String username;
	private Integer view;
   public BoardDto(Integer id, String title, String content, String username,Integer view) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.view = view;
    }

}
