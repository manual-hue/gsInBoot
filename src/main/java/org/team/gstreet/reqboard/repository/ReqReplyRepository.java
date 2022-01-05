package org.team.gstreet.reqboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.team.gstreet.reqboard.entity.ReqReply;

public interface ReqReplyRepository extends JpaRepository<ReqReply, Long> {

    @Query("select r from ReqReply r where r.reqBoard.bno = :bno")
    Page<ReqReply> getListByBno(Long bno, Pageable pageable);

    @Query("select count(r) from ReqReply r where r.reqBoard.bno = :bno")
    int getReplyCountOfBoard(Long bno);
}

