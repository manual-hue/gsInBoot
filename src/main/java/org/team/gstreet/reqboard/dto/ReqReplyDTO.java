package org.team.gstreet.reqboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqReplyDTO {
    private Long rno;

    private String replyText;

    private String replier;

    private Long bno;

    private LocalDateTime replyDate;
}
