package com.example.researchgate.Service;

import com.example.researchgate.Entity.Achievement;

import java.util.List;

public interface AchievementService {
    Integer addAch(Achievement achievement);
    void delAch(Integer achId);
    Achievement getAch(Integer achId);
    List<Achievement> getAchByWriter(String keyword);
    List<Achievement> getAchByAbstract(String keyword);
    List<Achievement> getAchByKeyword(String keyword, String type);
    void setAch(Achievement achievement);
    List<Achievement> getAchByUserId(Integer userId);
}
