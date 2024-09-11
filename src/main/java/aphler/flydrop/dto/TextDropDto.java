package aphler.flydrop.dto;

import lombok.Data;

@Data
public class TextDropDto {

    private String text;

    //超时时间(分钟)
    private Integer expiresMinute;

    //是否需要取件码
    private boolean needCode;

}
