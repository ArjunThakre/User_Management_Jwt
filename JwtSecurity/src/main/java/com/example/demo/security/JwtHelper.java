package com.example.demo.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	//requirement : 5 hour * 60 min 8 60 sec
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	  SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generates a secure key

	//private String secret="abxhshhhsjhshdsfyfgdfgdfdfdsnjknjbjbvvhmvnvbbcvbcvbcbcvbcnnbmcgfcgffdfdfd";
	
	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T  getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	//for retrieveing any information from token we will need the secret key
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(token).parseClaimsJws(token).getBody();
				
//				.parser().setSigningKey(token).parseClaimsJws(token).getBody();
	}
	
	//check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
	
	
	//generate Token For user
	public String generateToken(UserDetails userdetails) {
		Map<String,Object> claims= new HashMap<>();
		return doGenerateToken(claims, userdetails.getUsername());
	}
	
	 //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims,String subject) {
		
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
				//.signWith(secret,SignatureAlgorithm.HS512).compact();
	}
	
	//validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
}
