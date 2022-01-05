package org.team.gstreet.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.team.gstreet.member.entity.Member;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MesseageDTO {

    private Long mno;

    private String send;

    private String receive;

    private String content;

    private LocalDateTime send_time;
}
