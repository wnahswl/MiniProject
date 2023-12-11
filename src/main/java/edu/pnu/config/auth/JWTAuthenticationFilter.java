package edu.pnu.config.auth;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// 인증 객체
	private final AuthenticationManager authenticationManager;

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		ObjectMapper mapper = new ObjectMapper();
		Member member = null;
		try {
			System.out.println("인증시도");
			member = mapper.readValue(request.getInputStream(), Member.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("로그인 faild");
		}

		Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

		Authentication auth = authenticationManager.authenticate(authToken);
		System.out.println("auth11:" + auth);
		return auth;
	}

	// 인증이 성공했을 때 실행되는 후처리 메소드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		User user = (User) authResult.getPrincipal();
		String token = JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 360))
				.withClaim("username", user.getUsername()).sign(Algorithm.HMAC256("edu.pnu.jwt"));
		
		//클라이언트에게 토큰전송
		response.addHeader("Authorization", "Bearer " + token);
	}

}
