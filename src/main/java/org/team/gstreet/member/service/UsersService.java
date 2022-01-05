package org.team.gstreet.member.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team.gstreet.member.entity.UsersEntity;
import org.team.gstreet.member.repository.UsersRepository;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	private final ModelMapper modelMapper;

	public UsersEntity create(final UsersEntity usersEntity) {
		if(usersEntity == null || usersEntity.getEmail() == null) {
			throw new RuntimeException("Invalid arguments");
		}

		final String email = usersEntity.getEmail();
		if(usersRepository.existsByEmail(email)) {
			log.warn("존재하는 이메일" + email);
			throw new RuntimeException("존재하는 이메일");
		}

		return usersRepository.save(usersEntity);
	}

	public UsersEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
		final UsersEntity originalUser = usersRepository.findByEmail(email);

		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}

	public long getUsersCount() {
		long result = usersRepository.count();
		return result;
	}





}
