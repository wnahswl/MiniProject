package edu.pnu;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.domain.Board;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.BoardRepository;

@SpringBootTest
class SubProjectApplicationTests {
	
	@Autowired
	BoardRepository bRepo;
	
	@Autowired
	MemberRepository mRepo;
	
	
	
	@Autowired
	PasswordEncoder encoder;

//	@Test
	void testBoard() {
		Board q1 = new Board();
		q1.setTitle("Q 제목");
		q1.setContent("Q 내용");
		q1.setCreateDate(new Date());
		bRepo.save(q1);	
	}
//	@Test
	void testMember(){
		mRepo.save(Member.builder()
				.username("admin")
				.password(encoder.encode("abcd"))
				.nickname("admin")
				.regiDate(new Date())
				.role(Role.ROLE_ADMIN)
				.build());
	}
//	@Test
	void test() {
		mRepo.save(Member.builder()
				.username("test")
				.password(encoder.encode("test"))
				.nickname("test")
				.regiDate(new Date())
				.role(Role.ROLE_USER)
				.build());
	}

//	@Test
	void testMembers()
	{
		for(int i=1;i<=50;i++) {
		mRepo.save(Member.builder()
				.username("test" +i)
				.password(encoder.encode("test" +i))
				.nickname("test"+i)
				.regiDate(new Date())
				.role(Role.ROLE_USER)
				.build());
		}
	}
//		@Test
		void testBoard1()
		{
			for(int i=1;i<=10;i++) {
				bRepo.save(Board.builder()
						.title("Test"+i)
						.content("Test"+i)
						.createDate(new Date())
						.build());
			}
		}
	
}