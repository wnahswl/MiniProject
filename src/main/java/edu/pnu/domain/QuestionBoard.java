package edu.pnu.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Board")
public class QuestionBoard {
	//기본키로 지정하고 1씩 자동증가
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String title;
	
	//컬럼의 속성을 Text로 정의
	@Column(columnDefinition = "TEXT")
	private String content;
	
	//실제 컬럼명은 create_Date로 변경됨
	private Date createDate;
	
	
	
		//게시글과 댓글은 1:N 관계
	//질문을 삭제하면 답변도 함께 삭제하기 위해 cascadeType.REMOVE 사용
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<AnswerBoard> answerList;
	
//	//게시글과 유저는 N:1
	@ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private Member member;
	

}
