package edu.pnu.domain;

import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
	
//	@Column(unique = true)
	private String nickname;
	
	private Date regiDate;
	
	//바뀐 폰번을 고려하여 논유니크
	private String phoneNum;
	
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
//	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
//	@JsonIgnore
//	private List<QuestionBoard> questions;
	
	

}
