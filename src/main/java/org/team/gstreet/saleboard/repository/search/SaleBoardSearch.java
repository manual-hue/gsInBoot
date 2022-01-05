package org.team.gstreet.saleboard.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.team.gstreet.saleboard.entity.SaleBoard;

public interface SaleBoardSearch {


     Page<SaleBoard> search(char[] typeArr, String keyword, Pageable pageable);


     Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword,Pageable pageable);


     Page<Object[]> getListSearch(char[] typeArr, String keyword, Pageable pageable); //안됨

}
