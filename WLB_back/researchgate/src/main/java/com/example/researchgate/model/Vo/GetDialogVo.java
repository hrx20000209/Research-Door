package com.example.researchgate.model.Vo;

import lombok.Data;

import java.util.List;

@Data
public class GetDialogVo {
    /**
     * 主题
     */
    private String  msgSubject;
    /**
     * 信息框
     */
    private List<DialogVo> msgList;
}
