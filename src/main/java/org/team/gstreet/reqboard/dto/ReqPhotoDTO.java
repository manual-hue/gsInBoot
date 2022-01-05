package org.team.gstreet.reqboard.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class ReqPhotoDTO {

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;

    public String getLink() {
        return savePath+"/"+uuid+"_"+fileName;
    }
}
