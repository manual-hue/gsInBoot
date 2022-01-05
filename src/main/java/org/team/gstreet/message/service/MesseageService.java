package org.team.gstreet.message.service;

import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.message.dto.MesseageDTO;
import org.team.gstreet.message.entity.Message;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.PageResponseDTO;


@Transactional
public interface MesseageService {

    Long register(MesseageDTO messeageDTO);

    MesseageDTO read(Long mno);

    PageResponseDTO<Message> getListMesseage(PageRequestDTO pageRequestDTO,String mid);



}
