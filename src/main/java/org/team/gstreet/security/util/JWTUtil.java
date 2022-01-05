package org.team.gstreet.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
	private final static String secretKey = "helloworld111112222233333333344444444444555555555";

	private SecretKey key;

	public JWTUtil(){
		key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String content){


		long timeAmount = 10;

		String jws = Jwts.builder()
				.setSubject(content)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(timeAmount).toInstant()))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();


		return jws;
	}

	public void validateToken(String token)throws JwtException {
		Jws<Claims> jws = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);


	}
}
