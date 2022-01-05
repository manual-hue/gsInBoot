package org.team.gstreet.reqboard.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.team.gstreet.reqboard.entity.ReqBoard;

public interface ReqBoardSearch {

     Page<Object[]> search(char[] typeArr, String keyword, Pageable pageable);

     Page<Object[]> searchWithAll(char[] typeArr, String keyword, Pageable pageable);

     Page<ReqBoard> searchMain(char[] typeArr, String keyword, Pageable pageable);
}
