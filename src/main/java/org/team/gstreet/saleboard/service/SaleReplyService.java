package org.team.gstreet.saleboard.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.PageResponseDTO;
import org.team.gstreet.saleboard.dto.SaleReplyDTO;


@Transactional
public interface SaleReplyService {
    PageResponseDTO<SaleReplyDTO> getListOfBoard(Long sno, PageRequestDTO pageRequestDTO);

    Long register(SaleReplyDTO replyDTO);

    PageResponseDTO<SaleReplyDTO> remove(Long sno, Long rno, PageRequestDTO pageRequestDTO);

    @Transactional
    PageResponseDTO<SaleReplyDTO> modify(SaleReplyDTO replyDTO, PageRequestDTO pageRequestDTO);
}
