package com.example.researchgate.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.researchgate.Dao.AchievementDao;
import com.example.researchgate.Dao.UserDao;
import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Service.AchievementService;
import com.example.researchgate.Utils.EntityToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    UserDao userDao;

    @Autowired
    AchievementDao achievementDao;

    @Override
    public Integer addAch(Achievement achievement){
        achievementDao.insert(achievement);
        QueryWrapper<Achievement> achievementQueryWrapper = new QueryWrapper<>();
        achievementQueryWrapper.eq("authors", achievement.getAuthors())
                .eq("user_id", achievement.getUserId())
                .eq("title", achievement.getTitle())
                .eq("type", achievement.getType());
        List<Achievement> achievementList = achievementDao.selectList(achievementQueryWrapper);
        if (achievementList!=null && achievementList.size()!=0)
            return achievementList.get(0).getAchId();
        return 0;
    }

    @Override
    public void delAch(Integer achId){
        achievementDao.deleteById(achId);
    }

    @Override
    public Achievement getAch(Integer achId){
        return achievementDao.selectById(achId);
    }

    @Override
    public void setAch(Achievement achievement){
        achievementDao.updateById(achievement);
    }

    @Override
    public List<Achievement> getAchByWriter(String keyword){
        QueryWrapper<Achievement> achievementQueryWrapper = new QueryWrapper<>();
        achievementQueryWrapper.like("authors", keyword);
        return achievementDao.selectList(achievementQueryWrapper);
    }

    @Override
    public List<Achievement> getAchByAbstract(String keyword){
        QueryWrapper<Achievement> achievementQueryWrapper = new QueryWrapper<>();
        achievementQueryWrapper.like("description", keyword);
        return achievementDao.selectList(achievementQueryWrapper);
    }

    @Override
    public List<Achievement> getAchByKeyword(String keyword, String type){
        QueryWrapper<Achievement> achievementQueryWrapper = new QueryWrapper<>();
        if (type.equals("0")) {
            achievementQueryWrapper.like("authors", keyword).
                    or().like("description", keyword).
                    or().like("title", keyword);
        }else{
            achievementQueryWrapper.eq("type", type).and(
                    i -> i.like("authors", keyword).
                    or().like("description", keyword).
                    or().like("title", keyword));
        }
        return achievementDao.selectList(achievementQueryWrapper);
    }

    @Override
    public List<Achievement> getAchByUserId(Integer userId){
        QueryWrapper<Achievement> achievementQueryWrapper = new QueryWrapper<>();
        achievementQueryWrapper.eq("user_id", userId);
        return achievementDao.selectList(achievementQueryWrapper);
    }
}
