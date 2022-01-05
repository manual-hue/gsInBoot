package org.team.gstreet.saleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.team.gstreet.saleboard.entity.SalePicture;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleBoardDTO {

    private Long sno;

    private String stitle;

    private String swriter;

    private String scontent;

    private String scategory;

    private LocalDateTime sreg_date;

    private LocalDateTime smod_date;

    private Integer s_count;

    private Integer slike_count;

    private double lat;

    private double lang;

    private String s_option;


    private List<String> tags;
    private List<SalePictureDTO> pictures;




}
