package com.example.researchgate.Controller;

import com.example.researchgate.Entity.Achievement;
import com.example.researchgate.Entity.Relation;
import com.example.researchgate.Service.AchievementService;
import com.example.researchgate.Service.RelationService;
import com.example.researchgate.Utils.EntityToMap;
import com.example.researchgate.Utils.Result;
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
public class RelationController {

    @Autowired
    AchievementService achievementService;

    @Autowired
    RelationService relationService;

    @PostMapping("/achievement/authentication")
    public Result authentication(@RequestBody Map<String, Object> maps){
        Integer achId = Integer.valueOf(maps.get("id").toString());
        Integer userId = Integer.valueOf(maps.get("userId").toString());
        String name = (String) maps.get("name");
        Relation relation = new Relation();
        relation.setName(name);
        relation.setAchieveId(achId);
        relation.setUserId(userId);
        if(relationService.isAuthenticated(relation))
            return Result.fail("该人员已被认证");
        relationService.addRelation(relation);
        return Result.succ("认证成功");
    }

    @PostMapping("/account/getAuthenticatedAch")
    public Result getAuthenticatedAch(@RequestBody Map<String, Object>maps){
        Integer userId = Integer.valueOf(maps.get("userId").toString());
        List<Achievement> achievementList = relationService.findAchievementByUserId(userId);
        if (achievementList == null || achievementList.size()==0){
            return Result.fail("无结果");
        }
        List<Map<String, Object>> data = new ArrayList<>();
        for(Achievement achievement : achievementList){
            data.add(EntityToMap.object2Map(achievement));
        }
        return Result.succ("查找完毕", data);
    }
}
