package org.team.gstreet.Saleboard.repository;


import com.google.common.collect.Sets;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.team.gstreet.saleboard.dto.SaleBoardDTO;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.entity.SalePicture;
import org.team.gstreet.saleboard.repository.SaleBoardRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class SaleBoardRepositoryTest {

    @Autowired
    private SaleBoardRepository saleBoardRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testInsert() { //등록

        IntStream.rangeClosed(1,10).forEach(i -> {
                    SaleBoard saleBoard = SaleBoard.builder()
                            .stitle("판매글")
                            .scontent("판매내용")
                            .s_option("progress")
                            .swriter("user" + (i%10))
                            .scategory("Asia")
                            .lat(37.5701802)
                            .lang(126.9810083)
                            .build();
            saleBoardRepository.save(saleBoard);
                });


    }

    @Test
    public void testRead() { //조회
        Optional<SaleBoard> result = saleBoardRepository.findById(2L);
        result.ifPresent(saleBoard -> log.info(saleBoard));

        if(result.isPresent()) {
            log.info(result.get());
        }

        if(result.isEmpty()) {
            throw new RuntimeException("NOT FOUND!!");
        }
    }

    @Transactional
    @Commit
    @Test
    public void testUpdate() {

        Set<String> updateTags = Sets.newHashSet("aaa","bbb","ccc");

        Set<SalePicture> pictures = IntStream.rangeClosed(10,15).mapToObj(i -> {
            SalePicture salePicture = SalePicture.builder()
                    .uuid(UUID.randomUUID().toString())
                    .savePath("2021/11/11")
                    .fileName("Test" + i + ".jpg")
                    .idx(i)
                    .build();

            return salePicture;
        }).collect(Collectors.toSet());


        Optional<SaleBoard> result = saleBoardRepository.findById(241L);

        SaleBoard saleBoard = result.orElseThrow();

        saleBoard.setStitle("수정");
        saleBoard.setScontent("제목");
        saleBoard.setScategory("Asia");
        saleBoard.setLat(11.1);
        saleBoard.setLang(22.2);
        saleBoard.setTags(updateTags);
        saleBoard.setPictures(pictures);

        saleBoardRepository.save(saleBoard);


    }


    @Test
    public void testDelete() {
        saleBoardRepository.deleteById(3L);

    }

    @Test
    public void testPaging() {

        Pageable pageable = PageRequest.of(1,10, Sort.by("sno").descending());

        Page<SaleBoard> result = saleBoardRepository.findAll(pageable);

        result.get().forEach(reqBoard -> log.info(reqBoard));

    }

    @Test
    public void testgetListTitle() {

        List<SaleBoard> result = saleBoardRepository.getListTitle("판매글");

        log.info(result);

    }

    @Test
    public void testSearch1() {

        char[] typeArr = null;
        String keyword = null;
        Pageable pageable = PageRequest.of(0,10, Sort.by("sno").descending());

        Page<SaleBoard> result = saleBoardRepository.search(typeArr,keyword,pageable);

        result.get().forEach(saleBoard -> {
            log.info(saleBoard);
            log.info("------------");

            SaleBoardDTO saleBoardDTO = modelMapper.map(saleBoard,SaleBoardDTO.class);

            log.info(saleBoardDTO);
        });
    }


    @Test
    public void testex1() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("sno").descending());

        Page<Object[]> result = saleBoardRepository.ex1(pageable);

        result.get().forEach(element -> {
            Object[] arr = (Object[])element;
            log.info(Arrays.toString(arr));
        });

    }


    @Test
    public void testSearchWithReplyCount() {
        char[] typeArr = {'W'};
        String keyword = "제이콥";

        Pageable pageable = PageRequest.of(0,10,Sort.by("sno").descending());

        Page<Object[]> result = saleBoardRepository.searchWithReplyCount(typeArr,keyword,pageable);

        log.info("total: " + result.getTotalPages());

        result.get().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });
        saleBoardRepository.searchWithReplyCount(typeArr,keyword,pageable);
    }


    @Test
    public void testHashInsert() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            Set<String> tags = IntStream.rangeClosed(1,3).mapToObj(j -> i + "_tag_" + j)
                    .collect(Collectors.toSet());

            Set<SalePicture> pictures = IntStream.rangeClosed(1,3).mapToObj(j -> {
                SalePicture salePicture = SalePicture.builder()
                        .uuid(UUID.randomUUID().toString())
                        .savePath("2021/11/10")
                        .fileName("img" + j + ".jpg")
                        .idx(j)
                        .build();
                return salePicture;
            }).collect(Collectors.toSet());


            SaleBoard saleBoard = SaleBoard.builder()
                    .stitle("판매글")
                    .scontent("판매내용")
                    .swriter("user" + (i%10))
                    .scategory("Asia")
                    .lat(37.5701802)
                    .lang(126.9810083)
                    .tags(tags)
                    .pictures(pictures)
                    .build();

            saleBoardRepository.save(saleBoard);
        });
    }

    @Test
    public void testSelectOne() {
        Long sno = 220L;

        Optional<SaleBoard> saleBoard = saleBoardRepository.findById(sno);

        SaleBoard saleBoard1 = saleBoard.orElseThrow();

        SaleBoardDTO saleBoardDTO = modelMapper.map(saleBoard1,SaleBoardDTO.class);

        log.info(saleBoardDTO);
    }

    @Test
    public void testPaging2() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("sno").descending());

        Page<SaleBoard> result = saleBoardRepository.findAll(pageable);

        result.get().forEach(saleBoard -> {
            log.info(saleBoard);
            log.info(saleBoard.getTags());
            log.info("------------");
        });
    }

    @Test
    public void testSeachTag() {
        String tag = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("sno").descending());

        Page<SaleBoard> result =  saleBoardRepository.searchTags(tag,pageable);

        result.get().forEach(saleBoard ->{
            log.info(saleBoard);
            log.info(saleBoard.getTags());
            log.info(saleBoard.getPictures());
            log.info("----------------------");
        });
    }

    @Test
    public void testWithFavorite() {

        char[] typeArr = {'W'};
        String keyword = "제이콥";


        Pageable pageable = PageRequest.of(0,10, Sort.by("sno").descending());
        Page<Object[]> result = saleBoardRepository.getListSearch(typeArr,keyword,pageable);

        for(Object[] o : result.getContent()) { //list타입으로반환해줌
            log.info(Arrays.toString(o));
        }

    }
}
