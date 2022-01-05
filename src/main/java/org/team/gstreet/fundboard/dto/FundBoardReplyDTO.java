package org.team.gstreet.fundboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundBoardReplyDTO {

    private Long frno;

    private String freplyText;

    private String freplyer;

    private Long fno;

    private LocalDateTime freplyDate;
}
