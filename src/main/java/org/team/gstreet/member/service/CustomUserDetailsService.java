package org.team.gstreet.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team.gstreet.member.dto.MemberDTO;
import org.team.gstreet.member.dto.UsersDTO;
import org.team.gstreet.member.entity.Member;
import org.team.gstreet.member.entity.UsersEntity;
import org.team.gstreet.member.repository.MemberRepository;
import org.team.gstreet.member.repository.UsersRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UsersRepository usersRepository;


	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		log.info("-----------------------------");
		log.info("------loadUserByEmail------" + email);

		Optional<UsersEntity> optionalUsersEntity = usersRepository.getEmailEager(email);

		UsersEntity member = optionalUsersEntity.orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

		log.info("Member: " + member);

		UsersDTO usersDTO = UsersDTO.builder()
				.id(member.getId())
				.password(member.getPassword())
				.username(member.getUsername())
				.nickname(member.getNickname())
				.email(member.getEmail())
				//.roles(member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toSet()))
				.build();

		log.info(usersDTO);

		log.info("-------------------------------------------------");
		log.info("-------------------------------------------------");

		return usersDTO;
	}
}
