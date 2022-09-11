package com.example.researchgate.Controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.researchgate.Entity.Notice;
import com.example.researchgate.Entity.User;
import com.example.researchgate.Entity.UserFollow;
import com.example.researchgate.Service.Impl.AccountService;
import com.example.researchgate.Service.Impl.NoticeService;
import com.example.researchgate.Utils.InstitutionUtil;
import com.example.researchgate.Utils.RespUtil;
import com.example.researchgate.Utils.Result;
import com.example.researchgate.enums.CodeEnum;

import com.example.researchgate.model.Resp;
import com.example.researchgate.model.Ro.*;
import com.example.researchgate.model.Vo.GetDialogVo;
import com.example.researchgate.model.Vo.MsgGroupVo;
import com.example.researchgate.model.Vo.UserIfFollowVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户相关
 * @author zihanWang
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private NoticeService noticeService;
    /**
     * 添加账户接口
     * @param accountRo
     * @return User
     */
    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    @ResponseBody
    public Resp addAccount(@RequestBody AccountRo accountRo) {
        try {
            accountService.addAccount(accountRo);
            User user = accountService.getByAccount(accountRo.getUserEmail());
            if(user.getUserId()!=0){
                return RespUtil.ok(user);
            }
            else{
                return RespUtil.fail(CodeEnum.ADD_USER_FAIL);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.ADD_USER_FAIL);
        }
    }

    @RequestMapping(value = "logUser",method = RequestMethod.POST)
    @ResponseBody
    public Resp logUser(@RequestBody LoginRo loginRo){
        try{
            User user = accountService.verifyLogin(loginRo);
            return RespUtil.ok(user);
        } catch(Exception e){
            if(e.getMessage().equals("密码错误")){
                return RespUtil.fail(CodeEnum.PASSWORD_WRONG);
            }
            else{
                return RespUtil.fail(CodeEnum.LOGIN_MISS_CONTENT);
            }
        }
    }

    @RequestMapping(value = "getNoticeById", method = RequestMethod.GET)
    @ResponseBody
    public Resp getNoticeById(@RequestParam(value = "userId", required = false) Integer userId){
        try{
            List<Notice> noticeList = noticeService.getNoticeByUserId(userId);
            return RespUtil.ok(noticeList);
        } catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_NOTICE_FAIL);
        }
    }

    @RequestMapping(value = "editIntroduction",method = RequestMethod.POST)
    @ResponseBody
    public Resp editIntroduction(@RequestBody IntroductionRo introductionRo){
        try{
            String res = accountService.editUserIntroduction(introductionRo);
            Map<String,String> map = new HashMap<>();
            map.put("introduction", res);
            return RespUtil.ok(map);
        } catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.EDIT_INTRODUCTION_FAIL);
        }
    }

    @RequestMapping(value = "sendMsgById",method = RequestMethod.POST)
    @ResponseBody
    public Resp sendMsgById(@RequestBody SendMsgRo sendMsgRo){
        try{
            accountService.sendMessage(sendMsgRo);
            return RespUtil.ok();
        } catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.SEND_MESSAGE_FAIL);
        }
    }

    @RequestMapping(value = "getDialog",method = RequestMethod.POST)
    @ResponseBody
    public Resp sendMsgById(@RequestBody GetDialogRo getDialogRo){
        try{
            GetDialogVo getDialogVo = accountService.getDialog(getDialogRo);
            Map<String,GetDialogVo> map = new HashMap<>();
            map.put("msgBox",getDialogVo);
            return RespUtil.ok(map);
        } catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_DIALOG_FAIL);
        }
    }

    @RequestMapping(value = "getMsgGroupById",method = RequestMethod.POST)
    @ResponseBody
    public Resp getMsgGroupById(@RequestBody UserIdRo userIdRo){
        try{
            List<MsgGroupVo> msgGroupVoList = accountService.getMsgGroupById(userIdRo.getUserId());
            Map<String,List<MsgGroupVo>> map = new HashMap<>();
            map.put("msgGroupList",msgGroupVoList);
            return RespUtil.ok(map);
        } catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_MSG_GROUP_FAIL);
        }
    }


    @RequestMapping(value = "checkUserById",method = RequestMethod.POST)
    @ResponseBody
    public Resp checkUserById(@RequestBody UserIdRo userIdRo){
        try{
            String userId = userIdRo.getUserId();
            User tempUser = accountService.getUserById(userId);
            tempUser.setUserFollowerCnt(accountService.getFollowCnt(userId));
            tempUser.setUserInstitution(InstitutionUtil.getInstitution(tempUser.getUserEmail()));
            Map<String,User> map = new HashMap<>();
            map.put("userInfo",tempUser);
            return  RespUtil.ok(map);
        }catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_USER_FAIL);
        }
    }

    @RequestMapping(value = "searchUserByName",method = RequestMethod.POST)
    @ResponseBody
    public Resp searchUserByName(@RequestBody UserNameRo userNameRo){
        try{
            String userName = userNameRo.getUserName();
            List<UserIfFollowVo> userList = accountService.getUserListByName(userName,userNameRo.getUserId());
            Map<String,List<UserIfFollowVo>> map = new HashMap<>();
            map.put("userList",userList);
            return  RespUtil.ok(map);
        }catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_USER_LIST_FAIL);
        }
    }

    @RequestMapping(value = "getMsgSentGroupById",method = RequestMethod.POST)
    @ResponseBody
    public Resp getMsgSentGroupById(@RequestBody UserIdRo userIdRo){
        try{
            List<MsgGroupVo> msgGroupVoList = accountService.getMsgSentGroupById(userIdRo.getUserId());
            Map<String,List<MsgGroupVo>> map = new HashMap<>();
            map.put("msgGroupList",msgGroupVoList);
            return RespUtil.ok(map);
        } catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_MSG_GROUP_FAIL);
        }
    }

    @RequestMapping(value = "followUser",method = RequestMethod.POST)
    @ResponseBody
    public Resp followUser(@RequestBody UserFollowingRo userFollow){
        try{
            accountService.followUser(userFollow);
            return RespUtil.ok();
        }   catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.USER_FOLLOW_FAIL);
        }
    }

    @RequestMapping(value = "unfollowUser",method = RequestMethod.POST)
    @ResponseBody
    public Resp unfollowUser(@RequestBody UserFollowingRo userFollow){
        try{
            accountService.unFollowUser(userFollow);
            return RespUtil.ok();
        }   catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.UN_USER_FOLLOW_FAIL);
        }
    }

    @RequestMapping(value = "getFollowingUser",method = RequestMethod.POST)
    @ResponseBody
    public Resp getFollowingUser(@RequestBody UserIdRo userIdRo){
        try{
            List<UserFollowRo> userFollowRos = accountService.getFollowList(userIdRo);
            List<UserFollowRo> userFollowerRos = accountService.getFollowerist(userIdRo);
            Map<String,List<UserFollowRo>> map = new HashMap<>();
            map.put("followingList",userFollowRos);
            map.put("followerList",userFollowerRos);
            return RespUtil.ok(map);
        }   catch (Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_FOLLOW_LIST_FAIL);
        }
    }

    @RequestMapping(value = "requireResearch",method = RequestMethod.POST)
    @ResponseBody
    public Resp requereResearch(@RequestBody RequireResearchRo requireResearchRo){
        accountService.sendTitleNotice(requireResearchRo);
        return RespUtil.ok();
    }


}
