package com.example.researchgate.model.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DialogVo {
    /**
     * 发送名字
     */
    private String msgFrom;
    /**
     * 信息内容
     */
    private String msgContent;
    /**
     * 时间
     */
    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date msgDate;
}
