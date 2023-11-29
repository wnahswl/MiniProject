package edu.pnu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	

	
	@Bean
	 CorsFilter corsFilter() {
		//URL패턴을 기반으로 한 Cors 구성 제공
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//Cors 구성 객체 생성
		CorsConfiguration config = new CorsConfiguration();
		
		// 특정 도메인 또는 IP 허용하도록 설정
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addAllowedMethod(CorsConfiguration.ALL);
		config.addExposedHeader("Authorization");
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		
		// CorsFilter 생성자가 UrlBasedCorsConfigurationSource를 받도록 변경
		return new CorsFilter(source);
	}

}
