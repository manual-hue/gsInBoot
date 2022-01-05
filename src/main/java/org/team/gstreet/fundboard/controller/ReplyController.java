package org.team.gstreet.fundboard.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.team.gstreet.fundboard.dto.FundBoardReplyDTO;
import org.team.gstreet.fundboard.dto.PageRequestDTO;
import org.team.gstreet.fundboard.dto.PageResponseDTO;
import org.team.gstreet.fundboard.service.FundBoardReplyService;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies3")
public class ReplyController {

    private final FundBoardReplyService replyService;

    @GetMapping("/list/{fno}")
    public PageResponseDTO<FundBoardReplyDTO> getListOfBoard(@PathVariable("fno") Long fno, PageRequestDTO pageRequestDTO){

        return replyService.getListOfFundBoard(fno, pageRequestDTO);

    }

    @PostMapping("")
    public PageResponseDTO<FundBoardReplyDTO> register(@RequestBody FundBoardReplyDTO replyDTO ){

        replyService.register(replyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(-1).build();

        return replyService.getListOfFundBoard(replyDTO.getFno(), pageRequestDTO);

    }

    @DeleteMapping("/{fno}/{frno}")
    public PageResponseDTO<FundBoardReplyDTO> remove(
            @PathVariable("fno") Long fno,
            @PathVariable("frno") Long frno,
            PageRequestDTO requestDTO ){

        return replyService.remove(fno, frno, requestDTO);
    }

    @PutMapping("/{fno}/{frno}")
    public PageResponseDTO<FundBoardReplyDTO> modify(
            @PathVariable("fno") Long fno,
            @PathVariable("frno") Long frno,
            @RequestBody FundBoardReplyDTO replyDTO,
            PageRequestDTO requestDTO){

        log.info("fno: " + fno);
        log.info("frno: " + frno);
        log.info("replyDTO: " + replyDTO);


        return replyService.modify(replyDTO, requestDTO);
    }

}






