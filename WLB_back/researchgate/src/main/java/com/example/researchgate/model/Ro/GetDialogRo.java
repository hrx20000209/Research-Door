package com.example.researchgate.model.Ro;

import lombok.Data;

@Data
public class GetDialogRo {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 对话的另一方的userName
     */
    private String msgTo;
    /**
     * 对话的subject
     */
    private String msgSubject;
}
