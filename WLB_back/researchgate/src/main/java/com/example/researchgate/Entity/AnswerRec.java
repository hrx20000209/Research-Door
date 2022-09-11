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
public class AnswerRec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private int rId;
    private int answerId;
    private int recommenderId;

    public void setrId(int rId) {
        this.rId = rId;
    }



    public void setRecommenderId(int recommenderId) {
        this.recommenderId = recommenderId;
    }

    public int getrId() {
        return rId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public int getRecommenderId() {
        return recommenderId;
    }
}

