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
	
	
	//본명
//	@Column(unique = true)
	private String nickname;
	
	private Date regiDate;

	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//양방향 매핑
	//EAGER로 설정해서 회원정보를 가져올 때 등록한 게시물도 같이 조회
	@OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
	private List<Board> boardList = new ArrayList<Board>();
	


}
