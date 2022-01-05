package org.team.gstreet.chatting.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team.gstreet.chatting.entity.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@Log4j2
@RequestMapping("/chatting")
public class ChattingController {

    static int roomNumber = 0;
    List<Room> roomList = new ArrayList<>();


    @GetMapping("/room")
    public void room() {
        log.info("나와라!!");
    }


    @GetMapping("/chat")
    public void chat(@RequestParam HashMap<Object, Object> params, Model model) {
        log.info("나와라!!");
        int roomNumber = Integer.parseInt((String) params.get("roomNumber"));
        String roomName = (String)params.get("roomName");


        List<Room> new_list = roomList.stream().filter(o->o.getRoomNumber()==roomNumber).collect(Collectors.toList());
        if(new_list != null && new_list.size() > 0) {


            model.addAttribute("roomName", roomName);
            model.addAttribute("roomNumber", roomNumber);

        }

    }


    @PostMapping("/createRoom")
    public @ResponseBody
    List<Room> createRoom(@RequestParam HashMap<Object,Object> params) {
        String roomName = (String) params.get("roomName");

        if(roomName != null && !roomName.trim().equals("")) {
            Room room = new Room();
            room.setRoomName(roomName);
            room.setRoomNumber(++roomNumber);
            roomList.add(room);
        }

        return roomList;
    }


    @PostMapping("/getRoom")
    public @ResponseBody List<Room> getRoom(@RequestParam HashMap<Object,Object> params) {

        return roomList;
    }




}
