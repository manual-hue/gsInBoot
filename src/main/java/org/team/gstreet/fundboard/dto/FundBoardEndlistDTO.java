package org.team.gstreet.fundboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundBoardEndlistDTO {

    private Long fno;
    private String ftitle; // 제목
    private String fwriter; // 작성자
    private String fcontent;
    private LocalDateTime fenddate; // 종료일
    private Integer fcount; // 조회수
    private Integer fprice; // 가격
    private List<String> ftags; // 해시태그
    private List<String> imageTags; // image 태그

    public List<String> getImageTags() { // 이미지 태그 추출

        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img src 추출 정규표현식
        Matcher matcher = pattern.matcher(fcontent);

        List<String> list = new ArrayList<>();

        while(matcher.find()){
            list.add(matcher.group(1));
        }

        return list;
    }

}
