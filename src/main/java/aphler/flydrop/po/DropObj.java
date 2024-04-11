package aphler.flydrop.po;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @TableName drop_obj
 */
@TableName(value = "drop_obj")
@Data
public class DropObj implements Serializable {

    @JsonIgnore
    @TableId(type = IdType.INPUT)
    private Long id;

    private String code;

    private Integer type;

    private String content;

    private String fileName;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_MINUTE_PATTERN)
    private Date createTime;

    //过期时间
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_MINUTE_PATTERN)
    private Date expiresTime;

    @TableLogic(value = "0", delval = "1")
    private Boolean deleted;

    @Serial
    private static final long serialVersionUID = 234229504536L;
}