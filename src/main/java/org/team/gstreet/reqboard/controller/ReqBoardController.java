package org.team.gstreet.reqboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team.gstreet.reqboard.dto.PageRequestDTO;
import org.team.gstreet.reqboard.dto.ReqBoardDTO;
import org.team.gstreet.reqboard.service.ReqBoardService;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reqboard")
public class ReqBoardController {
    private final ReqBoardService reqBoardService;

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("Controller Remove:" + bno);

        if (reqBoardService.remove(bno)) {
            log.info("remove success");
            redirectAttributes.addFlashAttribute("result", "success");
        }
        return "redirect:/reqboard/list";
    }


    @PostMapping("/modify")
    public String modifyPost(ReqBoardDTO reqBoardDTO, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info("c      modify:" + reqBoardDTO);

        reqBoardService.modify(reqBoardDTO);

        Long bno = reqBoardService.register(reqBoardDTO);

        log.info("등록완료됐나: " + bno);

        redirectAttributes.addAttribute("bno", reqBoardDTO.getBno());

        return "redirect:/reqboard/read";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        model.addAttribute("res", reqBoardService.getListWithAll(pageRequestDTO));
    }


    @GetMapping("/register")
    public void register() {

    }


    @PostMapping("/register")
    public String registerPost(ReqBoardDTO reqBoardDTO, RedirectAttributes redirectAttributes) {

        Long bno = reqBoardService.register(reqBoardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/reqboard/list";
    }

    @GetMapping(value = {"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        log.info("c read" + bno);
        log.info("c read " + pageRequestDTO);

        model.addAttribute("dto", reqBoardService.read(bno));
    }

}
