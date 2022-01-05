package org.team.gstreet.saleboard.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@ToString(exclude = {"tags","pictures"})
public class SaleBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    private String stitle;

    private String swriter;

    private String scontent;

    private String scategory;

    @CreationTimestamp
    private LocalDateTime sreg_date;

    @UpdateTimestamp
    private LocalDateTime smod_date;


    @Column(name = "s_count")
    private Integer s_count = 0;


    private double lat;

    private double lang;



    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public void setScontent(String scontent) {
        this.scontent = scontent;
    }

    public void setScategory(String scategory) {
        this.scategory = scategory;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setPictures(Set<SalePicture> pictures) {
        this.pictures = pictures;
    }

    public void setS_option(String s_option) {
        this.s_option = s_option;
    }

    public void setS_count(Integer s_count) {
        this.s_count = this.s_count == null ? 0 : this.s_count;
    }

    public void setSmod_date(LocalDateTime smod_date) {
        this.smod_date = smod_date;
    }

    private String s_option;




    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "sale_board_tags")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    @Builder.Default
    private Set<String> tags = new HashSet<>();


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="sale_board_picture")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<SalePicture> pictures;




}
