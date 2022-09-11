package com.example.researchgate.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.researchgate.Dao.AchievementDao;
import com.example.researchgate.Dao.FollowDao;
import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Entity.Follow;
import com.example.researchgate.Entity.User;
import com.example.researchgate.Service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;

    @Autowired
    AchievementDao achievementDao;

    @Override
    public void addFollow(Follow follow){
        followDao.insert(follow);
    }

    @Override
    public void delFollow(Integer userId, Integer achId){
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("user_id", userId);
        followQueryWrapper.eq("ach_id", achId);
        followDao.delete(followQueryWrapper);
    }

    @Override
    public Integer isFollow(Integer userId, Integer achId){
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("user_id", userId);
        followQueryWrapper.eq("ach_id", achId);
        List<Follow> followList = followDao.selectList(followQueryWrapper);
        if (followList==null || followList.size()==0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public List<Follow> getFollowByUid(Integer UserId){
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("user_id", UserId);
        List<Follow> followList = followDao.selectList(followQueryWrapper);
        if (followList==null || followList.size()==0){
            return null;
        }else{
            return followList;
        }
    }
}
