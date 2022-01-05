package org.team.gstreet.saleboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.gstreet.saleboard.entity.SaleFavorite;

public interface SaleFavoriteRepository extends JpaRepository<SaleFavorite,Long> {
}
