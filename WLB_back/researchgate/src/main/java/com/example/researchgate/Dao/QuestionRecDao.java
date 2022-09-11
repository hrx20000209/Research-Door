package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.Question;
import com.example.researchgate.Entity.QuestionRec;
import com.example.researchgate.Entity.QuestionFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRecDao extends BaseMapper<QuestionRec> {
    @Select("select * from question_rec where recommender_id=#{user_id}")
    List<QuestionRec> getRecByUser(Integer user_id);
    @Delete("delete from question_rec where recommender_id=#{user_id} and question_id=#{question_id}")
    void unRec(Integer user_id,Integer question_id);
    @Select("select * from question_rec where recommender_id=#{user_id} and question_id=#{question_id}")
    QuestionRec getRec(Integer user_id,Integer  question_id);
    @Delete("delete from question_rec where question_id=#{question_id}")
    void delRec(Integer question_id);
}
