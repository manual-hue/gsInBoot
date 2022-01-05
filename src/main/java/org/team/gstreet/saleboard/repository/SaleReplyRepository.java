package org.team.gstreet.saleboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.team.gstreet.saleboard.entity.SaleReply;

import java.util.List;

public interface SaleReplyRepository extends JpaRepository<SaleReply,Long> {

    List<SaleReply> findSaleReplyBySaleBoardSnoOrderByRno(Long sno);

    @Query("select r from SaleReply r where r.saleBoard.sno = :sno")
    Page<SaleReply> getListBySno(Long sno, Pageable pageable);

    @Query("select count(r) from SaleReply r where r.saleBoard.sno = :sno")
    int getSaleReplyCountOfSaleBoard(Long sno);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update SaleReply s set s.group_id = 0 where s.rno = :rno")
    int update(Long rno);



}
