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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private int questionId;
    private String title;
    private String description;
    private int followNum;
    private int ownerId;
    private int recNum;
    private int ansNum;
    private int readNum;
    private String keywords;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public int getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getFollowNum() {
        return followNum;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getRecNum() {
        return recNum;
    }

    public int getAnsNum() {
        return ansNum;
    }

    public int getReadNum() {
        return readNum;
    }

    public String getKeywords() {
        return keywords;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setRecNum(int recNum) {
        this.recNum = recNum;
    }

    public void setAnsNum(int ansNum) {
        this.ansNum = ansNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

