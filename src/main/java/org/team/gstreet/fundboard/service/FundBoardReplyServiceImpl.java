package org.team.gstreet.fundboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.team.gstreet.fundboard.dto.FundBoardReplyDTO;
import org.team.gstreet.fundboard.dto.PageRequestDTO;
import org.team.gstreet.fundboard.dto.PageResponseDTO;
import org.team.gstreet.fundboard.entity.FundBoardReply;
import org.team.gstreet.fundboard.repository.FundBoardReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FundBoardReplyServiceImpl implements FundBoardReplyService {

    private final ModelMapper modelMapper;
    private final FundBoardReplyRepository replyRepository;

    @Override
    public PageResponseDTO<FundBoardReplyDTO> getListOfFundBoard(Long fno, PageRequestDTO pageRequestDTO) {

        Pageable pageable= null;

        if(pageRequestDTO.getPage() == -1) {
            int lastPage = calcLastPage(fno, pageRequestDTO.getSize());  // -1 댓글이 없는 경우 , 숫자 마지막 댓글 페이지
            if (lastPage <= 0) {
                lastPage = 1;
            }
            pageRequestDTO.setPage(lastPage);
        }

        pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize());


        Page<FundBoardReply> result = replyRepository.getListByFno(fno, pageable);

        List<FundBoardReplyDTO> dtoList = result.get()
                .map(reply ->  modelMapper.map(reply, FundBoardReplyDTO.class))
                .collect(Collectors.toList());

        dtoList.forEach(replyDTO -> log.info(replyDTO));


        return new PageResponseDTO<>( pageRequestDTO, (int)result.getTotalElements(),dtoList);
    }

    @Override
    public Long register(FundBoardReplyDTO replyDTO) {

        FundBoardReply reply = modelMapper.map(replyDTO, FundBoardReply.class);

        replyRepository.save(reply);

        return reply.getFrno();
    }

    @Override
    public PageResponseDTO<FundBoardReplyDTO> remove(Long fno, Long frno, PageRequestDTO pageRequestDTO) {

        replyRepository.deleteById(frno);

        return getListOfFundBoard(fno, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<FundBoardReplyDTO> modify(FundBoardReplyDTO replyDTO, PageRequestDTO pageRequestDTO) {

        FundBoardReply reply = replyRepository.findById(replyDTO.getFrno()).orElseThrow();

        reply.setText(replyDTO.getFreplyText());

        replyRepository.save(reply);

        return getListOfFundBoard(replyDTO.getFno(), pageRequestDTO);
    }

    private int calcLastPage(Long fno, double size) {

        int count = replyRepository.getReplyCountOfFundboard(fno);
        int lastPage = (int)(Math.ceil(count/size));

        return lastPage;
    }
}





