package com.example.researchgate.Controller;

import com.example.researchgate.Dao.UserDao;
import com.example.researchgate.Dto.AddsmgDTO;
import com.example.researchgate.Dto.LikesmgDTO;
import com.example.researchgate.Dto.SelectsmgDTO;
import com.example.researchgate.Entity.Statemsg;
import com.example.researchgate.Utils.Result;
import com.example.researchgate.jpa.StatemsgJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class StatemsgController {
    @Autowired
    private StatemsgJpa statemsgJpa;

    @Autowired
    private UserDao userDao;

    @PostMapping("/addSmg")
    public HashMap addSmg(@RequestBody AddsmgDTO dto) {
        HashMap ret = new HashMap();

        Statemsg statemsg = Statemsg.builder()
                .smgdescription(dto.getSmgdescription())
                .smgdate(new Date())
                .smglike("")
                .smgowners(dto.getSmgowners())
                .smgtype(dto.getSmgtype())
                .smgrelationid(dto.getSmgrelationid())
                .build();

        statemsgJpa.save(statemsg);

        return ret;
    }

    @PostMapping("/selectSmg")
    public Result selectSmg(@RequestBody SelectsmgDTO dto) {
        List<Statemsg> statemsgs;

        if (dto.getKeyword().isEmpty()) {
            statemsgs = statemsgJpa.findAll();
        } else {
            statemsgs = statemsgJpa.findBySmgdescriptionContains(dto.getKeyword());
        }

        List<Statemsg> data = statemsgs.stream().map(statemsg -> {
            int smgflag = statemsg.getSmglike().contains(userDao.selectByUserId(String.valueOf(dto.getUserid())).getUserName()) ? 1 : 0;
            statemsg.setSmgflag(smgflag);
            return statemsg;
        }).collect(Collectors.toList());
        return Result.succ(data);
    }

    @PostMapping("/likeSmg")
    public Result likeSmg(@RequestBody LikesmgDTO dto) {
        Optional<Statemsg> statemsgOptional = statemsgJpa.findById(dto.getSmgid());
        if (statemsgOptional.isPresent()) {
            Statemsg statemsg = statemsgOptional.get();
            statemsg.setSmglikenum(statemsg.getSmglikenum() + 1);
            String userName = userDao.selectByUserId(String.valueOf(dto.getUserid())).getUserName();
            statemsg.setSmglike(statemsg.getSmglike() + "," + userName);
            statemsgJpa.save(statemsg);
            return Result.succ();
        } else {
            return Result.fail("not exists");
        }
    }
}
