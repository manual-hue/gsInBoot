package org.team.gstreet.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.gstreet.security.util.Token;
import org.team.gstreet.member.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 토큰 재발급을 위한 컨트롤러
@RequiredArgsConstructor
@RestController
@Log4j2
public class TokenController {
	private final TokenService tokenService;

	@GetMapping("/token/expired")
	public String auth() {
		log.warn("token expired");
		throw new RuntimeException();
	}

	@GetMapping("/token/refresh")
	public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("Refresh");

		if (token != null && tokenService.verifyToken(token)) {
			String email = tokenService.getUid(token);
			Token newToken = tokenService.generateToken(email, "USER");

			response.addHeader("Auth", newToken.getToken());
			response.addHeader("Refresh", newToken.getRefreshToken());
			response.setContentType("application/json;charset=UTF-8");

			return "HAPPY NEW TOKEN";
		}

		throw new RuntimeException();
	}
}
