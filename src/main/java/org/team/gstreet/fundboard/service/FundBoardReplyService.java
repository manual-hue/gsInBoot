package org.team.gstreet.fundboard.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.fundboard.dto.FundBoardReplyDTO;
import org.team.gstreet.fundboard.dto.PageRequestDTO;
import org.team.gstreet.fundboard.dto.PageResponseDTO;

@Transactional
public interface FundBoardReplyService {

    PageResponseDTO<FundBoardReplyDTO> getListOfFundBoard(Long fno, PageRequestDTO pageRequestDTO);

    Long register(FundBoardReplyDTO replyDTO);

    PageResponseDTO<FundBoardReplyDTO> remove(Long fno, Long frno, PageRequestDTO pageRequestDTO);

    PageResponseDTO<FundBoardReplyDTO> modify(FundBoardReplyDTO replyDTO, PageRequestDTO pageRequestDTO);


}
