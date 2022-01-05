package org.team.gstreet.fundboard.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class FundBoardPictureDTO {

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;

    public String getLink() {

        return savePath+"/s_"+uuid+"_"+fileName;
    }
}
