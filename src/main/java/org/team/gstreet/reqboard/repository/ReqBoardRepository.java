package org.team.gstreet.reqboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.team.gstreet.reqboard.entity.ReqBoard;
import org.team.gstreet.reqboard.repository.search.ReqBoardSearch;

import java.util.List;

public interface ReqBoardRepository extends JpaRepository<ReqBoard,Long>, ReqBoardSearch {

    @Query(value = "select d from ReqBoard d left join d.tags dt where dt like concat('%', :tag, '%') ")
    Page<ReqBoard> searchTags(String tag, Pageable pageable);

    @Query("select b.bno, b.r_category, b.r_title, b.r_writer, b.r_regDate, b.views, count(r) from ReqBoard b left join ReqReply r on r.reqBoard = b group by b ")
    Page<Object[]> getListWithPT(Pageable pageable);

    @Modifying
    @Query("update ReqBoard r set r.views = r.views + 1 where r.bno =:bno")
    int updateView(Long bno);

    @Query(value = " select sum(cnt) from (\n" +
            "                                 select count(*) cnt from tbl_fundboard\n" +
            "                                 union all\n" +
            "                                 select count(*) cnt from tbl_reqboard\n" +
            "                                 union all\n" +
            "                                 select count(*) cnt from sale_board\n" +
            "                             ) a;",nativeQuery = true)
    List<Object> mainBoardCount(); //전체 게시글 수 - Main

    @Query(value = " select sum(c)  AS '최근게시글' from (\n" +
            " select count(*) c from sale_board where date(sreg_date) = date(now())\n" +
            " union all\n" +
            " select count(*) c from tbl_fundboard where date(fregdate) = date(now())\n" +
            " union all\n" +
            " select count(*) c from tbl_reqboard where date(r_reg_date) = date(now())\n" +
            "  ) a;",nativeQuery = true)
    List<Object> mainUpdateBoard(); //오늘의 게시글 - Main

}
