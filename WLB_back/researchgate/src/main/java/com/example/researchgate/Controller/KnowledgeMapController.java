package com.example.researchgate.Controller;


import com.example.researchgate.Service.Impl.AccountService;
import com.example.researchgate.Utils.RespUtil;
import com.example.researchgate.enums.CodeEnum;
import com.example.researchgate.model.Resp;
import com.example.researchgate.model.Ro.UserIdRo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class KnowledgeMapController {
    @Autowired
    public AccountService accountService;

    @RequestMapping(value = "getGraph",method = RequestMethod.POST)
    @ResponseBody
    public Resp getGraph(@RequestBody UserIdRo userIdRo){
        try{
            return RespUtil.ok(accountService.generateGraph(userIdRo.getUserId()));
        } catch(Exception e){
            System.out.println(e.toString());
            return RespUtil.fail(CodeEnum.GET_GRAPH_FAIL);
        }
    }

}
