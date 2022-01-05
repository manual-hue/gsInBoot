package org.team.gstreet.reqboard.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "reqBoard")
public class ReqReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replier;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReqBoard reqBoard;

    @CreationTimestamp
    private LocalDateTime replyDate;

    public void setText(String text) {
        this.replyText = text;
    }
}
