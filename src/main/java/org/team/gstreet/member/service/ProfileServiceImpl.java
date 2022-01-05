package org.team.gstreet.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.team.gstreet.member.dto.UsersDTO;
import org.team.gstreet.member.entity.UsersEntity;
import org.team.gstreet.member.repository.UsersRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

	private final ModelMapper modelMapper;
	private final UsersRepository usersRepository;

	@Override
	public String register(UsersDTO usersDTO) {
		UsersEntity usersEntity = modelMapper.map(usersDTO, UsersEntity.class);

		usersRepository.save(usersEntity);

		return usersEntity.getId();
	}

	@Override
	public UsersDTO getProfile(String id) {
		Optional<UsersEntity> result = usersRepository.findById(id);

		if(result.isEmpty()){
			throw new RuntimeException("not found");
		}
		return modelMapper.map(result.get(),UsersDTO.class);
	}
}
