package org.team.gstreet.reqboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team.gstreet.reqboard.dto.*;
import org.team.gstreet.reqboard.entity.ReqBoard;
import org.team.gstreet.reqboard.entity.ReqPhoto;
import org.team.gstreet.reqboard.repository.ReqBoardRepository;
import org.team.gstreet.saleboard.dto.SaleBoardDTO;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.entity.SalePicture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class ReqBoardServiceImpl implements ReqBoardService {

    private final ModelMapper modelMapper;
    private final ReqBoardRepository reqBoardRepository;

    @Override
    public Long register(ReqBoardDTO reqBoardDTO) {
        ReqBoard reqBoard = modelMapper.map(reqBoardDTO, ReqBoard.class);

        log.info("reqBoard :" + reqBoard);
        log.info("Tags : " + reqBoard.getTags());
        log.info("Pictures : " + reqBoard.getPhotos());

        reqBoardRepository.save(reqBoard);


        return reqBoard.getBno();
    }

    @Override
    public PageResponseDTO<ReqBoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Page<ReqBoard> result = reqBoardRepository.findAll(pageable);

        long totalCount = result.getTotalElements();

        List<ReqBoardDTO> dtoList
                = result.get().map(reqBoard -> modelMapper.map(reqBoard, ReqBoardDTO.class)).collect(Collectors.toList());

        return new PageResponseDTO<>(pageRequestDTO, (int) totalCount, dtoList);
    }

    @Override
    public PageResponseDTO<ReqBoardListDTO> getListWithAll(PageRequestDTO pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Page<Object[]> result = reqBoardRepository.searchWithAll(typeArr, keyword, pageable);

        List<ReqBoardListDTO> dtoList = result.get().map(objects -> {
            ReqBoardListDTO listDTO = ReqBoardListDTO.builder()
                    .bno((Long) objects[0])
                    .r_category((String) objects[1])
                    .r_title((String) objects[2])
                    .r_writer((String) objects[3])
                    .r_regDate((LocalDateTime) objects[4])
                    .r_modDate((LocalDateTime) objects[5])
                    .replyCount((Long) objects[6])
                    .views((Integer) objects[7])
                    .build();
            return listDTO;
        }).collect(Collectors.toList());
        //내용물 뽑아내기 modelmapper 사용할수 없다 왜냐 객체와 객체간 매핑을 해 주는 애 이기 때문,
        // 이거는 boardlistdto에 배열을 매칭하는것이기 때문에 안맞는다.
        return new PageResponseDTO<>(pageRequestDTO, (int) result.getTotalElements(), dtoList);
    }

    @Override
    public ReqBoardDTO read(Long bno) {

        Optional<ReqBoard> result = reqBoardRepository.findById(bno);

        reqBoardRepository.updateView(bno);
        ReqBoard reqBoard = result.orElseThrow();

        ReqBoardDTO dto =  modelMapper.map(result.get(),ReqBoardDTO.class);

        return dto;
    }

    @Override
    public void modify(ReqBoardDTO reqBoardDTO) {
        log.info("req modify..." + reqBoardDTO);

        Optional<ReqBoard> result = reqBoardRepository.findById(reqBoardDTO.getBno());

            ReqBoard tags = modelMapper.map(reqBoardDTO.getTags(),ReqBoard.class);
            ReqBoard pictures = modelMapper.map(reqBoardDTO.getPhotos(),ReqBoard.class);

            Set<String> ta1 = tags.getTags();
            Set<ReqPhoto> p1 = pictures.getPhotos();

            ReqBoard reqBoard2 = result.orElseThrow();

            reqBoard2.setR_title(reqBoardDTO.getR_title());
            reqBoard2.setR_content(reqBoardDTO.getR_content());
            reqBoard2.setR_category(reqBoardDTO.getR_category());
            reqBoard2.setViews(reqBoardDTO.getViews());
            reqBoard2.setR_modDate(reqBoardDTO.getR_modDate());
            reqBoard2.setR_regDate(reqBoardDTO.getR_regDate());
            reqBoard2.setTags(ta1);
            reqBoard2.setPhotos(p1);


            reqBoardRepository.save(reqBoard2);



    }

    @Override
    public boolean remove(Long bno) {
        reqBoardRepository.deleteById(bno);
        return false;
    }
}
