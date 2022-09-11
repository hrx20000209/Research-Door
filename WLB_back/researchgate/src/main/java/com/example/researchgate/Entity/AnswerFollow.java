package com.example.researchgate.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AnswerFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private int rId;
    private int answerId;
    private int followerId;

    public int getrId() {
        return rId;
    }



    public int getFollowerId() {
        return followerId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }
}

