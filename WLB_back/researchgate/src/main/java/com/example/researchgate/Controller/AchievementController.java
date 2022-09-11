package com.example.researchgate.Controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.researchgate.Entity.*;
import com.example.researchgate.Service.*;
import com.example.researchgate.Service.Impl.AccountService;
import com.example.researchgate.Utils.DateTool;
import com.example.researchgate.Utils.EntityToMap;
import com.example.researchgate.Utils.Result;
import com.example.researchgate.jpa.StatemsgJpa;
import com.example.researchgate.model.Ro.SendMsgFrom;
import com.example.researchgate.model.Ro.SendMsgRo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.util.*;

@RequestMapping("/api/achievement")
@RestController
public class AchievementController {
    @Autowired
    UserService userService;

    @Autowired
    AchievementService achievementService;

    @Autowired
    FollowService followService;

    @Autowired
    FileService fileService;

    @Autowired
    AccountService accountService;

    @Autowired
    RelationService relationService;

    @Autowired
    StatemsgJpa statemsgJpa;

    @PostMapping(path = "/addAch")
    public Result addAch(@RequestBody Map<String, Object> maps){
        Integer type = (Integer) maps.get("type");
        String title = (String) maps.get("title");
        String authors = (String) maps.get("authors");
        Integer userId = Integer.valueOf(maps.get("userId").toString());
        String doi = (String)(maps.get("doi")==null ? "" : maps.get("doi"));
        String description = (String)(maps.get("description")==null ? "" : maps.get("description"));
        Achievement achievement = new Achievement();
        achievement.setTitle(title);
        achievement.setType(type);
        achievement.setAuthors(authors);
        achievement.setUserId(userId);
        achievement.setDoi(doi);
        achievement.setDescription(description);
        achievement.setCiteCnt(0);
        achievement.setReadCnt(0);
        achievement.setRecommendCnt(0);
        achievement.setFollowedCnt(0);
        achievement.setDate(DateTool.string2Date(DateTool.getCurDate(), 0));

        String journal = (String) (maps.get("journal")==null ? "" : maps.get("journal"));
        String volume = (String) (maps.get("volume")==null ? "" : maps.get("volume"));
        String issue = (String) (maps.get("issue")==null ? "" : maps.get("issue"));
        String page = (String) (maps.get("page")==null ? "" : maps.get("page"));
        achievement.setJournal(journal);
        achievement.setVolume(volume);
        achievement.setIssue(issue);
        achievement.setPage(page);

        String number = (String) (maps.get("number")==null ? "" : maps.get("number"));
        achievement.setNumber(number);

        String institution = (String) (maps.get("institution")==null ? "" : maps.get("institution"));
        achievement.setInstitution(institution);

        achievement.setIsAvailable(0);
        Integer achId = achievementService.addAch(achievement);
        if (maps.get("file")!=null) {
            MultipartFile file = (MultipartFile) maps.get("file");
            achievement.setIsAvailable(1);
            try {
                achievement.setFilePath(fileService.uploadFile(file, "achievement/"+achievement.getAchId()));
                achievementService.setAch(achievement);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        achievementService.setAch(achievement);
        User user = userService.getUserById(userId);
        Statemsg statemsg = Statemsg.builder()
                .smgdescription(user.getUserName()+"上传了新成果："+achievement.getTitle())
                .smgdate(new Date())
                .smglike("")
                .smgowners(user.getUserName())
                .smgtype(1)
                .smgrelationid(achId)
                .build();
        statemsgJpa.save(statemsg);
        return Result.succ("上传成功");
    }
//
//    @PostMapping("/deleAch")
//    public Result deleAch(@RequestBody Map<String, Object> maps){
//        Integer achId = (Integer) maps.get("achId");
//        achievementService.delAch(achId);
//        return Result.succ("已删除");
//    }

//    @GetMapping("/searchAchWriters")
//    public Result searchAchWriters(@RequestParam("achId") Object achid){
//        Integer achId = (Integer) achid;
//        Achievement achievement = achievementService.getAch(achId);
//        if (achievement == null)
//            return Result.fail("无作品");
//        String writers = achievement.getAchOwners();
//        return Result.succ("作者查找完毕", writers);
//    }

    @PostMapping("/searchAch")
    public Result searchAch(@RequestBody Map<String, Object> maps){
        String keyword = (String) maps.get("keyword");
        String type = (String) maps.get("type");
        String temp = (String) maps.get("userId");
        Integer userId = Integer.valueOf(temp);
        List<Achievement> achievementList =  achievementService.getAchByKeyword(keyword, type);
        List<Map<String, Object>> data = new ArrayList<>();
        if (achievementList==null || achievementList.size()==0)
            return Result.fail("无结果");
        for (Achievement achievement: achievementList){
            Map<String, Object> map = EntityToMap.object2Map(achievement);
            Integer typeId = (Integer) map.get("type");
            switch (typeId){
                case 1:
                    map.put("type", "期刊论文");
                    break;
                case 2:
                    map.put("type", "专利");
                    break;
                case 3:
                    map.put("type", "技术报告");
            }
            map.put("isFollowed", followService.isFollow(userId, achievement.getAchId()));
            map.put("isAuthor", relationService.isAuthor(userId, achievement.getAchId()));
            map.put("id", achievement.getAchId());
            List<Map<String, Object>> author = new ArrayList<>();
            String temp2 = achievement.getAuthors();
            String[] authors = temp2.split(";");
            for(String name : authors){
                User user = relationService.findUserByAchAndName(name, achievement.getAchId());
                Map<String, Object> map2 = new HashMap<>();
                map2.put("name", name);
                if (user!=null){
                    map2.put("userId", user.getUserId());
                }else map2.put("userId", 0);
                author.add(map2);
            }
            map.put("author", author);
            data.add(map);
        }
        return Result.succ("查找完毕", data);
    }

    @PostMapping("/downloadAch")
    public Result downloadAch(@RequestBody Map<String, Object> maps){
        Integer achId = Integer.valueOf(maps.get("id").toString());
        Achievement achievement = achievementService.getAch(achId);
        if (achievement.getFilePath()!=null)
            return Result.succ("查找完毕", achievement.getFilePath());
        else
            return Result.fail("没有文件");
    }

    @PostMapping("/requestAch")
    public Result requestAch(@RequestBody Map<String, Object> maps){
        Integer achId = Integer.valueOf(maps.get("id").toString());
        Integer userId = Integer.valueOf(maps.get("userId").toString());
        Achievement achievement = achievementService.getAch(achId);

        SendMsgRo sendMsgRo = new SendMsgRo();
        sendMsgRo.setUserId(userId.toString());
        SendMsgFrom sendMsgFrom = new SendMsgFrom();
        sendMsgFrom.setMsgTo(userService.getUserById(achievement.getUserId()).getUserName());
        sendMsgFrom.setMsgSubject("提交全文提醒。");
        sendMsgFrom.setMsgContent("有人正希望您上传"+achievement.getTitle()+"的全文");
        sendMsgRo.setSendMsgForm(sendMsgFrom);
        try {
            accountService.sendMessage(sendMsgRo);
            return Result.succ("已发送");
        } catch (Exception e) {
            System.out.println(e.toString());
            return Result.fail("未发送");
        }
    }

    @PostMapping("/getDetail")
    public Result getDetail(@RequestBody Map<String, Object> maps){
        Integer achId = Integer.valueOf(maps.get("id").toString());
        Integer userId = Integer.valueOf(maps.get("userId").toString());
        Achievement achievement = achievementService.getAch(achId);
        Map<String, Object> info = EntityToMap.object2Map(achievement);
        Integer typeId = (Integer) info.get("type");
        switch (typeId){
            case 1:
                info.put("type", "期刊论文");
                break;
            case 2:
                info.put("type", "专利");
                break;
            case 3:
                info.put("type", "技术报告");
        }
        info.put("isFollowed", followService.isFollow(userId, achId));
        info.put("isAuthor", relationService.isAuthor(userId, achId));
        Map<String, Object> data = new HashMap<>();
        data.put("info", info);
        List<Map<String, Object>> author = new ArrayList<>();
        String[] authors = achievement.getAuthors().split(";");
        for(String name : authors){
            User user = relationService.findUserByAchAndName(name, achId);
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            if (user!=null){
                map.put("userId", user.getUserId());
            }else map.put("userId", 0);
            author.add(map);
        }
        data.put("author", author);
        return Result.succ("获取成果详情成功", data);
    }
}
