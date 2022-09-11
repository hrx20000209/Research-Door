package com.example.researchgate.model.Ro;

import lombok.Data;

@Data
public class SendMsgRo {
    /**
     * 消息发送
     */
    private SendMsgFrom sendMsgForm;
    /**
     * 发送人
     */
    private String userId;
}
