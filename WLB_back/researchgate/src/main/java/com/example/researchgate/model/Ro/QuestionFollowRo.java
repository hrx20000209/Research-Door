package com.example.researchgate.model.Ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionFollowRo {

    int rId;
    int questionId;
    int userId;
}