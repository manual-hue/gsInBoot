package org.team.gstreet.saleboard.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.PageResponseDTO;
import org.team.gstreet.saleboard.dto.SaleBoardDTO;
import org.team.gstreet.saleboard.dto.SaleBoardListDTO;

@Transactional
public interface SaleBoardService {

    Long register(SaleBoardDTO reqBoardDTO);

    PageResponseDTO<SaleBoardDTO> getList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<SaleBoardListDTO> getListWithReplyCount(PageRequestDTO pageRequestDTO);

    PageResponseDTO<SaleBoardListDTO> getListWithFavorite(PageRequestDTO pageRequestDTO);

    SaleBoardDTO read(Long sno);

    void modify(SaleBoardDTO boardDTO);

    void remove(Long sno);


    void updateViewCount(Long sno);


}
