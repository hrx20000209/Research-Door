package com.example.researchgate.Controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Entity.Question;
import com.example.researchgate.Entity.User;
import com.example.researchgate.Entity.QuestionFollow;
import com.example.researchgate.Entity.QuestionRec;
import com.example.researchgate.Service.Impl.QuestionService;
import com.example.researchgate.Service.Impl.AccountService;
import com.example.researchgate.Utils.RespUtil;
import com.example.researchgate.Utils.Result;
import com.example.researchgate.enums.CodeEnum;

import com.example.researchgate.model.Resp;
import com.example.researchgate.model.Ro.QuestionRo;
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
@RequestMapping("/question")
@Slf4j
public class QuestionController {
    @Data
    class RandF{
        int isFollowing;
        int isRec;
        int questionId;
        String title;
        String description;
        int followNum;
        int ownerId;
        int recNum;
        int ansNum;
        int readNum;
        String keywords;
        Date createTime;
        String userName;
        String introduction;
    }


    @Autowired
    private QuestionService questionService;
    @Autowired
    private AccountService accountService;
    /**
     * 添加问题接口
     * @param questionRo
     * @return Question
     */
    @RequestMapping(value = "addQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Resp addQuestion(@RequestBody QuestionRo questionRo) {
        try {
            int id = questionService.addQuestion(questionRo);
            if(id!=0){
                Question question = questionService.getQuestionById(id);
                return RespUtil.ok(question);
            }
            else{
                return RespUtil.fail(CodeEnum.ADD_QUESTION_FAIL);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.ADD_QUESTION_FAIL);
        }
    }
    /**
     * 删除问题接口
     * @param questionRo
     * @return Question
     */
    @RequestMapping(value = "delQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Result delQuestion(@RequestBody QuestionRo questionRo) {
        try {
            questionService.delQuestion(questionRo);

            return Result.succ("删除成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("删除失败");
        }
    }
    /**
     * 更新问题接口
     * @param questionRo
     * @return Question
     */
    @RequestMapping(value = "updateQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Resp updateQuestion(@RequestBody QuestionRo questionRo) {
        try {
            int id = questionService.updateQuestion(questionRo);
            if(id!=0){
                Question question = questionService.getQuestionById(id);
                return RespUtil.ok(question);
            }
            else{
                return RespUtil.fail(CodeEnum.ADD_QUESTION_FAIL);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.ADD_QUESTION_FAIL);
        }
    }
    /**
     * 获取问题接口
     * @param questionId
     * @return Question
     */
    @GetMapping("/getQuestion")
    @ResponseBody
    public Resp getQuestion(@RequestParam("questionId") String questionId,@RequestParam("userId") String userId){

        RandF qandF = new RandF();
        Integer Id = Integer.parseInt(questionId);
        Integer userid = Integer.parseInt(userId);
        Question question = questionService.getQuestionById(Id);
        BeanUtils.copyProperties(question, qandF);
        List<Question> list1 = questionService.getFollowedQuestion(userid);
        List<Question> list2 = questionService.getRecedQuestion(userid);
        int flag1=0,flag2=0;
        for(Question q :list1){
            if(q.getQuestionId()==Id)
                flag1=1;
        }
        qandF.isFollowing=flag1;
        for(Question q :list2){
            if(q.getQuestionId()==Id)
                flag2=1;
        }
        qandF.isRec=flag2;
        User owner = accountService.getUserById(String.valueOf(question.getOwnerId()));
        qandF.userName = owner.getUserName();
        qandF.introduction = owner.getIntroduction();
        if (question == null)
            return RespUtil.fail(CodeEnum.GET_QUESTION_FAIL);
        return RespUtil.ok(qandF);
    }
    /**
     * 获取此用户提问的问题
     * @param userId
     * @return Question
     */
    @GetMapping("/getAskedQuestion")
    public Resp getAskedQuestion(@RequestParam("userId") String userId){
        Integer Id = Integer.parseInt(userId);
        List<Question> questions = questionService.getAskedQuestion(Id);
        List<RandF> RandFs = new ArrayList<>();

        List<Question> list1 = questionService.getFollowedQuestion(Id);
        List<Question> list2 = questionService.getRecedQuestion(Id);
        for(Question question:questions){
            RandF tmp = new RandF();
            BeanUtils.copyProperties(question, tmp);
            int flag1=0,flag2=0;
            for(Question q :list1){
                if(q.getQuestionId()==question.getQuestionId())
                    flag1=1;
            }
            tmp.isFollowing=flag1;
            for(Question q :list2){
                if(q.getQuestionId()==question.getQuestionId())
                    flag2=1;
            }
            tmp.isRec=flag2;
            User owner = accountService.getUserById(String.valueOf(question.getOwnerId()));
            tmp.userName = owner.getUserName();
            tmp.introduction = owner.getIntroduction();
            RandFs.add(tmp);
        }

        return RespUtil.ok(RandFs);
    }

    /**
     * 关注问题接口
     *
     * @return Question
     */
    @RequestMapping(value = "followQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Result followQuestion(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer questionId = (Integer) maps.get("questionId");
            Question question = questionService.getQuestionById(questionId);
            if(question == null){
                return Result.fail("问题不存在");
            }
            QuestionFollow questionFollow= questionService.getQuestionFollow(userId,questionId);
            if(questionFollow!= null){
                return Result.fail("不可重复关注");
            }

            questionService.followQuestion(userId,questionId);

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
    @RequestMapping(value = "unfollowQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Result unfollowQuestion(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer questionId = (Integer) maps.get("questionId");
            Question question = questionService.getQuestionById(questionId);
            if(question == null){
                return Result.fail("问题不存在");
            }
            QuestionFollow questionFollow = questionService.getQuestionFollow(userId,questionId);
            if(questionFollow==null){
                return Result.fail("未关注此问题");
            }
            questionService.unfollowQuestion(userId,questionId);

            return Result.succ("取消关注成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("取消关注失败");
        }
    }

    /**
     * 获取此用户提问的问题
     * @param userId
     * @return Question
     */
    @GetMapping("/getFollowedQuestion")
    public Resp getFollowedQuestion(@RequestParam("userId") String userId){
        Integer Id = Integer.parseInt(userId);
        List<Question> questions = questionService.getFollowedQuestion(Id);
        List<RandF> RandFs = new ArrayList<>();

        List<Question> list1 = questionService.getFollowedQuestion(Id);
        List<Question> list2 = questionService.getRecedQuestion(Id);
        for(Question question:questions){
            RandF tmp = new RandF();
            BeanUtils.copyProperties(question, tmp);
            int flag1=0,flag2=0;
            for(Question q :list1){
                if(q.getQuestionId()==question.getQuestionId())
                    flag1=1;
            }
            tmp.isFollowing=flag1;
            for(Question q :list2){
                if(q.getQuestionId()==question.getQuestionId())
                    flag2=1;
            }
            tmp.isRec=flag2;
            User owner = accountService.getUserById(String.valueOf(question.getOwnerId()));
            tmp.userName = owner.getUserName();
            tmp.introduction = owner.getIntroduction();
            RandFs.add(tmp);
        }

        return RespUtil.ok(RandFs);

    }

    /**
     * 搜索问题接口
     * @param keyword
     * @return Question
     */
    @RequestMapping(value = "searchQuestion",method = RequestMethod.GET)
    @ResponseBody
    public Resp searchQuestion(@RequestParam("keyword") String keyword,@RequestParam("userId") String userId) {
        try {
            Integer Id = Integer.parseInt(userId);
            List<Question> list = questionService.searchQuestion(keyword);
            List<RandF> RandFs = new ArrayList<>();

            List<Question> list1 = questionService.getFollowedQuestion(Id);
            List<Question> list2 = questionService.getRecedQuestion(Id);
            for(Question question:list){
                RandF tmp = new RandF();
                BeanUtils.copyProperties(question, tmp);
                int flag1=0,flag2=0;
                for(Question q :list1){
                    if(q.getQuestionId()==question.getQuestionId())
                        flag1=1;
                }
                tmp.isFollowing=flag1;
                for(Question q :list2){
                    if(q.getQuestionId()==question.getQuestionId())
                        flag2=1;
                }
                tmp.isRec=flag2;
                User owner = accountService.getUserById(String.valueOf(question.getOwnerId()));
                tmp.userName = owner.getUserName();
                tmp.introduction = owner.getIntroduction();
                RandFs.add(tmp);
            }

            return RespUtil.ok(RandFs);
            
        } catch (Exception e) {
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.SEARCH_QUESTION_FAIL);
        }
    }

    /**
     * 推荐问题接口
     *
     * @return Question
     */
    @RequestMapping(value = "recQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Result recQuestion(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer questionId = (Integer) maps.get("questionId");
            Question question = questionService.getQuestionById(questionId);
            if(question == null){
                return Result.fail("问题不存在");
            }
            QuestionRec questionRec= questionService.getQuestionRec(userId,questionId);
            if(questionRec!= null){
                return Result.fail("不可重复推荐");
            }

            questionService.recQuestion(userId,questionId);

            return Result.succ("推荐成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("推荐失败");
        }
    }

    /**
     * 取消关注问题接口
     *
     * @return Question
     */
    @RequestMapping(value = "unrecQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Result unrecQuestion(@RequestBody Map<String, Object> maps) {
        try {
            Integer userId = (Integer) maps.get("userId");
            Integer questionId = (Integer) maps.get("questionId");
            Question question = questionService.getQuestionById(questionId);
            if(question == null){
                return Result.fail("问题不存在");
            }
            QuestionRec questionRec = questionService.getQuestionRec(userId,questionId);
            if(questionRec==null){
                return Result.fail("未推荐此问题");
            }
            questionService.unrecQuestion(userId,questionId);

            return Result.succ("取消关注成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("取消关注失败");
        }
    }

    @RequestMapping(value = "readQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Result readQuestion(@RequestBody Map<String, Object> maps) {
        try {
            Integer questionId = (Integer) maps.get("questionId");
            Question question = questionService.getQuestionById(questionId);
            if(question == null){
                return Result.fail("问题不存在");
            }


            questionService.readQuestion(questionId);

            return Result.succ("阅读成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("阅读失败");
        }
    }

}
