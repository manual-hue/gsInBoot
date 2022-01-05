package org.team.gstreet.saleboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.PageResponseDTO;
import org.team.gstreet.saleboard.dto.SaleBoardDTO;
import org.team.gstreet.saleboard.dto.SaleBoardListDTO;
import org.team.gstreet.saleboard.service.SaleBoardService;


import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/saleboard")
public class SaleBoardController {

    private final SaleBoardService saleBoardService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("responseDTO",saleBoardService.getList(pageRequestDTO));

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String registerPost(SaleBoardDTO boardDTO,RedirectAttributes redirectAttributes) {

        Long sno = saleBoardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result",sno);

        return "redirect:/saleboard/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = {"/modify", "/read"})
    public void read(Long sno, PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("dto",saleBoardService.read(sno));


    }

    @PostMapping("/modify")
    public String modify(SaleBoardDTO saleBoardDTO, PageRequestDTO pageRequestDTO,RedirectAttributes redirectAttributes) {

        log.info("post modify.....");

        saleBoardService.modify(saleBoardDTO);
        Long sno = saleBoardService.register(saleBoardDTO);

        redirectAttributes.addAttribute("sno",saleBoardDTO.getSno());

        return "redirect:/saleboard/read";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove")
    public String remove(Long sno, RedirectAttributes redirectAttributes) {
        saleBoardService.remove(sno);

        redirectAttributes.addFlashAttribute("msg",sno);

        return "redirect:/saleboard/list";
    }


}
