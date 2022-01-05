package org.team.gstreet.Saleboard.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.entity.SaleReply;
import org.team.gstreet.saleboard.repository.SaleReplyRepository;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class SaleReplyRepositoryTests {

    @Autowired
    private SaleReplyRepository saleReplyRepository;

    @Test
    public void insert200(){

        IntStream.rangeClosed(1,200).forEach(i->{

            Long sno = (long)(200 - (i % 5)); // 200, 199, 198, 197, 196

            int replyCount = (i % 5); // 0,1,2,3,4

            IntStream.rangeClosed(0, replyCount).forEach(j->{

                SaleBoard board = SaleBoard.builder().sno(sno).build();


                SaleReply reply = SaleReply.builder()
                        .reply_text("Reply..."+i)
                        .group_id(1L)
                        .replyer("replier...")
                        .saleBoard(board)
                        .build();

                saleReplyRepository.save(reply);
            });//inner loop
        });//outer loop
    }

    @Test
    @Transactional
    public void testRead() {

     Long rno = 1L;

     SaleReply reply = saleReplyRepository.findById(rno).get();

     log.info(reply);

     log.info(reply.getSaleBoard());
    }

//    @Test
//    public void testBySno() {
//        Long sno = 1L;
//        List<SaleReply> replyList = saleReplyRepository.findSaleReplyBySaleBoardSnoOrderByRno(sno);
//
//        replyList.forEach(reply -> log.info(reply));
//    }

    @Transactional
    @Test
    public void testListOfSaleBoard() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());
        Page<SaleReply> result = saleReplyRepository.getListBySno(197L,pageable);

        log.info(result.getTotalElements());

        result.get().forEach(reply -> log.info(reply));
    }


    @Transactional
    @Test
    public void testCountSaleBoard() {
        Long sno = 190L;

        int count = saleReplyRepository.getSaleReplyCountOfSaleBoard(sno);

        int lastPage = (int)(Math.ceil(count/10.0));


        if(lastPage == 0) {
            lastPage = 1;
        }
        Pageable pageable = PageRequest.of(lastPage -1,10);

        Page<SaleReply> result = saleReplyRepository.getListBySno(sno,pageable);

        log.info("total : " + result.getTotalElements());
        log.info("...." + result.getTotalPages());

        result.get().forEach(saleReply -> {
            log.info(saleReply);
        });


    }
}
