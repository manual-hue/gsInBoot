package org.team.gstreet.saleboard.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class SalePictureDTO {


    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;

    public String getLink() { //썸네일
        return savePath+"/s_"+uuid+"_"+fileName;
    }

    public String getLinkFile() { //일반파일사이즈
        return savePath+ "/" + uuid+"_"+fileName;
    }
}
