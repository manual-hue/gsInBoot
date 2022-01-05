package org.team.gstreet.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.team.gstreet.member.dto.UserDto;
import org.team.gstreet.member.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

//토큰 검증용
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
	private final TokenService tokenService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String token = ((HttpServletRequest)request).getHeader("Auth");

		if (token != null && tokenService.verifyToken(token)) {
			String email = tokenService.getUid(token);


			//아직 db연동 안함
			UserDto userDto = UserDto.builder()
					.email(email)
					.name("이름")
					.picture("프로필").build();

			Authentication auth = getAuthentication(userDto);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		chain.doFilter(request, response);
	}

	public Authentication getAuthentication(UserDto member) {
		return new UsernamePasswordAuthenticationToken(member, "",
		                                               Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
