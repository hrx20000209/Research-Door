package com.example.researchgate.Service.Impl;

import com.example.researchgate.Dao.AnswerDao;
import com.example.researchgate.Dao.AnswerFollowDao;
import com.example.researchgate.Dao.AnswerRecDao;
import com.example.researchgate.Entity.Answer;
import com.example.researchgate.Entity.AnswerFollow;
import com.example.researchgate.Entity.AnswerRec;

import com.example.researchgate.Entity.Question;
import com.example.researchgate.model.Ro.AnswerRo;
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
public class AnswerService {
    @Autowired
    AnswerDao answerDao;
    @Autowired
    AnswerFollowDao answerFollowDao;
    @Autowired
    AnswerRecDao answerRecDao;
    @Transactional(rollbackFor = Exception.class)
    public int addAnswer(AnswerRo answerRo) {
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerRo, answer);

        answer.setFollowNum(0);

        answer.setRecNum(0);

        answerDao.insert(answer);
        return answer.getAnswerId();
    }

    public int updateAnswer(AnswerRo answerRo) {
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerRo,answer);
        answerDao.updateAnswerById(answer.getAnswerId(),answer.getContent());
        return answer.getAnswerId();
    }
    public Answer getAnswerById(Integer id){
        Answer answer = answerDao.getAnswerById(id);
        if(answer!=null){
            return answer;
        }
        return null;
    }



    public void delAnswer(AnswerRo answerRo){
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerRo, answer);
        answerDao.delAnswerById(answer.getAnswerId());
        answerFollowDao.delFollow(answer.getAnswerId());
        answerRecDao.delRec(answer.getAnswerId());
    }



    public void followAnswer(Integer userId,Integer answerId) {
        AnswerFollow answerFollow = new AnswerFollow();
        answerFollow.setAnswerId(answerId);
        answerFollow.setFollowerId(userId);
        answerDao.addAnswerFollow(answerId);
        answerFollowDao.insert(answerFollow);
    }

    public void recAnswer(Integer userId,Integer answerId) {
        AnswerRec answerRec = new AnswerRec();
        answerRec.setAnswerId(answerId);
        answerRec.setRecommenderId(userId);
        answerDao.addAnswerRec(answerId);
        answerRecDao.insert(answerRec);
    }

    public void unfollowAnswer(Integer userId,Integer answerId) {

        answerDao.subAnswerFollow(answerId);
        answerFollowDao.unFollow(userId,answerId);
    }

    public void unrecAnswer(Integer userId,Integer answerId) {

        answerDao.subAnswerRec(answerId);
        answerRecDao.unRec(userId,answerId);
    }

    public AnswerFollow getAnswerFollow(Integer userId,Integer answerId) {


        return answerFollowDao.getFollow(userId,answerId);
    }

    public AnswerRec getAnswerRec(Integer userId,Integer answerId) {


        return answerRecDao.getRec(userId,answerId);
    }
    public List<Answer> getFollowedAnswer(Integer id){
        List<Answer> answers = new ArrayList<>();
        List<AnswerFollow> answerFollows = answerFollowDao.getFollowByUser(id);
        for(AnswerFollow item:answerFollows) {
            Answer tmp = answerDao.getAnswerById(item.getAnswerId());
            answers.add(tmp);
        }
        return answers;
    }
    public List<Answer> getRecedAnswer(Integer id){
        List<Answer> answers = new ArrayList<>();
        List<AnswerRec> answerRecs = answerRecDao.getRecByUser(id);
        for(AnswerRec item:answerRecs) {
            Answer tmp = answerDao.getAnswerById(item.getAnswerId());
            answers.add(tmp);
        }
        return answers;
    }
    public List<Answer> getAnswers(Integer questionId){
        List<Answer> answers = answerDao.getAnswers(questionId);

        return answers;
    }

}