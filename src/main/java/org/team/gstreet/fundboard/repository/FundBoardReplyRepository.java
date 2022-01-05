package org.team.gstreet.fundboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.team.gstreet.fundboard.entity.FundBoardReply;

import java.util.List;

public interface FundBoardReplyRepository extends JpaRepository<FundBoardReply, Long> {

    List<FundBoardReply> findReplyByFundboard_FnoOrderByFrno(Long fno);

    @Query("select r from FundBoardReply r where r.fundboard.fno = :fno")
    Page<FundBoardReply> getListByFno(Long fno, Pageable pageable);

    @Query("select count(r) from FundBoardReply r where r.fundboard.fno = :fno")
    int getReplyCountOfFundboard(Long fno);

}
