package org.team.gstreet.saleboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleReplyDTO {


    private Long rno;

    private Long group_id;

    private String reply_text;

    private String replyer;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone="Asia/Seoul")
    private LocalDateTime reply_date;

    private Long sno;
}
