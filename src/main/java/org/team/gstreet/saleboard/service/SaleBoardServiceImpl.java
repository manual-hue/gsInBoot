package org.team.gstreet.saleboard.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.saleboard.dto.*;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.entity.SalePicture;
import org.team.gstreet.saleboard.entity.SaleReply;
import org.team.gstreet.saleboard.repository.SaleBoardRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class SaleBoardServiceImpl implements SaleBoardService {

    private final ModelMapper modelMapper;
    private final SaleBoardRepository boardRepository;


    @Override
    public Long register(SaleBoardDTO saleBoardDTO) {
        SaleBoard saleBoard = modelMapper.map(saleBoardDTO, SaleBoard.class);

        log.info("saleBoard :" + saleBoard);
        log.info("Tags : " + saleBoard.getTags());
        log.info("Pictures : " + saleBoard.getPictures());

        boardRepository.save(saleBoard);

        return saleBoard.getSno();
    }

    @Override
    public PageResponseDTO<SaleBoardDTO> getList(PageRequestDTO pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("sno").descending());
        Page<SaleBoard> result = boardRepository.search(typeArr, keyword, pageable);
        boardRepository.findWithFavoriteCount(pageable);

        List<SaleBoardDTO> dtoList = result.get().map(

                saleboard -> modelMapper.map(saleboard, SaleBoardDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return new PageResponseDTO<>(pageRequestDTO, (int) totalCount, dtoList);
    }

    @Override
    public PageResponseDTO<SaleBoardListDTO> getListWithReplyCount(PageRequestDTO pageRequestDTO) {
        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("sno").descending());

        Page<Object[]> result = boardRepository.searchWithReplyCount(typeArr, keyword, pageable);

        List<SaleBoardListDTO> dtoList = result.get().map(objects -> {
            SaleBoardListDTO boardListDTO = SaleBoardListDTO.builder()
                    .sno((Long) objects[0])
                    .stitle((String) objects[1])
                    .swriter((String) objects[2])
                    .scategory((String) objects[3])
                    .sreg_date((LocalDateTime) objects[4])
                    .replyCount((Long)objects[5])
                    .build();

            return boardListDTO;
        }).collect(Collectors.toList());

        return new PageResponseDTO<>(pageRequestDTO, (int) result.getTotalElements(), dtoList);
    }

    @Override
    public PageResponseDTO<SaleBoardListDTO> getListWithFavorite(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("sno").descending());


        Page<Object[]> result = boardRepository.findWithFavoriteCount(pageable);
        long totalCount = result.getTotalElements();

        List<SaleBoardListDTO> dtoList = result.get().map(objects -> {

            Object[] arr = (Object[]) objects;
            SaleBoard saleBoard = (SaleBoard)arr[0];
            long totalScore = (long) arr[1];


            SaleBoardListDTO saleBoardListDTO = modelMapper.map(saleBoard,SaleBoardListDTO.class);
            saleBoardListDTO.setTotalScore((int)totalScore);


            return saleBoardListDTO;
        }).collect(Collectors.toList());



        return new PageResponseDTO<>(pageRequestDTO, (int)totalCount, dtoList);

    }



    @Override
    public SaleBoardDTO read(Long sno) {

        Optional<SaleBoard> result = boardRepository.findById(sno);

        boardRepository.updateView(sno);
       SaleBoard saleBoard = result.orElseThrow();

       SaleBoardDTO dto =  modelMapper.map(result.get(),SaleBoardDTO.class);

        return dto;
    }


    @Override
    public void modify(SaleBoardDTO boardDTO) {

        log.info("sale modify..." + boardDTO);

        Optional<SaleBoard> result = boardRepository.findById(boardDTO.getSno());


        SaleBoard tags = modelMapper.map(boardDTO.getTags(),SaleBoard.class);
        SaleBoard pictures = modelMapper.map(boardDTO.getPictures(),SaleBoard.class);



        Set<String> ta1 = tags.getTags();
        Set<SalePicture> p1 = pictures.getPictures();

        SaleBoard saleBoard = result.orElseThrow();


        saleBoard.setStitle(boardDTO.getStitle());
        saleBoard.setScontent(boardDTO.getScontent());
        saleBoard.setScategory(boardDTO.getScategory());
        saleBoard.setLat(boardDTO.getLat());
        saleBoard.setLang(boardDTO.getLang());
        saleBoard.setS_option(boardDTO.getS_option());
        saleBoard.setTags(ta1);
        saleBoard.setPictures(p1);
        saleBoard.setS_count(boardDTO.getS_count());
        saleBoard.setSmod_date(boardDTO.getSmod_date());


         boardRepository.save(saleBoard);

    }

    @Override
    public void remove(Long sno) {

        boardRepository.deleteById(sno);
    }

    @Override
    public void updateViewCount(Long sno) { //조회수
        boardRepository.updateView(sno);
    }


}
