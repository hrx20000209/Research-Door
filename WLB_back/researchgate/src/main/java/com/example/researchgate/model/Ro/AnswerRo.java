package com.example.researchgate.model.Ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AnswerRo {

    int answerId;
    String content;
    int ownerId;
    int questionId;
    int recNum;
    int followNum;
    Date createTime;
}