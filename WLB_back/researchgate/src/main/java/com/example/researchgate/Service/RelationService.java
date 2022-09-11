package com.example.researchgate.Service;

import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Entity.Relation;
import com.example.researchgate.Entity.User;

import java.util.List;

public interface RelationService {
    void addRelation(Relation relation);
    void delRelation(Integer userId, Integer achId);
    Integer isAuthor(Integer userId, Integer achId);
    Boolean isAuthenticated(Relation relation);
    User findUserByAchAndName(String name, Integer achId);
    List<User> findUserByAch(Integer achId);
    List<Achievement> findAchievementByUserId(Integer userId);
}
