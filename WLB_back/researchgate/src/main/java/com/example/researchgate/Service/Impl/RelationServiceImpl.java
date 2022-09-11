package com.example.researchgate.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.researchgate.Dao.AchievementDao;
import com.example.researchgate.Dao.RelationDao;
import com.example.researchgate.Dao.UserDao;
import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Entity.Relation;
import com.example.researchgate.Entity.User;
import com.example.researchgate.Service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    RelationDao relationDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AchievementDao achievementDao;

    @Override
    public void addRelation(Relation relation){
        relationDao.insert(relation);
    }

    @Override
    public void delRelation(Integer userId, Integer achId){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("user_id", userId).eq("achieve_id", achId);
        relationDao.delete(relationQueryWrapper);
    }

    @Override
    public Integer isAuthor(Integer userId, Integer achId){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("user_id", userId).eq("achieve_id", achId);
        List<Relation> relationList = relationDao.selectList(relationQueryWrapper);
        if (relationList==null  || relationList.size()==0){
            return 0;
        }else return 1;
    }

    @Override
    public Boolean isAuthenticated(Relation relation){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("user_id", relation.getUserId())
                .eq("achieve_id", relation.getAchieveId())
                .eq("name", relation.getName());
        List<Relation> relationList = relationDao.selectList(relationQueryWrapper);
        if (relationList==null  || relationList.size()==0){
            return false;
        }else return true;
    }

    @Override
    public User findUserByAchAndName(String name, Integer achId){
        QueryWrapper<Relation> relationQueryWrapper =  new QueryWrapper<>();
        relationQueryWrapper.eq("achieve_id", achId)
                .eq("name", name);
        List<Relation> relationList = relationDao.selectList(relationQueryWrapper);
        if (relationList==null || relationList.size()==0)
            return null;
        Relation relation = relationList.get(0);
        return userDao.selectById(relation.getUserId());
    }

    @Override
    public List<User> findUserByAch(Integer achId){
        QueryWrapper<Relation> relationQueryWrapper =  new QueryWrapper<>();
        relationQueryWrapper.eq("achieve_id", achId);
        List<Relation> relationList = relationDao.selectList(relationQueryWrapper);
        List<User> userList = new ArrayList<>();
        if (relationList==null || relationList.size()==0)
            return null;
        for(Relation relation : relationList){
            User user = userDao.selectById(relation.getUserId());
            if (user!=null)
                userList.add(user);
        }
        return userList;
    }

    @Override
    public List<Achievement> findAchievementByUserId(Integer userId){
        QueryWrapper<Relation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("user_id", userId);
        List<Relation> relationList = relationDao.selectList(relationQueryWrapper);
        List<Achievement> achievementList = new ArrayList<>();
        if (relationList==null || relationList.size()==0)
            return null;
        for(Relation relation : relationList){
            Achievement achievement = achievementDao.selectById(relation.getAchieveId());
            if (achievement!=null)
                achievementList.add(achievement);
        }
        return achievementList;
    }
}
