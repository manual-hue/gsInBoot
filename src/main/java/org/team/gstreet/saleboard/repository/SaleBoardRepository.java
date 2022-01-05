package org.team.gstreet.saleboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.repository.search.SaleBoardSearch;

import java.util.List;

public interface SaleBoardRepository extends JpaRepository<SaleBoard,Long>, SaleBoardSearch {

    @Query("select t from SaleBoard t where t.stitle like concat('%', :keyword,'%')")
    List<SaleBoard> getListTitle(String keyword);


    @Query("select s.lat, s.lang from SaleBoard s")
    List<SaleBoard> getCategory();


    @Query("select s.sno,s.stitle,s.swriter,s.scategory,s.sreg_date, count(r) from SaleBoard s left join SaleReply  r on r.saleBoard = s group by s")
    Page<Object[]> ex1(Pageable pageable);



    @Query("select s from SaleBoard s left join s.tags dt where dt like concat('%',:tag,'%')")
    Page<SaleBoard> searchTags(String tag, Pageable pageable);


    @Modifying
    @Query("update SaleBoard s set s.s_count = s.s_count + 1 where s.sno = :sno")
    int updateView(Long sno);



    @Query("select s,coalesce( sum (f.score),0) from SaleBoard s left join SaleFavorite f on f.saleBoard = s group by s")
    Page<Object[]> findWithFavoriteCount(Pageable pageable);

}




