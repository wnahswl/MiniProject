package edu.pnu.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "User")
public class Member {
	
	
	@Id
	private String username;
	
	@Nonnull
	private String password;
	
//	@Column(unique = true)
	private String nickname;
	
	private Date regiDate;
	
	//바뀐 폰번을 고려하여 논유니크
	private String phoneNum;
	
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
//양방향 매핑
	//EAGER로 설정해서 회원정보를 가져올 때 등록한 게시물도 같이 조회
	@OneToMany(mappedBy = "member",fetch = FetchType.EAGER)
	private List<QuestionBoard> boardList = new ArrayList<QuestionBoard>();
	


}
