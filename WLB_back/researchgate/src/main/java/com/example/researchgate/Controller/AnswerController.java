package com.example.researchgate.Controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.researchgate.Entity.*;
import com.example.researchgate.Service.Impl.AccountService;
import com.example.researchgate.Service.Impl.AnswerService;
import com.example.researchgate.Service.Impl.QuestionService;
import com.example.researchgate.Utils.RespUtil;
import com.example.researchgate.Utils.Result;
import com.example.researchgate.enums.CodeEnum;

import com.example.researchgate.model.Resp;
import com.example.researchgate.model.Ro.AnswerRo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 问题相关
 * @author lhr
 */
@RestController
@RequestMapping("/answer")
@Slf4j
public class AnswerController {
    @Data
    class RandF{
        int isFollowing;
        int isRec;
        int answerId;
        String content;
        int ownerId;
        int questionId;
        int recNum;
        int followNum;
        Date createTime;
        String userName;
        String introduction;
    }


    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AccountService accountService;
    /**
     * 添加回答接口
     * @param answerRo
     * @return Answer
     */
    @RequestMapping(value = "addAnswer",method = RequestMethod.POST)
    @ResponseBody
    public Resp addAnswer(@RequestBody AnswerRo answerRo) {
        try {
            int id = answerService.addAnswer(answerRo);
            questionService.addAnswer(answerRo.getQuestionId());
            if(id!=0){
                Answer answer = answerService.getAnswerById(id);
                return RespUtil.ok(answer);
            }
            else{
                return RespUtil.fail(CodeEnum.ADD_ANSWER_FAIL);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.ADD_ANSWER_FAIL);
        }
    }

