package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.researchgate.Entity.AnswerRec;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRecDao extends BaseMapper<AnswerRec> {
    @Select("select * from answer_rec where recommender_id=#{user_id}")
    List<AnswerRec> getRecByUser(Integer user_id);
    @Delete("delete from answer_rec where recommender_id=#{user_id} and answer_id=#{answer_id}")
    void unRec(Integer user_id,Integer answer_id);
    @Select("select * from answer_rec where recommender_id=#{user_id} and answer_id=#{answer_id}")
    AnswerRec getRec(Integer user_id,Integer  answer_id);
    @Delete("delete from answer_rec where answer_id=#{answer_id}")
    void delRec(Integer answer_id);
}
