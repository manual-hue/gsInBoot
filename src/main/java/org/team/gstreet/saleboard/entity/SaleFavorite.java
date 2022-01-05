package org.team.gstreet.saleboard.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.team.gstreet.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"sale_board","member"})
public class SaleFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long fno;

    @ManyToOne(fetch = FetchType.LAZY)
    private SaleBoard saleBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private int score;

    @CreationTimestamp
    private LocalDateTime regDate;
}
