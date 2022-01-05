package org.team.gstreet.fundboard.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "fundboard")
public class FundBoardReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long frno;

    private String freplyText;

    private String freplyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private FundBoard fundboard;

    @CreationTimestamp
    private LocalDateTime freplyDate;

    public void setText(String text){
        this.freplyText = text;
    }
}
