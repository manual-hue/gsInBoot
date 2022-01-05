package org.team.gstreet.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.team.gstreet.member.dto.UserDto;
import org.team.gstreet.member.mapper.UserRequestMapper;
import org.team.gstreet.security.util.Token;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	private final TokenService      tokenService;
	private final UserRequestMapper userRequestMapper;
	private final ObjectMapper      objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		UserDto userDto = userRequestMapper.toDto(oAuth2User);

		Token token = tokenService.generateToken(userDto.getEmail(), "USER");
		log.info("{}", token);

		writeTokenResponse(response, token);
	}

	private void writeTokenResponse(HttpServletResponse response, Token token)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");

		response.addHeader("Auth", token.getToken());
		response.addHeader("Refresh", token.getRefreshToken());
		response.setContentType("application/json;charset=UTF-8");

		var writer = response.getWriter();
		writer.println(objectMapper.writeValueAsString(token));
		writer.flush();
	}
}
