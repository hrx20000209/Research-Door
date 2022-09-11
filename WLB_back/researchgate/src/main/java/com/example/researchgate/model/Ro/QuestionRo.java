package com.example.researchgate.model.Ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionRo {

    String title;
    String description;
    int questionId;
    int followNum;
    int ownerId;
    int recNum;
    int ansNum;
    int readNum;
    String keywords;
    Date createTime;
}