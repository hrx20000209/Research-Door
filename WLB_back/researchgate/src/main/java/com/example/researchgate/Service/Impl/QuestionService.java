package com.example.researchgate.Service.Impl;

import com.example.researchgate.Dao.QuestionDao;
import com.example.researchgate.Dao.QuestionFollowDao;
import com.example.researchgate.Dao.QuestionRecDao;
import com.example.researchgate.Entity.Question;
import com.example.researchgate.Entity.QuestionFollow;
import com.example.researchgate.Entity.QuestionRec;
import com.example.researchgate.model.Ro.QuestionFollowRo;
import com.example.researchgate.model.Ro.QuestionRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuestionFollowDao questionFollowDao;
    @Autowired
    QuestionRecDao questionRecDao;
    @Transactional(rollbackFor = Exception.class)
    public int addQuestion(QuestionRo questionRo) {
        Question question = new Question();
        BeanUtils.copyProperties(questionRo, question);
        question.setAnsNum(0);
        question.setFollowNum(0);
        question.setReadNum(0);
        question.setRecNum(0);

        questionDao.insert(question);
        return question.getQuestionId();
    }

    public Question getQuestionById(Integer id){
        Question question = questionDao.getQuestionById(id);
        if(question!=null){
            return question;
        }
        return null;
    }


    public List<Question> getAskedQuestion(Integer id){
        List<Question> question = questionDao.getAskedQuestion(id);
        if(question!=null){
            return question;
        }
        return null;
    }
    public void delQuestion(QuestionRo questionRo){
        Question question = new Question();
        BeanUtils.copyProperties(questionRo, question);
        questionDao.delQuestionById(question.getQuestionId());
        questionFollowDao.delFollow(question.getQuestionId());
        questionRecDao.delRec(question.getQuestionId());
    }

    public int updateQuestion(QuestionRo questionRo) {
        Question question = new Question();
        question.setAnsNum(0);
        question.setFollowNum(0);
        question.setReadNum(0);
        question.setRecNum(0);
        BeanUtils.copyProperties(questionRo, question);
        questionDao.updateQuestionById(question.getQuestionId(),question.getTitle(),question.getDescription(),question.getKeywords());
        return question.getQuestionId();
    }

    public void followQuestion(Integer userId,Integer questionId) {
        QuestionFollow questionFollow = new QuestionFollow();
        questionFollow.setQuestionId(questionId);
        questionFollow.setFollowerId(userId);
        questionDao.addQuestionFollow(questionId);
        questionFollowDao.insert(questionFollow);
    }

    public void recQuestion(Integer userId,Integer questionId) {
        QuestionRec questionRec = new QuestionRec();
        questionRec.setQuestionId(questionId);
        questionRec.setRecommenderId(userId);
        questionDao.addQuestionRec(questionId);
        questionRecDao.insert(questionRec);
    }

    public void unfollowQuestion(Integer userId,Integer questionId) {

        questionDao.subQuestionFollow(questionId);
        questionFollowDao.unFollow(userId,questionId);
    }

    public void unrecQuestion(Integer userId,Integer questionId) {

        questionDao.subQuestionRec(questionId);
        questionRecDao.unRec(userId,questionId);
    }

    public QuestionFollow getQuestionFollow(Integer userId,Integer questionId) {


        return questionFollowDao.getFollow(userId,questionId);
    }

    public QuestionRec getQuestionRec(Integer userId,Integer questionId) {


        return questionRecDao.getRec(userId,questionId);
    }
    public List<Question> getFollowedQuestion(Integer id){
        List<Question> questions = new ArrayList<>();
        List<QuestionFollow> questionFollows = questionFollowDao.getFollowByUser(id);
        for(QuestionFollow item:questionFollows) {
            Question tmp = questionDao.getQuestionById(item.getQuestionId());
            questions.add(tmp);
        }
        return questions;
    }
    public List<Question> getRecedQuestion(Integer id){
        List<Question> questions = new ArrayList<>();
        List<QuestionRec> questionRecs = questionRecDao.getRecByUser(id);
        for(QuestionRec item:questionRecs) {
            Question tmp = questionDao.getQuestionById(item.getQuestionId());
            questions.add(tmp);
        }
        return questions;
    }
    public List<Question> searchQuestion(String keyword){
        List<Question> questions = questionDao.searchQuestion(keyword);

        return questions;
    }

    public void addAnswer(Integer questionId) {
        questionDao.addAnswer(questionId);

    }
    public void delAnswer(Integer questionId) {
        questionDao.delAnswer(questionId);

    }

    public void readQuestion(Integer questionId) {
        questionDao.readQuestion(questionId);

    }
}