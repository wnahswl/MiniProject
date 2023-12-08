package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.pnu.config.auth.JWTAuthenticationFilter;
import edu.pnu.config.auth.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	
	private final MemberRepository memberRepository;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	//유저의 패스워드 암호화
	@Bean
	 BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티 필터 설정
	@Bean
	  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth->auth
				.requestMatchers(new AntPathRequestMatcher("/comment/delete")).authenticated()
				.anyRequest().permitAll());
		
		http.cors(cors->cors.configurationSource(corsConfigurationSource()));
		http.csrf(csrf->csrf.disable());
		
	
		
		http.formLogin(frmLogin->frmLogin.disable());
		http.httpBasic(basic->basic.disable());
		
		
		http.sessionManagement(ssmn->ssmn.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		//스프링 시큐리티가 등록한 필터체인의 뒤에 작성한 필터를 추가한다.
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepository), AuthorizationFilter.class);
		
		
		return http.build();
	}
	@Bean
	 CorsConfigurationSource corsConfigurationSource() {
		//URL패턴을 기반으로 한 Cors 구성 제공
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//Cors 구성 객체 생성
		CorsConfiguration config = new CorsConfiguration();
		// 특정 도메인 또는 IP 허용하도록 설정
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedOrigin("http://10.125.121.218:3000");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		
		config.addExposedHeader("Authorization");
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		
		// CorsFilter 생성자가 UrlBasedCorsConfigurationSource를 받도록 변경
		return source;
	}
	
	
	
}
