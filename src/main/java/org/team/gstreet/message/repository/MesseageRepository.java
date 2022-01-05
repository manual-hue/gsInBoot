package org.team.gstreet.message.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.gstreet.message.entity.Message;
import org.team.gstreet.saleboard.entity.SaleBoard;

import java.util.List;


public interface MesseageRepository extends JpaRepository<Message,Long> {

   @Query("select m from Message m where m.receive = :receive")
   Page<Message> getListSelect(@Param("receive") String mid, Pageable pageable);


}
