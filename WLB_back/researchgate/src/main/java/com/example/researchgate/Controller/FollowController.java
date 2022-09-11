package com.example.researchgate.Controller;

import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Entity.Follow;
import com.example.researchgate.Service.AchievementService;
import com.example.researchgate.Service.FollowService;
import com.example.researchgate.Utils.EntityToMap;
import com.example.researchgate.Utils.RespUtil;
import com.example.researchgate.Utils.Result;
import com.example.researchgate.enums.CodeEnum;
import com.example.researchgate.model.Resp;
import com.example.researchgate.model.Ro.UserIdRo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class FollowController {

    @Autowired
    AchievementService achievementService;

    @Autowired
    FollowService followService;

    @PostMapping("/achievement/followAch")
    public Result followAch(@RequestBody Map<String, Object> maps){
        Integer achId = Integer.valueOf(maps.get("id").toString());
        Integer userId = Integer.valueOf(maps.get("userId").toString());
        if (userId==null){
            return Result.fail("用户id错误");
        }
        Achievement achievement = achievementService.getAch(achId);
        if (achievement==null){
            return Result.fail("作品不存在");
        }
        String followers = achievement.getAchFollows();
        if (followers==null) followers = "" + userId;
        else followers = followers+";" + userId;
        achievement.setAchFollows(followers);
        achievement.setFollowedCnt(achievement.getFollowedCnt()+1);
        achievementService.setAch(achievement);
        Follow follow = new Follow();
        follow.setAchId(achId);
        follow.setUserId(userId);
        followService.addFollow(follow);
        return Result.succ("关注成功");
    }

    @PostMapping("/achievement/cancelFollow")
    public Result cancelFollow(@RequestBody Map<String, Object>maps){
        Integer achId = Integer.valueOf(maps.get("id").toString());
        Integer userId = Integer.valueOf(maps.get("userId").toString());
        if (userId==null){
            return Result.fail("用户id错误");
        }
        Achievement achievement = achievementService.getAch(achId);
        if (achievement==null){
            return Result.fail("作品不存在");
        }
        String followers = achievement.getAchFollows();
        if (followers==null) {
            return Result.fail("未关注");
        }
        String[] f = followers.split(";");
        String newFo = "";
        for (String id : f){
            if (!id.equals(("" + userId)))
                if (newFo.length()==0)
                    newFo = id;
                else newFo = newFo + ";" + id;
        }
        achievement.setAchFollows(newFo);
        achievement.setFollowedCnt(achievement.getFollowedCnt()-1);
        achievementService.setAch(achievement);
        followService.delFollow(userId, achId);
        return Result.succ("取关成功");
    }


    @PostMapping("/account/getFollowingAch")
    public Result getFollowingAchievement(@RequestBody Map<String, Object> maps) {
        String temp = (String) maps.get("userId");
        Integer userId = Integer.valueOf(temp);
        List<Follow> followList = followService.getFollowByUid(userId);
        List<Map<String, Object>> achievementList = new ArrayList<>();
        if (followList==null)
            return Result.fail("没有关注成果");
        for (Follow follow : followList) {
            Achievement achievement = achievementService.getAch(follow.getAchId());
            if (achievement!=null)
                achievementList.add(EntityToMap.object2Map(achievement));
        }
        return Result.succ("成功", achievementList);
    }
//    @GetMapping("/searchAchFollows")
//    public Result searchAchFollows(@RequestParam("achId") Object achid){
//        Integer achId = (Integer) achid;
//        Achievement achievement = achievementService.getAch(achId);
//        if (achievement==null){
//            return Result.fail("作品不存在");
//        }
//        Map<Integer, String> followers = new HashMap<>();
//        if (achievement.getIsFollowed()==0){
//            return Result.succ("无关注",followers);
//        }
//        String[] f = achievement.getAchFollows().split(";");
//        for (String id : f){
//            Integer userId = Integer.valueOf(id);
//            User user  = userService.getUserById(userId);
//            followers.put(userId, user.getUserName());
//        }
//        return Result.succ("查找完毕", followers);
//    }
}
