package com.example.researchgate.model.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MsgGroupVo {
    /**
     * 接收名字
     */
    private String msgTo;
    /**
     * 聊天对象
     */
    private String msgPartner;
    /**
     * 主题
     */
    private String msgSubject;
    /**
     * 最后内容
     */
    private String msgLast;
    /**
     * 最后日期
     */
    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date msgDate;
}
