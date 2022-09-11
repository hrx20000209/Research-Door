package com.example.researchgate.Notice;


import com.example.researchgate.Controller.AccountController;
import com.example.researchgate.model.Resp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class NoticeTest  {
    @Autowired
    AccountController accountController;
    @Test
    public void testGetNotice(){
        Resp resp = accountController.getNoticeById(7);
    }

}
