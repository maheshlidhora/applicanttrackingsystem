package com.newrise.applicanttrackingsystem.servicesimpl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.entities.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService 
{
	private String secretKey = "";
	
	public JWTService() {
		try 
		{
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
	}

	public String generateToken(Users users) 
	{
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", users.getEmail());
	    Set<Roles> roles = users.getRoles();
	    if (roles != null) {
	        claims.put("roles", roles.stream()
	            .map(Roles::getRoleName)
	            .collect(Collectors.toList()));
	    } else {
	        claims.put("roles", new ArrayList<>());
	    }
	    
	    return Jwts.builder()
	    	    .claims(claims)
	    	    .subject(users.getEmail())
	    	    .issuedAt(new Date(System.currentTimeMillis()))
	    	    .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
	    	    .signWith(getKey())
	    	    .compact();
	}

	private Key getKey() 
	{
		byte [] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) 
	{
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) 
    {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) 
    {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) 
    {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) 
    {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) 
    {
        return extractClaim(token, Claims::getExpiration);
    }

}
