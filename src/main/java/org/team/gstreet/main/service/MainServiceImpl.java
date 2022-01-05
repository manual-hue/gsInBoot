package org.team.gstreet.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team.gstreet.fundboard.dto.FundBoardDTO;
import org.team.gstreet.fundboard.dto.PageResponseDTO;
import org.team.gstreet.fundboard.entity.FundBoard;
import org.team.gstreet.fundboard.repository.FundBoardRepository;
import org.team.gstreet.main.dto.PageRequestDTO2;
import org.team.gstreet.main.dto.PageResponseDTO2;
import org.team.gstreet.reqboard.dto.ReqBoardDTO;
import org.team.gstreet.reqboard.entity.ReqBoard;
import org.team.gstreet.reqboard.repository.ReqBoardRepository;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.SaleBoardDTO;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.repository.SaleBoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

    private final ModelMapper modelMapper;
    private final SaleBoardRepository boardRepository;
    private final ReqBoardRepository reqBoardRepository;
    private final FundBoardRepository fundBoardRepository;

    @Override
    public PageResponseDTO2<SaleBoardDTO> getSaleboardList(PageRequestDTO2 pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("sno").descending());
        Page<SaleBoard> result = boardRepository.search(typeArr, keyword, pageable);


        List<SaleBoardDTO> dtoList = result.get().map(

                saleboard -> modelMapper.map(saleboard, SaleBoardDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return new PageResponseDTO2<>(pageRequestDTO, (int) totalCount, dtoList);

    }

    @Override
    public PageResponseDTO2<ReqBoardDTO> getReqboardList(PageRequestDTO2 pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("bno").descending());
        Page<ReqBoard> result = reqBoardRepository.searchMain(typeArr, keyword, pageable);


        List<ReqBoardDTO> dtoList = result.get().map(

                reqBoard -> modelMapper.map(reqBoard, ReqBoardDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return new PageResponseDTO2<>(pageRequestDTO, (int) totalCount, dtoList);
    }

    @Override
    public PageResponseDTO2<FundBoardDTO> getFundboardList(PageRequestDTO2 pageRequestDTO) {
        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("fno").descending());

        Page<FundBoard> result = fundBoardRepository.search(typeArr,keyword,pageable);

        List<FundBoardDTO> dtoList = result.get().map(

                board -> modelMapper.map(board,FundBoardDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return new PageResponseDTO2<>(pageRequestDTO,(int)totalCount,dtoList);
    }

    @Override
    public List<Object> mainBoardCountService() {
        List<Object> r = reqBoardRepository.mainBoardCount();
        return r;
    }

    @Override
    public List<Object> mainUpdateBoard() {
        List<Object> r2 = reqBoardRepository.mainUpdateBoard();

        return r2;
    }


}
