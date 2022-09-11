package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.researchgate.Entity.AnswerFollow;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerFollowDao extends BaseMapper<AnswerFollow> {
    @Select("select * from answer_follow where follower_id=#{user_id}")
    List<AnswerFollow> getFollowByUser(Integer user_id);
    @Delete("delete from answer_follow where follower_id=#{user_id} and answer_id=#{answer_id}")
    void unFollow(Integer user_id,Integer answer_id);
    @Select("select * from answer_follow where follower_id=#{user_id} and answer_id=#{answer_id}")
    AnswerFollow getFollow(Integer user_id,Integer  answer_id);
    @Delete("delete from answer_follow where answer_id=#{answer_id}")
    void delFollow(Integer answer_id);
}
