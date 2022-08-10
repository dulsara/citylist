package com.dulsara.citylist.ciltylistapp.security.jwt;

import com.dulsara.citylist.ciltylistapp.user.model.User;
import com.dulsara.citylist.ciltylistapp.util.GlobalConstant;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
	

	private String SECRET_KEY="secret";
	
	public String generateAccessToken(User user) {
		
		return Jwts.builder()
				.setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
				.setIssuer("CodeDula")
				.claim("auth", user.getAuthorities())
				.claim("roles", user.getRoles().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
	public boolean validateAccessToken(String token) throws Exception {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT expired", ex.getMessage());
			throw new Exception(GlobalConstant.JWTErrors.JWT_EXPIRED_ERROR);

		} catch (IllegalArgumentException ex) {
			LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
			throw new Exception(GlobalConstant.JWTErrors.JWT_TOKEN_EMPTY_ERROR);

		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT is invalid", ex);
			throw new Exception(GlobalConstant.JWTErrors.JWT_TOKEN_INVALID_ERROR);

		} catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT is not supported", ex);
			throw new Exception(GlobalConstant.JWTErrors.JWT_TOKEN_NOT_SUPPORTED_ERROR);

		} catch (SignatureException ex) {
			LOGGER.error("Signature validation failed");
			throw new Exception(GlobalConstant.JWTErrors.JWT_SIGNATURE_ERROR);

		}
	}
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
}
