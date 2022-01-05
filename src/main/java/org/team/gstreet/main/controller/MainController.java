package org.team.gstreet.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team.gstreet.main.dto.PageRequestDTO2;
import org.team.gstreet.main.service.MainService;
import org.team.gstreet.member.service.UsersService;


@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MainService mainService;
    private final UsersService usersService;

    @GetMapping("/list")
    public void list(PageRequestDTO2 pageRequestDTO, Model model) {
        model.addAttribute("responseDTO",mainService.getSaleboardList(pageRequestDTO)); //saleboard목록
        model.addAttribute("reqboardDTO",mainService.getReqboardList(pageRequestDTO)); //reqboard목록
        model.addAttribute("fundboardDTO",mainService.getFundboardList(pageRequestDTO)); //fundboard목록
        model.addAttribute("boardcount",mainService.mainBoardCountService()); //총게시글 수
        model.addAttribute("updateboardcount",mainService.mainUpdateBoard());//오늘의게시글
        model.addAttribute("relUser",usersService.getUsersCount()); //신규회원수
    }


}
