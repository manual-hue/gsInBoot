package org.team.gstreet.saleboard.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "sale_board")
public class SaleReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private Long group_id;

    private String reply_text;

    private String replyer;

    @CreationTimestamp
    private LocalDateTime reply_date;

    public void setText(String text) {
        this.reply_text=text;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    private SaleBoard saleBoard;




}
