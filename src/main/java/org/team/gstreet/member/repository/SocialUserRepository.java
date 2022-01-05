package org.team.gstreet.member.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.team.gstreet.member.entity.SocialUser;

import java.util.Optional;

public interface SocialUserRepository extends CrudRepository<SocialUser, Long> {
	Optional<SocialUser> findByEmail(String email);

}
