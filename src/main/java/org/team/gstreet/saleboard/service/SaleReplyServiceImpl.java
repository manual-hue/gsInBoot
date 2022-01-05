package org.team.gstreet.saleboard.service;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.PageResponseDTO;
import org.team.gstreet.saleboard.dto.SaleReplyDTO;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.entity.SaleReply;
import org.team.gstreet.saleboard.repository.SaleReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SaleReplyServiceImpl implements SaleReplyService{

    private final SaleReplyRepository saleReplyRepository;
    private final ModelMapper modelMapper;


    public int calcLastPage(Long sno,double size) {
        int count = saleReplyRepository.getSaleReplyCountOfSaleBoard(sno);

        int lastPage = (int)(Math.ceil(count/size));


        return lastPage;
    }

    @Override
    public PageResponseDTO<SaleReplyDTO> getListOfBoard(Long sno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = null;

        if(pageRequestDTO.getPage() == -1) {
            int lastPage = calcLastPage(sno,pageRequestDTO.getSize());

            if(lastPage <= 0) {
                lastPage = 1;
            }
            pageRequestDTO.setPage(lastPage);
        }

        pageable = PageRequest.of(pageRequestDTO.getPage()-1,pageRequestDTO.getSize());

        Page<SaleReply> result = saleReplyRepository.getListBySno(sno,pageable);

        List<SaleReplyDTO> dtoList = result.get().map(reply -> modelMapper.map(reply,SaleReplyDTO.class)).collect(Collectors.toList());

        dtoList.forEach(replyDTO -> log.info(replyDTO));

        return new PageResponseDTO<>(pageRequestDTO,(int)result.getTotalElements(),dtoList);
    }



    @Override
    public Long register(SaleReplyDTO replyDTO) {
        SaleBoard board = SaleBoard.builder()
                .sno(replyDTO.getSno())
                .build();

        SaleReply reply = modelMapper.map(replyDTO, SaleReply.class);


        if(reply.getGroup_id() == null){
            saleReplyRepository.save(reply);
            log.info(reply.getRno());
            saleReplyRepository.update(reply.getRno());

        }
        saleReplyRepository.update(reply.getRno());
        saleReplyRepository.save(reply);



        return reply.getRno();
    }

    @Override
    public PageResponseDTO<SaleReplyDTO> remove(Long sno, Long rno, PageRequestDTO pageRequestDTO) {

        saleReplyRepository.deleteById(rno);

        return getListOfBoard(sno,pageRequestDTO);
    }

    @Override
    public PageResponseDTO<SaleReplyDTO> modify(SaleReplyDTO replyDTO, PageRequestDTO pageRequestDTO) {

        SaleReply reply = saleReplyRepository.findById(replyDTO.getRno()).orElseThrow();

        reply.setText(replyDTO.getReply_text());

        saleReplyRepository.save(reply);

        log.info("수정됐니? "  + replyDTO.getReply_text());

        return getListOfBoard(replyDTO.getSno(),pageRequestDTO);
    }
}
