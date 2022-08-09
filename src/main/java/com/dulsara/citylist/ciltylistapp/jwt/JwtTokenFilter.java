package com.dulsara.citylist.ciltylistapp.jwt;

import com.dulsara.citylist.ciltylistapp.exception.ForbiddenException;
import com.dulsara.citylist.ciltylistapp.user.model.Role;
import com.dulsara.citylist.ciltylistapp.user.model.User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor

public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtUtil;



	@Override
	protected void doFilterInternal(HttpServletRequest request, 
				HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			if (!hasAuthorizationBearer(request)) {
				filterChain.doFilter(request, response);
				return;
			}
			String token = getAccessToken(request);

			if (!jwtUtil.validateAccessToken(token)) {
				filterChain.doFilter(request, response);
				return;
			}
			try {
				setAuthenticationContext(token, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			filterChain.doFilter(request, response);


		}
	}

	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}

		return true;
	}

	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}

	private void setAuthenticationContext(String token, HttpServletRequest request) throws ForbiddenException {
		UserDetails userDetails = getUserDetails(token);

		try {
			UsernamePasswordAuthenticationToken
					authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			authentication.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e){
			throw new ForbiddenException("User is not granted to execute Operation ");
		}


	}

	private UserDetails getUserDetails(String token) {
		User userDetails = new User();
		Claims claims = jwtUtil.parseClaims(token);
		String subject = (String) claims.get(Claims.SUBJECT);
		String roles = (String) claims.get("roles");

		roles = roles.replace("[", "").replace("]", "");
		String[] roleNames = roles.split(",");
		
		for (String aRoleName : roleNames) {
			userDetails.addRole(new Role(aRoleName));
		}
		
		String[] jwtSubject = subject.split(",");

		userDetails.setId(Long.parseLong(jwtSubject[0]));
		userDetails.setUserName(jwtSubject[1]);

		return userDetails;
	}
}
