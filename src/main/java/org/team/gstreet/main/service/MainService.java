package org.team.gstreet.main.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.fundboard.dto.FundBoardDTO;
import org.team.gstreet.main.dto.PageRequestDTO2;
import org.team.gstreet.main.dto.PageResponseDTO2;
import org.team.gstreet.reqboard.dto.ReqBoardDTO;
import org.team.gstreet.saleboard.dto.SaleBoardDTO;

import java.util.List;

@Transactional
public interface MainService {

    PageResponseDTO2<SaleBoardDTO> getSaleboardList(PageRequestDTO2 pageRequestDTO); //Saleboard목록

    PageResponseDTO2<ReqBoardDTO> getReqboardList(PageRequestDTO2 pageRequestDTO); //Reqboard목록

    PageResponseDTO2<FundBoardDTO> getFundboardList(PageRequestDTO2 pageRequestDTO); //Fundboard목록

    List<Object> mainBoardCountService(); //총게시글 수

    List<Object> mainUpdateBoard(); //오늘의 게시글



}
