package com.example.researchgate.Service;

import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Entity.Follow;

import java.util.List;

public interface FollowService {
    void addFollow(Follow follow);
    void delFollow(Integer userId, Integer achId);
    Integer isFollow(Integer userId, Integer achId);
    List<Follow> getFollowByUid(Integer UserId);
}
