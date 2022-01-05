package org.team.gstreet.fundboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.team.gstreet.fundboard.entity.FundBoard;
import org.team.gstreet.fundboard.repository.search.FundBoardSearch;

public interface FundBoardRepository extends JpaRepository<FundBoard,Long>, FundBoardSearch {

    @Query("select f from FundBoard f left join f.ftags ft where ft like concat('%',:tag,'%')")
    Page<FundBoard> searchTags(String tag, Pageable pageable);

    @Query("select f.fno, f.fwriter, f.ftitle, f.fcontent, f.fregdate, f.fenddate, f.fprice, coalesce(sum(f.fcount), 0), count(r) from FundBoard f left join FundBoardReply r on r.fundboard = f group by f")
    Page<Object[]> ex1(Pageable pageable);

    @Query("select f.fno, f.fwriter, f.ftitle, f.fcontent, f.fregdate, f.fenddate, f.fprice, coalesce(sum(f.fcount), 0), count(r) " +
            "from FundBoard f left join FundBoardReply r on r.fundboard = f group by f")
    Page<Object[]> getList(Pageable pageable);

    @Modifying
    @Query("update FundBoard f set f.fcount = f.fcount + 1 where f.fno = :fno")
    int updateView(Long fno);

}
