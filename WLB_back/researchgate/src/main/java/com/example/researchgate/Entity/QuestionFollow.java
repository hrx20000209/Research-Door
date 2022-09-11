package com.example.researchgate.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private int rId;
    private int questionId;
    private int followerId;

    public int getrId() {
        return rId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }
}

