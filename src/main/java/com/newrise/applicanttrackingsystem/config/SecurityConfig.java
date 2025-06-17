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
<<<<<<< HEAD
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
=======
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig 
{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilet;
	
	private static final String[] PUBLIC_URLS = {
			"/", 
			"/user/", "/user/register", "/user/login", "/user/generateOtp", "/user/verifyOtp"
	};
	
	@SuppressWarnings({ "deprecation", "removal" })
	private static final RequestMatcher[] CSRF_IGNORED = new RequestMatcher[] {
	    new AntPathRequestMatcher("/user/register"),
	    new AntPathRequestMatcher("/user/login"),
	    new AntPathRequestMatcher("/user/addRole"),
	    new AntPathRequestMatcher("/user/deleteRole/{id}"),
	    new AntPathRequestMatcher("/user/updateRole/{id}"),
	    new AntPathRequestMatcher("/user/generateOtp"),
	    new AntPathRequestMatcher("/user/verifyOtp")
	};
>>>>>>> d7282b574a48ffe8b4885e02b06454390eb87a14

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