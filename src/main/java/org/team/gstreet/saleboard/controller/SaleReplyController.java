package org.team.gstreet.saleboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.PageResponseDTO;
import org.team.gstreet.saleboard.dto.SaleReplyDTO;
import org.team.gstreet.saleboard.service.SaleReplyService;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
public class SaleReplyController {

    private final SaleReplyService saleReplyService;

    @GetMapping("/list/{sno}")
    public PageResponseDTO<SaleReplyDTO> getListOfBoard(@PathVariable("sno")Long sno, PageRequestDTO pageRequestDTO) {
        return saleReplyService.getListOfBoard(sno, pageRequestDTO);
    }


    @PostMapping("")
    public PageResponseDTO<SaleReplyDTO> register(@RequestBody SaleReplyDTO replyDTO) {
        saleReplyService.register(replyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(-1)
                .build();

        return saleReplyService.getListOfBoard(replyDTO.getSno(),pageRequestDTO);

    }

    @DeleteMapping("/{sno}/{rno}")
    public PageResponseDTO<SaleReplyDTO> remove(@PathVariable("sno") Long sno,@PathVariable("rno")Long rno, PageRequestDTO pageRequestDTO) {

        return saleReplyService.remove(sno,rno,pageRequestDTO);
    }

    @PutMapping("/{sno}/{rno}")
    public PageResponseDTO<SaleReplyDTO> modify(@PathVariable("sno") Long sno,@PathVariable("rno")Long rno, @RequestBody SaleReplyDTO replyDTO, PageRequestDTO pageRequestDTO) { //수정

        log.info("sno: " + sno);
        log.info("rno: " + rno);
        log.info("replyDTO: " + replyDTO);

        return saleReplyService.modify(replyDTO,pageRequestDTO);
    }
}
