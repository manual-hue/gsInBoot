package org.team.gstreet.fundboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team.gstreet.fundboard.dto.FundBoardDTO;
import org.team.gstreet.fundboard.dto.PageRequestDTO;
import org.team.gstreet.fundboard.dto.PageResponseDTO;
import org.team.gstreet.fundboard.service.FundBoardService;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/fundboard")
public class FundBoardController {
    private final FundBoardService fundBoardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<FundBoardDTO> responseDTO

                = fundBoardService.getList(pageRequestDTO);

        model.addAttribute("responseDTO",fundBoardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){ }

    @PostMapping("/register")
    public String registerPost(FundBoardDTO fundBoardDTO, RedirectAttributes redirectAttributes){

        Long fno = fundBoardService.register(fundBoardDTO);

        log.info("FNO: " + fno);
        log.info("TAGS : " + fundBoardDTO.getFtags());
        log.info("FDTO : "+ fundBoardDTO);

        redirectAttributes.addFlashAttribute("result", fno);

        return "redirect:/fundboard/read?fno="+fno;
    }

    @GetMapping(value = {"/read", "/modify"})
    public void read(@RequestParam(value = "fno", required=false) Long fno, PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("dto", fundBoardService.read(fno));

    }

    @PostMapping("/modify")
    public String modifyPost(FundBoardDTO fundBoardDTO, RedirectAttributes redirectAttributes){

        if(fundBoardService.modify(fundBoardDTO)){
            redirectAttributes.addFlashAttribute("result","modified");
        }

        return "redirect:/fundboard/read?fno="+fundBoardDTO.getFno();
    }

    @PostMapping("/remove/{fno}")
    public String remove(@PathVariable("fno") Long fno, RedirectAttributes redirectAttributes){

        fundBoardService.remove(fno);

        return "redirect:/fundboard/list";
    }

    @GetMapping("/payment")
    public void payment(){}

    @PostMapping("/payment")
    public void paymentPost(){}

    @GetMapping("/paymentSuccess")
    public void paymentSuccess(){ }
}
