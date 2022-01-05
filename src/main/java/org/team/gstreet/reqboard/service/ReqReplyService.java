package org.team.gstreet.reqboard.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.reqboard.dto.PageRequestDTO;
import org.team.gstreet.reqboard.dto.PageResponseDTO;
import org.team.gstreet.reqboard.dto.ReqReplyDTO;

@Transactional
public interface ReqReplyService {

    PageResponseDTO<ReqReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

    Long register(ReqReplyDTO reqReplyDTO);

    PageResponseDTO<ReqReplyDTO> remove(Long bno, Long rno, PageRequestDTO pageRequestDTO);

    PageResponseDTO<ReqReplyDTO> modify(ReqReplyDTO reqReplyDTO, PageRequestDTO pageRequestDTO);



}
