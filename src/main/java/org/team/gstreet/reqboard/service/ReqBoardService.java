package org.team.gstreet.reqboard.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.reqboard.dto.PageRequestDTO;
import org.team.gstreet.reqboard.dto.PageResponseDTO;
import org.team.gstreet.reqboard.dto.ReqBoardDTO;
import org.team.gstreet.reqboard.dto.ReqBoardListDTO;

@Transactional
public interface ReqBoardService {

    Long register(ReqBoardDTO reqBoardDTO);

    PageResponseDTO<ReqBoardDTO> getList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ReqBoardListDTO> getListWithAll(PageRequestDTO pageRequestDTO);

    ReqBoardDTO read(Long bno);

    void modify(ReqBoardDTO boardDTO);

    boolean remove(Long bno);
}
