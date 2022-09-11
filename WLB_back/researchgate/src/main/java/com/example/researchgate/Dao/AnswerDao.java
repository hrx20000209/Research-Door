package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.Answer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDao extends BaseMapper<Answer> {
    @Select("select * from answer where answer_id=#{answer_id}")
    Answer getAnswerById(Integer answer_id);

    @Delete("delete from answer where answer_id=#{answer_id}")
    void delAnswerById(Integer answer_id);
    @Update("update answer set follow_num=follow_num+1 where answer_id=#{answer_id}")
    void addAnswerFollow(Integer answer_id);
    @Update("update answer set follow_num=follow_num-1 where answer_id=#{answer_id}")
    void subAnswerFollow(Integer answer_id);
    @Update("update answer set rec_num=rec_num+1 where answer_id=#{answer_id}")
    void addAnswerRec(Integer answer_id);
    @Update("update answer set rec_num=rec_num-1 where answer_id=#{answer_id}")
    void subAnswerRec(Integer answer_id);
    @Select("select * from answer where question_id=#{question_id}")
    List<Answer> getAnswers(Integer question_id);
    @Update("update answer set content=#{content} where answer_id=#{answer_id}")
    void updateAnswerById(Integer answer_id,String content);
}
