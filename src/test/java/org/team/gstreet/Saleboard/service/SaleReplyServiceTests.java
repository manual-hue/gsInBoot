package org.team.gstreet.Saleboard.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.SaleReplyDTO;
import org.team.gstreet.saleboard.service.SaleReplyService;

@SpringBootTest
@Log4j2
public class SaleReplyServiceTests {

    @Autowired
    private SaleReplyService saleReplyService;

    @Test
    public void testList() {

        Long sno = 197L;

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        saleReplyService.getListOfBoard(sno,pageRequestDTO);
    }

    @Test
    public void testReply() {
        Long sno = 198L;

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(-1)
                .build();

        log.info(saleReplyService.getListOfBoard(sno,pageRequestDTO));
    }


    @Test
    public void testRegister() {
        SaleReplyDTO replyDTO = SaleReplyDTO.builder()
                .sno(198L)
                .reply_text("198번 해본다다다다")
                .replyer("foo")
                .build();

        saleReplyService.register(replyDTO);
    }
}
