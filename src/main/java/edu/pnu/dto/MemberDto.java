package edu.pnu.dto;

import edu.pnu.domain.Board;
import edu.pnu.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	private String username;
	private String password;
	private String checkPassword;
	private String nickname;
	private Role role;
	private Board board;

}
