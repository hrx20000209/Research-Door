package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.Question;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends BaseMapper<Question> {
    @Select("select * from question where question_id=#{question_id}")
    Question getQuestionById(Integer question_id);
    @Select("select * from question where owner_id=#{owner_id}")
    List<Question> getAskedQuestion(Integer owner_id);
    @Delete("delete from question where question_id=#{question_id}")
    void delQuestionById(Integer question_id);
    @Update("update question set title=#{title},description=#{description},keywords=#{keywords} where question_id=#{question_id}")
    void updateQuestionById(Integer question_id,String title,String description,String keywords);
    @Update("update question set follow_num=follow_num+1 where question_id=#{question_id}")
    void addQuestionFollow(Integer question_id);
    @Update("update question set follow_num=follow_num-1 where question_id=#{question_id}")
    void subQuestionFollow(Integer question_id);
    @Update("update question set rec_num=rec_num+1 where question_id=#{question_id}")
    void addQuestionRec(Integer question_id);
    @Update("update question set rec_num=rec_num-1 where question_id=#{question_id}")
    void subQuestionRec(Integer question_id);
    @Select(" select * from question" +
            "        where title like concat('%',#{keyword},'%')" +
            "        or description like concat('%',#{keyword},'%')" +
            "        or keywords like concat('%',#{keyword},'%')")
    List<Question> searchQuestion(String keyword);
    @Update("update question set ans_num=ans_num+1 where question_id=#{question_id}")
    void addAnswer(Integer question_id);
    @Update("update question set ans_num=ans_num-1 where question_id=#{question_id}")
    void delAnswer(Integer question_id);
    @Update("update question set read_num=read_num+1 where question_id=#{question_id}")
    void readQuestion(Integer question_id);
}
