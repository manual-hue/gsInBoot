package org.team.gstreet.Saleboard.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team.gstreet.member.entity.Member;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.entity.SaleFavorite;
import org.team.gstreet.saleboard.repository.SaleFavoriteRepository;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class SaleFavoriteRepositoryTests {
    @Autowired
    SaleFavoriteRepository favoriteRepository;

    @Test
    public void insertDommies() {



        IntStream.rangeClosed(1,3).forEach(i-> {


            Long sno = (long)6 + ( i % 3 );


            String mid = "user" + i; //mid 1~3



            SaleBoard saleBoard = SaleBoard.builder()
                    .sno(sno)
                    .build();

            Member member = Member.builder()
                    .mid(mid)
                    .build();

            SaleFavorite favorite = SaleFavorite.builder()
                    .member(member)
                    .saleBoard(saleBoard)
                    .score(1)
                    .build();

            favoriteRepository.save(favorite);

        });
    }


}
