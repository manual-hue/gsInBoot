package org.team.gstreet.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.team.gstreet.member.entity.UsersEntity;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,String> {
	UsersEntity findByEmail(String email);

	Boolean existsByEmail(String email);

	UsersEntity findByEmailAndPassword(String email, String password);

	@Query("select m from UsersEntity m where m.email = :email")
	Optional<UsersEntity> getEmailEager(String email);
}