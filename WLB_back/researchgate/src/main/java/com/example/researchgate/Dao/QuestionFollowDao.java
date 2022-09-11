package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.Question;
import com.example.researchgate.Entity.QuestionFollow;
import com.example.researchgate.Entity.QuestionFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionFollowDao extends BaseMapper<QuestionFollow> {
    @Select("select * from question_follow where follower_id=#{user_id}")
    List<QuestionFollow> getFollowByUser(Integer user_id);
    @Delete("delete from question_follow where follower_id=#{user_id} and question_id=#{question_id}")
    void unFollow(Integer user_id,Integer question_id);
    @Select("select * from question_follow where follower_id=#{user_id} and question_id=#{question_id}")
    QuestionFollow getFollow(Integer user_id,Integer  question_id);
    @Delete("delete from question_follow where question_id=#{question_id}")
    void delFollow(Integer question_id);
}
