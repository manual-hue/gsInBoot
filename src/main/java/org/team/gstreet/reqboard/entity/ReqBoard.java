package org.team.gstreet.reqboard.entity;

import lombok.*;
import org.hibernate.annotations.*;
import org.team.gstreet.fundboard.entity.FundBoard;
import org.team.gstreet.saleboard.entity.SaleBoard;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_reqboard")
@Builder
@Setter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"tags","photos"})
public class ReqBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String r_category;

    private String r_title;

    private String r_content;

    private String r_writer;

    @Column(name = "views")
    private Integer views = 0;

    @CreationTimestamp
    private LocalDateTime r_regDate;
    @UpdateTimestamp
    private LocalDateTime r_modDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tbl_req_tags")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    @Builder.Default
    private Set<String> tags = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tbl_req_photo")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<ReqPhoto> photos;


    public void setViews(Integer views) {
        this.views = this.views == null ? 0 : this.views;
    }

    public void setR_category(String r_category) {
        this.r_category = r_category;
    }

    public void setR_title(String r_title) {
        this.r_title = r_title;
    }

    public void setR_content(String r_content) {
        this.r_content = r_content;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setPhotos(Set<ReqPhoto> photos) {
        this.photos = photos;
    }

    public void setR_writer(String r_writer) {
        this.r_writer = r_writer;
    }

    public void setR_modDate(LocalDateTime r_modDate) {
        this.r_modDate = r_modDate;
    }

    public void setR_regDate(LocalDateTime r_regDate) {
        this.r_regDate = r_regDate;
    }
}
