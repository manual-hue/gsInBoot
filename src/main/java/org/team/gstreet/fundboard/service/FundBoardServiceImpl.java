package org.team.gstreet.fundboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.fundboard.dto.FundBoardDTO;
import org.team.gstreet.fundboard.dto.PageRequestDTO;
import org.team.gstreet.fundboard.dto.PageResponseDTO;
import org.team.gstreet.fundboard.entity.FundBoard;
import org.team.gstreet.fundboard.repository.FundBoardRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FundBoardServiceImpl implements FundBoardService {

    private final ModelMapper modelMapper;
    private final FundBoardRepository boardRepository;

    @Override
    @Transactional
    public Long register(FundBoardDTO fundBoardDTO) {

        FundBoard fundBoard = modelMapper.map(fundBoardDTO, FundBoard.class);

        LocalDateTime time = LocalDateTime.now();
        LocalDateTime fenddate = time.plusWeeks(1);

        fundBoard.setFenddate(fenddate);

        log.info("해시태그!"+fundBoard.getFtags());

        boardRepository.save(fundBoard);

        return fundBoard.getFno();
    }

    @Override
    public PageResponseDTO<FundBoardDTO> getList(PageRequestDTO pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("fno").descending());

        Page<FundBoard> result = boardRepository.search(typeArr,keyword,pageable);

        List<FundBoardDTO> dtoList = result.get().map(

                board -> modelMapper.map(board,FundBoardDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return new PageResponseDTO<>(pageRequestDTO,(int)totalCount,dtoList);
    }

    @Override
    @Transactional
    public FundBoardDTO read(Long fno) {

        Optional<FundBoard> optionalFundBoard = boardRepository.findById(fno);

        FundBoard fundBoard = optionalFundBoard.orElseThrow();

        boardRepository.updateView(fno);

        FundBoardDTO dto = modelMapper.map(fundBoard, FundBoardDTO.class);

        return dto;
    }

    @Override
    public boolean modify(FundBoardDTO fundBoardDTO) {

        Optional<FundBoard> result = boardRepository.findById(fundBoardDTO.getFno());

        if(result.isEmpty()){
            throw new RuntimeException("NOT FOUND");
        }

        FundBoard board = result.get();
        board.change(fundBoardDTO.getFtitle(), fundBoardDTO.getFwriter(), fundBoardDTO.getFcontent(), fundBoardDTO.getFproduct(), fundBoardDTO.getFprice());
        boardRepository.save(board);

        return false;
    }

    @Override
    public Long remove(Long fno) {

        boardRepository.deleteById(fno);

        return fno;
    }
}
