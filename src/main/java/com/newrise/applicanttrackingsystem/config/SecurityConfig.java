package com.newrise.applicanttrackingsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String[] PUBLIC_URLS = {
			"/", 
			"/user/", "/user/register"
		};
	
	@SuppressWarnings({ "deprecation", "removal" })
	private static final RequestMatcher[] CSRF_IGNORED = new RequestMatcher[] {
//	    new AntPathRequestMatcher("/user/login"),
	    new AntPathRequestMatcher("/user/**")
	};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
    	http.csrf(csrf -> csrf
    		    .ignoringRequestMatchers(CSRF_IGNORED)
    		    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    		);
    	http.authorizeHttpRequests((request) -> request.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated());
    	http.httpBasic(Customizer.withDefaults());
    	http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
    
	@Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    	authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    	return authenticationProvider;
    }
}