package org.team.gstreet.member.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.team.gstreet.member.dto.ResponseDTO;
import org.team.gstreet.member.dto.UsersDTO;
import org.team.gstreet.member.entity.UsersEntity;
import org.team.gstreet.member.service.UsersService;
import org.team.gstreet.security.util.UsersTokenProvider;

@Log4j2
@RestController
@RequestMapping("/auth")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private UsersTokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@ModelAttribute UsersDTO usersDTO) {
		try {
			UsersEntity user = UsersEntity.builder()
					.email(usersDTO.getEmail())
					.username(usersDTO.getUsername())
					.password(passwordEncoder.encode(usersDTO.getPassword()))
					.nickname(usersDTO.getNickname())
					.build();

			UsersEntity registeredUser = usersService.create(user);
			UsersDTO responseUserDTO = UsersDTO.builder()
					.email(registeredUser.getEmail())
					.id(registeredUser.getId())
					.username(registeredUser.getUsername())
					.nickname(registeredUser.getNickname())
					.build();
			log.info("singup json? : " + ResponseEntity.ok().body(responseUserDTO));
			return ResponseEntity.ok().body(responseUserDTO);
		} catch (Exception e){
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@ModelAttribute UsersDTO usersDTO) {
		UsersEntity user = usersService.getByCredentials(
				usersDTO.getEmail(),
				usersDTO.getPassword(), passwordEncoder);


		if(user!=null) {
			final String token = tokenProvider.create(user);
			final UsersDTO responseUserDTO = UsersDTO.builder()
					.email(user.getEmail())
					.username(user.getUsername())
					.id(user.getId())
					.nickname(user.getNickname())
					.token(token)
					.build();
			log.info("singin json? : " + ResponseEntity.ok().body(responseUserDTO));
			return ResponseEntity.ok().body(responseUserDTO);

		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("Login Failed..")
					.build();

			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
	}

}
