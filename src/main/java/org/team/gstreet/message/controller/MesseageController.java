package org.team.gstreet.message.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team.gstreet.member.entity.Member;
import org.team.gstreet.message.dto.MesseageDTO;
import org.team.gstreet.message.entity.Message;
import org.team.gstreet.message.service.MesseageService;
import org.team.gstreet.saleboard.dto.PageRequestDTO;

import javax.servlet.http.HttpSession;


@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/messeage")
public class MesseageController {

    private final MesseageService messeageService;



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public void register() {


    }

    @PostMapping("/register")
    public String registerPost(MesseageDTO messeageDTO, RedirectAttributes redirectAttributes) {

        Long mno = messeageService.register(messeageDTO);

        redirectAttributes.addFlashAttribute("result",mno);


        return "redirect:/saleboard/list";
    }



    @GetMapping("/read")
    public void read(Long mno, MesseageDTO messeageDTO, Model model) {

        model.addAttribute("dto", messeageService.read(mno));
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public void list(@RequestParam(value = "receive") String id, PageRequestDTO pageRequestDTO,Model model) {
        model.addAttribute("responseDTO",messeageService.getListMesseage(pageRequestDTO,currentUserName()));

    }

    public String currentUserName() {
        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication2.getName();
        log.warn("current username : {}", username);
        return username;
    }
}
