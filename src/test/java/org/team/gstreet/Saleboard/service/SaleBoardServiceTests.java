package org.team.gstreet.Saleboard.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.main.service.MainService;
import org.team.gstreet.saleboard.dto.*;
import org.team.gstreet.saleboard.service.SaleBoardService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class SaleBoardServiceTests {

    @Autowired
    private SaleBoardService saleBoardService;

    @Autowired
    private MainService mainService;

    @Test
    public void testRegister() {

        IntStream.rangeClosed(1,10).forEach(i -> {
            List<String> tags = IntStream.rangeClosed(1, 3).mapToObj(j -> "_tag_" + j)
                    .collect(Collectors.toList());

            List<SalePictureDTO> pictures = IntStream.rangeClosed(1, 3).mapToObj(j -> {
                SalePictureDTO salePictureDTO = SalePictureDTO.builder()
                        .uuid(UUID.randomUUID().toString())
                        .savePath("2021/11/10")
                        .fileName("img" + j + ".jpg")
                        .idx(j)
                        .build();
                return salePictureDTO;
            }).collect(Collectors.toList());

            SaleBoardDTO saleBoardDTO = SaleBoardDTO.builder()
                    .stitle("SaleTitle")
                    .scontent("SaleContent")
                    .swriter("user")
                    .scategory("USA")
                    .lat(35.5090627)
                    .lang(139.2093892)
                    .tags(tags)
                    .s_option("progress")
                    .build();

            Long sno = saleBoardService.register(saleBoardDTO);
            log.info("SNO : " + sno);
        });
    }

    @Transactional(readOnly = true)
    @Test
    public void testRead() {

        Long sno = 220L;

        SaleBoardDTO dto = saleBoardService.read(sno);

        log.info(dto);

        log.info(dto.getPictures().size());

        dto.getPictures().forEach(salePictureDTO -> log.info(salePictureDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<SaleBoardDTO> responseDTO = saleBoardService.getList(pageRequestDTO);

        log.info(responseDTO);

        responseDTO.getDtoList().forEach(diaryDTO ->  {
            log.info(diaryDTO);
            log.info(diaryDTO.getTags());
            log.info(diaryDTO.getPictures());

            log.info("---------------------------");
        });
    }

    @Test
    public void testList2() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<SaleBoardListDTO> responseDTO = saleBoardService.getListWithFavorite(pageRequestDTO);

        log.info(responseDTO);


    }

    @Test
    public void testMain() {
        List<Object> r = mainService.mainBoardCountService();

        log.info(r);
    }

    @Test
    public void testUpdateMain() {
        List<Object> r2 = mainService.mainUpdateBoard();

        log.info(r2);
    }

}
