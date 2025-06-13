package com.newrise.applicanttrackingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final String[] PUBLIC_URLS = { "/", "/index.html", "/user/logPage", "/user/doLogin",
			"/submitLogForm", "/user/regPage", "/user/doRegistration", "/submitRegForm", "/failed", "/savepost",
			"/getpost/{id}", "/updatepost/{id}", "/getAllpost", "/deletepost/{id}","/hrcreated" };

	@SuppressWarnings({ "deprecation", "removal" })
	private static final RequestMatcher[] CSRF_IGNORED = new RequestMatcher[] {
//	    new AntPathRequestMatcher("/public/**"),
//	    new AntPathRequestMatcher("/login"),
			new AntPathRequestMatcher("/user/**"), new AntPathRequestMatcher("/savepost"),
			new AntPathRequestMatcher("/getpost/{id}"), new AntPathRequestMatcher("/updatepost/{id}"),
			new AntPathRequestMatcher("/getAllpost"), new AntPathRequestMatcher("/deletepost/{id}") , new AntPathRequestMatcher("/hrcreated")};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.ignoringRequestMatchers(CSRF_IGNORED)
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

		http.authorizeHttpRequests(
				(request) -> request.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated());
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}