    /**
     * 编辑回答接口
     * @param answerRo
     * @return Answer
     */
    @RequestMapping(value = "updateAnswer",method = RequestMethod.POST)
    @ResponseBody
    public Resp updateAnswer(@RequestBody AnswerRo answerRo) {
        try {
            int id = answerService.updateAnswer(answerRo);
            //questionService.addAnswer(answerRo.getQuestionId());
            if(id!=0){
                Answer answer = answerService.getAnswerById(id);
                return RespUtil.ok(answer);
            }
            else{
                return RespUtil.fail(CodeEnum.ADD_ANSWER_FAIL);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.ADD_ANSWER_FAIL);
        }
    }
    /**
     * 删除回答接口
     * @param answerRo
     * @return Answer
     */
    @RequestMapping(value = "delAnswer",method = RequestMethod.POST)
    @ResponseBody
    public Result delAnswer(@RequestBody AnswerRo answerRo) {
        try {
            Answer answer = answerService.getAnswerById(answerRo.getAnswerId());
            if(answer==null){
                return Result.fail("回答不存在");
            }
            answerService.delAnswer(answerRo);
            questionService.delAnswer(answer.getQuestionId());
            return Result.succ("删除成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("删除失败");
        }
    }

    /**
     * 获取回答接口
     * @param answerId
     * @return Answer
     */
    @GetMapping("/getAnswer")
    @ResponseBody
    public Resp getAnswer(@RequestParam("answerId") String answerId,@RequestParam("userId") String userId){

        RandF qandF = new RandF();
        Integer Id = Integer.parseInt(answerId);
        Integer userid = Integer.parseInt(userId);
        Answer answer = answerService.getAnswerById(Id);
        if(answer!=null)
            BeanUtils.copyProperties(answer, qandF);
        else
            return RespUtil.fail(CodeEnum.GET_QUESTION_FAIL);
        List<Answer> list1 = answerService.getFollowedAnswer(userid);
        List<Answer> list2 = answerService.getRecedAnswer(userid);
        int flag1=0,flag2=0;
        for(Answer a :list1){
            if(a.getAnswerId()==Id)
                flag1=1;
        }
        qandF.isFollowing=flag1;
        for(Answer a :list2){
            if(a.getAnswerId()==Id)
                flag2=1;
        }
        qandF.isRec=flag2;
        User owner = accountService.getUserById(String.valueOf(answer.getOwnerId()));
        qandF.userName = owner.getUserName();
        qandF.introduction = owner.getIntroduction();
        return RespUtil.ok(qandF);
    }

    /**
     * 关注回答接口
     *
     * @return Result
     */
    @RequestMapping(value = "followAnswer",method = RequestMethod.POST)
    @ResponseBody
    public Result followQuestion(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer answerId = (Integer) maps.get("answerId");
            Answer answer = answerService.getAnswerById(answerId);
            if(answer == null){
                return Result.fail("回答不存在");
            }
            AnswerFollow answerFollow= answerService.getAnswerFollow(userId,answerId);
            if(answerFollow!= null){
                return Result.fail("不可重复关注");
            }

            answerService.followAnswer(userId,answerId);

            return Result.succ("关注成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("关注失败");
        }
    }

    /**
     * 取消关注问题接口
     *
     * @return Question
     */
    @RequestMapping(value = "unfollowAnswer",method = RequestMethod.POST)
    @ResponseBody
    public Result unfollowAnswer(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer answerId = (Integer) maps.get("answerId");
            Answer answer = answerService.getAnswerById(answerId);
            if(answer == null){
                return Result.fail("问题不存在");
            }
            AnswerFollow answerFollow = answerService.getAnswerFollow(userId,answerId);
            if(answerFollow==null){
                return Result.fail("未关注此回答");
            }
            answerService.unfollowAnswer(userId,answerId);

            return Result.succ("取消关注成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("取消关注失败");
        }
    }

    /**
     * 获取此用户关注的回答
     * @param userId
     * @return Question
     */
    @GetMapping("/getFollowedAnswer")
    public Resp getFollowedAnswer(@RequestParam("userId") String userId){
        Integer Id = Integer.parseInt(userId);
        List<Answer> answers = answerService.getFollowedAnswer(Id);
        List<RandF> RandFs = new ArrayList<>();

        List<Answer> list1 = answerService.getFollowedAnswer(Id);
        List<Answer> list2 = answerService.getRecedAnswer(Id);
        for(Answer answer:answers){
            RandF tmp = new RandF();
            BeanUtils.copyProperties(answer, tmp);
            int flag1=0,flag2=0;
            for(Answer a :list1){
                if(a.getAnswerId()==answer.getAnswerId())
                    flag1=1;
            }
            tmp.isFollowing=flag1;
            for(Answer a :list2){
                if(a.getAnswerId()==answer.getAnswerId())
                    flag2=1;
            }
            tmp.isRec=flag2;
            User owner = accountService.getUserById(String.valueOf(answer.getOwnerId()));
            tmp.userName = owner.getUserName();
            tmp.introduction = owner.getIntroduction();
            RandFs.add(tmp);
        }

        return RespUtil.ok(RandFs);

    }



    /**
     * 推荐回答接口
     *
     * @return Result
     */
    @RequestMapping(value = "recAnswer",method = RequestMethod.POST)
    @ResponseBody
    public Result recAnswer(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer answerId = (Integer) maps.get("answerId");
            Answer answer = answerService.getAnswerById(answerId);
            if(answer == null){
                return Result.fail("回答不存在");
            }
            AnswerRec answerRec= answerService.getAnswerRec(userId,answerId);
            if(answerRec!= null){
                return Result.fail("不可重复推荐");
            }

            answerService.recAnswer(userId,answerId);

            return Result.succ("推荐成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("推荐失败");
        }
    }

    /**
     * 取消关注回答接口
     *
     * @return Result
     */
    @RequestMapping(value = "unrecAnswer",method = RequestMethod.POST)
    @ResponseBody
    public Result unrecAnswer(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer answerId = (Integer) maps.get("answerId");
            Answer answer = answerService.getAnswerById(answerId);
            if(answer == null){
                return Result.fail("回答不存在");
            }
            AnswerRec answerRec = answerService.getAnswerRec(userId,answerId);
            if(answerRec==null){
                return Result.fail("未推荐此回答");
            }
            answerService.unrecAnswer(userId,answerId);

            return Result.succ("取消关注成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("取消关注失败");
        }
    }

    /**
     * 获取此问题的回答
     * @param questionId
     * @return Question
     */
    @GetMapping("/getAnswers")
    public Resp getAnswers(@RequestParam("userId") String userId,@RequestParam("questionId") String questionId){
        Integer uId = Integer.parseInt(userId);
        Integer qId = Integer.parseInt(questionId);
        List<Answer> answers = answerService.getAnswers(qId);
        List<RandF> RandFs = new ArrayList<>();

        List<Answer> list1 = answerService.getFollowedAnswer(uId);
        List<Answer> list2 = answerService.getRecedAnswer(uId);
        for(Answer answer:answers){
            RandF tmp = new RandF();
            BeanUtils.copyProperties(answer, tmp);
            int flag1=0,flag2=0;
            for(Answer a :list1){
                if(a.getAnswerId()==answer.getAnswerId())
                    flag1=1;
            }
            tmp.isFollowing=flag1;
            for(Answer a :list2){
                if(a.getAnswerId()==answer.getAnswerId())
                    flag2=1;
            }
            tmp.isRec=flag2;
            User owner = accountService.getUserById(String.valueOf(answer.getOwnerId()));
            tmp.userName = owner.getUserName();
            tmp.introduction = owner.getIntroduction();
            RandFs.add(tmp);
        }

        return RespUtil.ok(RandFs);

    }
}
