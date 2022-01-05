package org.team.gstreet.fundboard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_fundboard")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "ftags")
@Log4j2
public class FundBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    private String ftitle;

    private String fcontent;

    private String fproduct; // 상품명

    private String fwriter;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime fregdate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fenddate;

    @Builder.Default
    private Integer fcount = 0; // 조회수

    @Builder.Default
    private Integer supportCount = 0; // 후원수

    private Integer fprice;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_fund_tag")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    @Builder.Default
    private Set<String> ftags = new HashSet<>();

    public void change(String ftitle, String fwriter, String fcontent, String fproduct, Integer fprice) {
        this.ftitle = ftitle;
        this.fwriter = fwriter;
        this.fcontent = fcontent;
        this.fproduct = fproduct;
        this.fprice = fprice;
    }

    public void setFenddate(LocalDateTime fenddate) {
        this.fenddate = fenddate;
    }

    public void setFtags(Set<String> ftags) {
        this.ftags = ftags;
    }

}