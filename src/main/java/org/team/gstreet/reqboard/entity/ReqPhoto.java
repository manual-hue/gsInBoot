package org.team.gstreet.reqboard.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "uuid")
public class ReqPhoto implements Comparable<ReqPhoto> {

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;


    @Override
    public int compareTo(ReqPhoto o) {
        return this.idx - o.idx;
    }
}
