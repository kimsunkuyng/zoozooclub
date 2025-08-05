package com.zoozooclub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authorize -> authorize
	            // Permit public pages and static resources
	            .requestMatchers("/", "/index", "/login", "/member/login", "/member/signup", "/register").permitAll()
	            .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/plugins/**", "/styles/**", "/board/**").permitAll()

	            // Authenticated routes
	            .requestMatchers("/member/**", "/mypage/**", "/shopping/cart", "/admin/**").authenticated()

	            // Default rule
	            .anyRequest().permitAll()
	        )
	        .oauth2Login(oauth2 -> oauth2
	            .loginPage("/member/login")
	            .defaultSuccessUrl("/member/oauth2login", true)
	        )
	        .formLogin(form -> form
	            .loginPage("/member/login")
	            .loginProcessingUrl("/dummy") 
	            .defaultSuccessUrl("/index", true)
	            .permitAll()
	        )
	       .logout(logout -> logout
	            .logoutUrl("/member/logout")
	            .logoutSuccessUrl("/index")
	            .permitAll()
	        )
	      .csrf(csrf -> csrf.disable());

	    return http.build();
	}

}
