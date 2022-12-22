package com.stackroute.authenticationservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stackroute.authenticationservice.service.CustomUserDetailsService;
import com.stackroute.authenticationservice.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Generated
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	JwtUtil jwtutil;
	@Autowired
	CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader=request.getHeader("Authorization");
		String token=null;
		String email=null;
		if(authorizationHeader!=null&& authorizationHeader.startsWith("Bearer "))
		{
			token=authorizationHeader.substring(7);
			try {
				email = jwtutil.extractEmail(token);
			}
			catch(IllegalArgumentException exception)
			{
				log.error("Unable to get JWT token");
			}
			catch (ExpiredJwtException exception)
			{
				log.error("JWT token is expired");
			}
		}
		if(email!=null&& SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=userDetailsService.loadUserByUsername(email);
			if(jwtutil.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			{
				log.error("Token is expired");
			}
		}
		filterChain.doFilter(request, response);
	}
}
