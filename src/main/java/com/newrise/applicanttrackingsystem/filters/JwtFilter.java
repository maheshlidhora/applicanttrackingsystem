package com.newrise.applicanttrackingsystem.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.newrise.applicanttrackingsystem.servicesimpl.CustomUserDetailsService;
import com.newrise.applicanttrackingsystem.servicesimpl.JWTService;
import com.newrise.applicanttrackingsystem.utils.ColorPrinter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter 
{
    @Autowired
    private JWTService jwtService;
    @Autowired
    private ApplicationContext context;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException 
    {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        // Extracting token from header
        if (authHeader != null && authHeader.startsWith("Bearer ")) 
        {
            token = authHeader.substring(7);
            try 
            {
                username = jwtService.extractUserName(token);
            } 
            catch (Exception e) 
            {
                System.out.println("Failed to extract username from token: " + e.getMessage());
            }
        }
        // Validating and Set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
            CustomUserDetailsService userDetailsService = context.getBean(CustomUserDetailsService.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) 
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                ColorPrinter.printGreen("Authenticated user: ");
                ColorPrinter.printlnPurple(username);
                ColorPrinter.printGreen("Authorities:");
                userDetails.getAuthorities().forEach(authority -> ColorPrinter.printlnPurple(" - " + authority.getAuthority()));
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } 
            else 
            {
                System.out.println("Token validation failed for user: " + username);
            }
        }
        filterChain.doFilter(request, response);
    }
}

