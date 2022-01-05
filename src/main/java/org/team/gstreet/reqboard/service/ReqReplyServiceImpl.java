package org.team.gstreet.reqboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.team.gstreet.reqboard.dto.PageRequestDTO;
import org.team.gstreet.reqboard.dto.PageResponseDTO;
import org.team.gstreet.reqboard.dto.ReqReplyDTO;
import org.team.gstreet.reqboard.entity.ReqReply;
import org.team.gstreet.reqboard.repository.ReqReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReqReplyServiceImpl implements ReqReplyService {

    private final ModelMapper modelMapper;
    private final ReqReplyRepository reqReplyRepository;

    @Override
    public PageResponseDTO<ReqReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = null;

        if (pageRequestDTO.getPage() == -1) {
            int lastPage = calcLastPage(bno, pageRequestDTO.getSize());  // -1 댓글이 없는 경우 , 숫자 마지막 댓글 페이지
            if (lastPage <= 0) {
                lastPage = 1;
            }
            pageRequestDTO.setPage(lastPage);
        }

        pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());


        Page<ReqReply> result = reqReplyRepository.getListByBno(bno, pageable);

        List<ReqReplyDTO> dtoList = result.get()
                .map(reply -> modelMapper.map(reply, ReqReplyDTO.class))
                .collect(Collectors.toList());

        //dtoList.forEach(replyDTO -> log.info(replyDTO));

        return new PageResponseDTO<>(pageRequestDTO, (int) result.getTotalElements(), dtoList);
    }

    @Override
    public Long register(ReqReplyDTO reqReplyDTO) {
        ReqReply reqReply = modelMapper.map(reqReplyDTO, ReqReply.class);

        reqReplyRepository.save(reqReply);

        return reqReply.getRno();

    }

    @Override
    public PageResponseDTO<ReqReplyDTO> remove(Long bno, Long rno, PageRequestDTO pageRequestDTO) {

        reqReplyRepository.deleteById(rno);

        return getListOfBoard(bno, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<ReqReplyDTO> modify(ReqReplyDTO reqReplyDTO, PageRequestDTO pageRequestDTO) {
        ReqReply reqReply = reqReplyRepository.findById(reqReplyDTO.getRno()).orElseThrow();

        reqReply.setText(reqReplyDTO.getReplyText());

        reqReplyRepository.save(reqReply);

        return getListOfBoard(reqReplyDTO.getBno(), pageRequestDTO);
    }

    private int calcLastPage(Long bno, double size) {

        int count = reqReplyRepository.getReplyCountOfBoard(bno);
        int lastPage = (int) (Math.ceil(count / size));

        return lastPage;
    }
}
