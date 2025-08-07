package com.newrise.applicanttrackingsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.newrise.applicanttrackingsystem.filters.JwtFilter;

@Configuration
@EnableWebSecurity
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
		new AntPathRequestMatcher("/user/"),
	   
	    new AntPathRequestMatcher("/user/login"),
	    new AntPathRequestMatcher("/user/logout"),
	    
	    new AntPathRequestMatcher("/user/generateOtp"),
	    new AntPathRequestMatcher("/user/verifyOtp"),
	    new AntPathRequestMatcher("/user/enableUser"),
	    new AntPathRequestMatcher("/user/disableUser"),
	    
	    new AntPathRequestMatcher("/user/register"),
	    new AntPathRequestMatcher("/user/findUser"),
//	    new AntPathRequestMatcher("/user/allUsersPaginated/?{page}&{size}"),
	    new AntPathRequestMatcher("/user/allUsersPaginated"),
	    new AntPathRequestMatcher("/user/allUsersPaginated/**"),
	    new AntPathRequestMatcher("/user/allUsers"),
	    new AntPathRequestMatcher("/user/deleteUser"),
	    
	    new AntPathRequestMatcher("/user/addRole"),
	    new AntPathRequestMatcher("/user/getRoleByName"),
	    new AntPathRequestMatcher("/user/updateRole/{id}"),
	    new AntPathRequestMatcher("/user/deleteRole/{id}"),
	    
//	    ------------------------- To HR -------------------------	    
	    new AntPathRequestMatcher("/user/createJob"),
//	    new AntPathRequestMatcher("/user/getallJobsPaginatedToHR/?{page}&{size}"),
	    new AntPathRequestMatcher("/user/getallJobsToHR"),
	    new AntPathRequestMatcher("/user/getallJobsToHR/**"),
	    new AntPathRequestMatcher("/user/getJobDetails/{jobId}"),
	    new AntPathRequestMatcher("/user/doUpdateInJob/{jobId}"),
	    new AntPathRequestMatcher("/user/deleteJob/{id}"),
	    new AntPathRequestMatcher("/interview/scheduleInterview"),
	    
//	    ------------------------- To Candidate -------------------------
//	    new AntPathRequestMatcher("/user/candidate/getallJobs/?{page}&{size}"),
	    new AntPathRequestMatcher("/user/candidate/getallJobs"),
	    new AntPathRequestMatcher("/user/candidate/getallJobs/**"),
	    new AntPathRequestMatcher("/user/candidate/applyForJob/{jobId}"),
//	    new AntPathRequestMatcher("/user/candidate/findAppliedApplications/?{page}&{size}"),
	    new AntPathRequestMatcher("/user/candidate/findAppliedApplications"),
	    new AntPathRequestMatcher("/user/candidate/findAppliedApplications/**"),
	    new AntPathRequestMatcher("/user/candidate/withdrawApplication/{applicationId}"),
	    
//	    new AntPathRequestMatcher("/user/**")				//For All "/user/________"
	};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
    	http.csrf(csrf -> csrf
    		    .ignoringRequestMatchers(CSRF_IGNORED)
    		    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    		);
    	http.authorizeHttpRequests((request) -> request
    			.requestMatchers(PUBLIC_URLS)
    			.permitAll()
    			.anyRequest()
    			.authenticated());
    	// REMOVE httpBasic — it conflicts with JWT filter
        http.httpBasic(Customizer.withDefaults());
    	http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	http.addFilterBefore(jwtFilet, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
	@Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    	authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    	return authenticationProvider;
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception 
	{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}