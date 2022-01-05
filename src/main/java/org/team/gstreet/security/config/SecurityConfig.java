package org.team.gstreet.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.team.gstreet.security.filter.JwtAuthFilter;
import org.team.gstreet.security.util.UsersJwtAuthenticationFilter;
import org.team.gstreet.member.service.TokenService;


@RequiredArgsConstructor
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	private final CustomOAuth2UserService oAuth2UserService;
//	private final OAuth2SuccessHandler    successHandler;
	private final TokenService            tokenService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UsersJwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
				.formLogin().loginPage("/customLogin").loginProcessingUrl("/login")
				.and()
				.csrf().disable();
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and()
//				.authorizeRequests()
//				.antMatchers("/index","/customLogin","/","/auth/**","/token/**").permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.oauth2Login();
//				.successHandler(successHandler);
//				.userInfoEndpoint().userService(oAuth2UserService);

		http.logout();
		http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	}
}