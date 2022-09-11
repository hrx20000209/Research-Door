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
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private int answerId;
    private String content;
    private int ownerId;
    private int questionId;
    private int recNum;
    private int followNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setRecNum(int recNum) {
        this.recNum = recNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getContent() {
        return content;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getRecNum() {
        return recNum;
    }

    public int getFollowNum() {
        return followNum;
    }

    public Date getCreateTime() {
        return createTime;
    }
}

