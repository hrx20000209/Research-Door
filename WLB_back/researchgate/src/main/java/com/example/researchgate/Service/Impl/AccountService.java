package com.example.researchgate.Service.Impl;

import com.example.researchgate.Dao.MessaageDao;
import com.example.researchgate.Dao.NoticeDao;
import com.example.researchgate.Dao.UserDao;
import com.example.researchgate.Dao.UserFollowDao;
import com.example.researchgate.Entity.Message;
import com.example.researchgate.Entity.Notice;
import com.example.researchgate.Entity.User;
import com.example.researchgate.Entity.UserFollow;
import com.example.researchgate.Utils.DateTool;
import com.example.researchgate.Utils.InstitutionUtil;
import com.example.researchgate.model.Ro.*;
import com.example.researchgate.model.Vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class AccountService {
    @Autowired
    UserDao userDao;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private MessaageDao messaageDao;

    @Autowired
    private UserFollowDao userFollowDao;

    @Transactional(rollbackFor = Exception.class)
    public void addAccount(AccountRo accountRo) throws Exception{
        User user = new User();
        BeanUtils.copyProperties(accountRo, user);
        String institue = InstitutionUtil.getInstitution(user.getUserEmail());
        if(institue.equals("Other Institution")){
            throw new Exception("机构不承认");
        }
        user.setUserInstitution(institue);
        userDao.insert(user);
    }

    public User getByAccount(String email){
        User user = userDao.getUserByEmail(email);
        if(user!=null){
            return user;
        }
        return null;
    }

    public User verifyLogin(LoginRo loginRo) throws Exception
    {

        String userId = loginRo.getUserId();
        String userEmail = loginRo.getUserEmail();
        String password = loginRo.getPassword();
        if( userId == null && userEmail == null) {
            throw new Exception("没有输入用户必要userId或者UserEmail");
        }
        else if (userId != null){
            User user =  userDao.selectById(userId);
            if( user!=null&&user.getPassword().equals(password)){
                return user;
            } else{
                throw new Exception("密码错误");
            }
        }
        else{
            User user = userDao.getUserByEmail(userEmail);
            if( user!=null&&user.getPassword().equals(password)){
                return user;
            } else{
                throw new Exception("密码错误");
            }
        }
    }

    public String editUserIntroduction(IntroductionRo introductionRo) throws Exception{
        Notice notice = new Notice();
        notice.setNotContent("高野淇中奖了！");
        noticeDao.insert(notice);
        Integer res =  userDao.updateIntroduction(introductionRo.getIntroduction(),introductionRo.getUserId());
        if(res == 0){
            throw new Exception("更新失败");
        }
        String userId = introductionRo.getUserId();
        User user = userDao.selectByUserId(userId);
        return user.getIntroduction();
    }

    public void sendMessage(SendMsgRo sendMsgRo) throws Exception{
        SendMsgFrom sendMsgFormRo = sendMsgRo.getSendMsgForm();
        Message message = new Message();
        message.setMsgFrom(sendMsgRo.getUserId());
        List<User> toUserList = userDao.getUserListByUserName(sendMsgFormRo.getMsgTo());
        if(toUserList==null || toUserList.size() ==0){
            throw new Exception("接收方不存在");
        }
        User toUser = toUserList.get(0);
        message.setMsgTo(toUser.getUserId().toString());
        message.setMsgContent(sendMsgFormRo.getMsgContent());
        message.setMsgSubject(sendMsgFormRo.getMsgSubject());
        String curTime = DateTool.getCurTime();
        Date curDate = DateTool.string2Date(curTime,1);
        message.setMsgDate(curDate);
        messaageDao.insert(message);
    }

    public GetDialogVo getDialog(GetDialogRo getDialogRo) throws Exception{
        List<User> toUserList = userDao.getUserListByUserName(getDialogRo.getMsgTo());
        if(toUserList==null || toUserList.size() ==0){
            throw new Exception("接收方不存在");
        }
        User toUser = toUserList.get(0);
        List<Message> messageList = messaageDao.getDialog(getDialogRo.getMsgSubject(),getDialogRo.getUserId(),toUser.getUserId().toString());
        GetDialogVo getDialogVo =new GetDialogVo();
        getDialogVo.setMsgSubject(getDialogRo.getMsgSubject());
        List<DialogVo> dialogVoList = new ArrayList<>();
        for(Message message:messageList){
            DialogVo dialogVo =  new DialogVo();
            dialogVo.setMsgContent(message.getMsgContent());
            dialogVo.setMsgDate(message.getMsgDate());
            User tempSender = userDao.selectByUserId(message.getMsgFrom());
            dialogVo.setMsgFrom(tempSender.getUserName());
            dialogVoList.add(dialogVo);
        }
        getDialogVo.setMsgList(dialogVoList);
        return getDialogVo;
    }

    public List<MsgGroupVo> getMsgGroupById(String userId){
        List<String> groupList = messaageDao.getUniqueGroup(userId);
        List<MsgGroupVo> msgGroupVoList =new ArrayList<>();
        for(String group : groupList){
            Message message = messaageDao.getLastMessage(group).get(0);
            MsgGroupVo msgGroupVo =new MsgGroupVo();
            User tempGetter = userDao.selectByUserId(message.getMsgTo());
            msgGroupVo.setMsgTo(tempGetter.getUserName());
            msgGroupVo.setMsgDate(message.getMsgDate());
            msgGroupVo.setMsgLast(message.getMsgContent());
            msgGroupVo.setMsgSubject(message.getMsgSubject());
            if(message.getMsgFrom().equals(userId)){
                User partner = userDao.selectByUserId(message.getMsgTo());
                msgGroupVo.setMsgPartner(partner.getUserName());
            } else{
                User partner = userDao.selectByUserId(message.getMsgFrom());
                msgGroupVo.setMsgPartner(partner.getUserName());
            }
            msgGroupVoList.add(msgGroupVo);
        }
        return msgGroupVoList;
    }

    public User getUserById(String userId){
        return userDao.selectByUserId(userId);
    }

    public List<UserIfFollowVo> getUserListByName(String userName,String userId){
        List<User> userList = userDao.getMaxUserListByUserName(userName);
        List<UserIfFollowVo> userIfFollowVos = new ArrayList<>();
        for(User user : userList){
            UserIfFollowVo userIfFollowVo = new UserIfFollowVo();
            BeanUtils.copyProperties(user, userIfFollowVo);
            List<UserFollow> userFollows= userFollowDao.getFollow(userId,user.getUserId().toString());
            if(userFollows!=null && userFollows.size()>=1){
                userIfFollowVo.setFollow(true);
            }
            else{
                userIfFollowVo.setFollow(false);
            }
            userIfFollowVos.add(userIfFollowVo);
        }
        return userIfFollowVos;
    }

    public List<MsgGroupVo> getMsgSentGroupById(String userId){
        List<String> groupList = messaageDao.getUniqueGroup(userId);
        List<MsgGroupVo> msgGroupVoList =new ArrayList<>();
        for(String group : groupList){
            List<Message> sentList = messaageDao.getUserSent(group,userId);
            if(sentList==null || sentList.size() ==0){
                continue;
            }
            Message message = messaageDao.getLastMessage(group).get(0);
            MsgGroupVo msgGroupVo =new MsgGroupVo();
            User tempGetter = userDao.selectByUserId(message.getMsgTo());
            msgGroupVo.setMsgTo(tempGetter.getUserName());
            msgGroupVo.setMsgDate(message.getMsgDate());
            msgGroupVo.setMsgLast(message.getMsgContent());
            msgGroupVo.setMsgSubject(message.getMsgSubject());
            if(message.getMsgFrom().equals(userId)){
                User partner = userDao.selectByUserId(message.getMsgTo());
                msgGroupVo.setMsgPartner(partner.getUserName());
            } else{
                User partner = userDao.selectByUserId(message.getMsgFrom());
                msgGroupVo.setMsgPartner(partner.getUserName());
            }
            msgGroupVoList.add(msgGroupVo);
        }
        return msgGroupVoList;
    }

    public void followUser(UserFollowingRo userFollowBack){
        UserFollow userFollow = new UserFollow();
        BeanUtils.copyProperties(userFollowBack, userFollow);
        userFollowDao.insert(userFollow);
        User tempFollower = userDao.selectByUserId(userFollow.getUserToFollowId());
        Notice notice = new Notice();
        String curTime = DateTool.getCurTime();
        Date curDate = DateTool.string2Date(curTime,1);
        notice.setNotDate(curDate);
        notice.setNotContentType(1);
        notice.setNotUserId(Integer.parseInt(tempFollower.getUserId().toString()));
        User tempOnwer = userDao.selectByUserId(userFollow.getUserId());
        String notContent = "用户"+tempOnwer.getUserName().toString()+"关注了你，快去看看吧";
        notice.setNotContent(notContent);
        noticeDao.insert(notice);
        return;
    }

    public void unFollowUser(UserFollowingRo userFollow){
        userFollowDao.delUserFollow(userFollow.getUserId(),userFollow.getUserToUnfollowId());
        return;
    }

    public List<UserFollowRo> getFollowList(UserIdRo userIdRo){
        List<UserFollow> userFollows = userFollowDao.getFollowList(userIdRo.getUserId());
        List<UserFollowRo> userFollowRos = new ArrayList<>();
        for(UserFollow userFollow : userFollows){
            String tempUserId = userFollow.getUserToFollowId();
            User tempFollower = userDao.selectByUserId(tempUserId);
            UserFollowRo userFollowRo = new UserFollowRo();
            userFollowRo.setUserId(tempFollower.getUserId().toString());
            userFollowRo.setUserEmail(tempFollower.getUserEmail());
            userFollowRo.setUserName(tempFollower.getUserName());
            userFollowRo.setUserInstitution(tempFollower.getUserInstitution());
            userFollowRos.add(userFollowRo);
        }
        return userFollowRos;
    }

    public Integer getFollowCnt(String userId){
        return userFollowDao.countFollow(userId);
    }

    public void sendTitleNotice (RequireResearchRo requireResearchRo){
        Notice notice = new Notice();
        String curTime = DateTool.getCurTime();
        Date curDate = DateTool.string2Date(curTime,1);
        notice.setNotDate(curDate);
        notice.setNotContentType(2);
        notice.setNotUserId(Integer.parseInt(requireResearchRo.getUserId()));
        String notContent = "请上传文献"+requireResearchRo.getTitle();
        notice.setNotContent(notContent);
        noticeDao.insert(notice);
        return;
    }

    public List<UserFollowRo> getFollowerist(UserIdRo userIdRo){
        List<UserFollow> userFollows = userFollowDao.getFollowerList(userIdRo.getUserId());
        List<UserFollowRo> userFollowRos = new ArrayList<>();
        for(UserFollow userFollow : userFollows){
            String tempUserId = userFollow.getUserId();
            User tempFollower = userDao.selectByUserId(tempUserId);
            UserFollowRo userFollowRo = new UserFollowRo();
            userFollowRo.setUserId(tempFollower.getUserId().toString());
            userFollowRo.setUserEmail(tempFollower.getUserEmail());
            userFollowRo.setUserName(tempFollower.getUserName());
            userFollowRo.setUserInstitution(tempFollower.getUserInstitution());
            List<UserFollow> userFollowsCheck = userFollowDao.getFollow(userIdRo.getUserId(),tempFollower.getUserId().toString());
            if(userFollowsCheck==null||userFollowsCheck.size()==0){
                userFollowRo.setFollow(false);
            }
            userFollowRo.setFollow(true);
            userFollowRos.add(userFollowRo);
        }
        return userFollowRos;
    }

    public GraphVo generateGraph(String userId){
        GraphVo graphVo = new GraphVo();
        User tempUser = userDao.selectByUserId(userId);
        Node tempNode = new Node();
        //判重用的
        HashSet<String> hashSet = new HashSet<>();
        List<User> sameInstituteUser = userDao.getSameInstitution(tempUser.getUserInstitution());
        List<Node> nodes = new ArrayList<>();
        List<Links> links = new ArrayList<>();
        for(User user : sameInstituteUser){
            Node node = new Node();
            node.setId(user.getUserId().toString());
            node.setInstitution(user.getUserInstitution());
            node.setName(user.getUserName());
            nodes.add(node);
        }
        for(Node node1 : nodes){
            for(Node node2 : nodes){
                String key1 = node1.getId()+"&&"+node2.getId();
                String key2 = node2.getId()+"&&"+node1.getId();
                if(!node1.getId().equals(node2.getId())&&!hashSet.contains(key1)&&!hashSet.contains(key2)){
                    Links link = new Links();
                    link.setSource(node1.getId());
                    link.setTarget(node2.getId());
                    String newKey1 = node1.getId()+"&&"+node2.getId();
                    String newKey2 = node2.getId()+"&&"+node1.getId();
                    hashSet.add(newKey1);
                    hashSet.add(newKey2);
                    links.add(link);
                }
            }
        }
        graphVo.setLinks(links);
        graphVo.setNodes(nodes);
        return graphVo;
    }

}