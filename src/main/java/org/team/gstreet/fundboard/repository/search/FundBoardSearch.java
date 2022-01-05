package org.team.gstreet.fundboard.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.team.gstreet.fundboard.entity.FundBoard;

public interface FundBoardSearch {

     Page<FundBoard> search(char[] typeArr, String keyword, Pageable pageable);

//     Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable);

}
