package org.team.gstreet.fundboard.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.fundboard.dto.PageRequestDTO;
import org.team.gstreet.fundboard.dto.PageResponseDTO;
import org.team.gstreet.fundboard.dto.FundBoardDTO;

@Transactional
public interface FundBoardService {

    Long register(FundBoardDTO fundBoardDTO);

    PageResponseDTO<FundBoardDTO> getList(PageRequestDTO pageRequestDTO);

    FundBoardDTO read(Long fno);

    boolean modify(FundBoardDTO fundboardDTO);

    Long remove(Long fno);
}
