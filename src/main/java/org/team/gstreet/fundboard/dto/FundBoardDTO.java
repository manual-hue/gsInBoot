package org.team.gstreet.fundboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class FundBoardDTO {

    private Long fno;

    private String ftitle;

    private String fcontent;

    private String fproduct; // 상품명

    private String fwriter;

    private LocalDateTime fregdate;

    private LocalDateTime fenddate; // 펀딩 종료일, 작성일 기준 +7일

    @Builder.Default
    private Integer fcount=0; // 조회수

    @Builder.Default
    private Integer supportCount=0; // 후원수

    private Integer fprice;

    private List<String> ftags;

    private List<String> imageTags;

    public List<String> getImageTags() { // 이미지 태그 추출

        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img src 추출 정규표현식
        Matcher matcher = pattern.matcher(fcontent);


        List<String> list = new Vector<>();

        while(matcher.find()){
            list.add(matcher.group(1));
        }
        return list;
    }


}
