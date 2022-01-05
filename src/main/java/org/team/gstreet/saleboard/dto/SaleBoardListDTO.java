package org.team.gstreet.saleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleBoardListDTO {

    private Long sno;

    private String stitle;

    private String swriter;

    private String scategory;

    private LocalDateTime sreg_date;

    private String s_option;

    private Integer s_count;

    private Long replyCount;

    private double lat;

    private double lang;

    private List<String> tags;

    private List<SalePictureDTO> pictures;

    private int totalScore;



}
