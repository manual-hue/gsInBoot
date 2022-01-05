package org.team.gstreet.reqboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqBoardDTO {

    private Long bno;

    private String r_category;

    private String r_title;

    private String r_content;

    private String r_writer;

    private Integer views;

    private LocalDateTime r_regDate;
    private LocalDateTime r_modDate;

    private List<String> tags;
    private List<ReqPhotoDTO> photos;

}
