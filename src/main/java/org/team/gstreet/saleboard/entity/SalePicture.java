package org.team.gstreet.saleboard.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "uuid")
public class SalePicture implements Comparable<SalePicture>{

    private String uuid;
    private String fileName;
    private int idx;
    private String savePath;
    @Override
    public int compareTo(SalePicture o) {
        return this.idx - o.idx;
    }
}
