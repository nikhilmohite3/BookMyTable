package com.stackroute.authenticationservice.util;

import com.stackroute.authenticationservice.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Generated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Generated
public class JwtUtil {


    private String secret = "javatechie";

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Object extractRole(String token) {
        Claims claim = extractAllClaims(token);
        return claim.get("Roles");
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //It will generate token based on the Email.
    public String generateToken(String Email, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Roles", role);
        return createToken(claims, Email);
    }

    //It will create token for a particular period of time.
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String Email = extractEmail(token);
        return Email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}