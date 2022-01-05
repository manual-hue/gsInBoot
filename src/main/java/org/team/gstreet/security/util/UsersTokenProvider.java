package org.team.gstreet.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.team.gstreet.member.entity.UsersEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Log4j2
@Service
public class UsersTokenProvider {
	private static final String SECRET_KEY = "gstreetrandomsecretkeyaoakaokaaokaoakokamdddmdmdmaslsl1231124123123123123123123123123123";

	public String create(UsersEntity usersEntity) {
		Date expiryDate = Date.from(
				Instant.now()
				.plus(1, ChronoUnit.DAYS));
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512,SECRET_KEY)
				.setSubject(usersEntity.getId())
				.setIssuer("gstreet app")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.compact();
	}

	public String validateAndGetUserId(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}
}
