package com.example.researchgate.model.Ro;

import lombok.Data;

@Data
public class SendMsgFormRo {
    /**
     * 接收人id
     */
    private String msgTo;
    /**
     * 消息subject
     */
    private String msgSubject;
    /**
     * 消息内容
     */
    private String msgContent;
}
