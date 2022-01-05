package org.team.gstreet.reqboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.team.gstreet.reqboard.dto.PageRequestDTO;
import org.team.gstreet.reqboard.dto.PageResponseDTO;
import org.team.gstreet.reqboard.dto.ReqReplyDTO;
import org.team.gstreet.reqboard.service.ReqReplyService;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies2")
public class ReqReplyController {

    private final ReqReplyService reqReplyService;

    @GetMapping("/list/{bno}")
    public PageResponseDTO<ReqReplyDTO> getListOfBoard(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO){

        return reqReplyService.getListOfBoard(bno, pageRequestDTO);

    }

    @PostMapping("")
    public PageResponseDTO<ReqReplyDTO> register(@RequestBody ReqReplyDTO reqReplyDTO ){

        reqReplyService.register(reqReplyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(-1).build();

        return reqReplyService.getListOfBoard(reqReplyDTO.getBno(), pageRequestDTO);

    }

    @DeleteMapping("/{bno}/{rno}")
    public PageResponseDTO<ReqReplyDTO> remove(
            @PathVariable("bno") Long bno,
            @PathVariable("rno") Long rno,
            PageRequestDTO pageRequestDTO ){

        return reqReplyService.remove(bno, rno, pageRequestDTO);
    }

    @PutMapping("/{bno}/{rno}")
    public PageResponseDTO<ReqReplyDTO> modify(
            @PathVariable("bno") Long bno,
            @PathVariable("rno") Long rno,
            @RequestBody ReqReplyDTO replyDTO,
            PageRequestDTO requestDTO ){

        log.info("bno: " + bno);
        log.info("rno: " + rno);
        log.info("replyDTO: " + replyDTO);


        return reqReplyService.modify(replyDTO, requestDTO);
    }
}
