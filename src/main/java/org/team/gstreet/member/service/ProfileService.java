package org.team.gstreet.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team.gstreet.member.dto.ResponseDTO;
import org.team.gstreet.member.dto.UsersDTO;
import org.team.gstreet.member.entity.UsersEntity;
import org.team.gstreet.member.repository.UsersRepository;

import javax.transaction.Transactional;

@Transactional
public interface ProfileService {

	String register(UsersDTO usersDTO);

	UsersDTO getProfile(String id);

}
