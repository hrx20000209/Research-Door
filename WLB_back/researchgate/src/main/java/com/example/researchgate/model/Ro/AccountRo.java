package com.example.researchgate.model.Ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AccountRo {

    /**
     * 用户名，必须
     */
    String userName;
    /**
     * 邮箱，必须
     */
    String userEmail;
    /**
     * 密码，必须
     */
    String password;
    /**
     * 研究所
     */
    String userInstitution;
    /**
     * 自我介绍
     */
    String introduction;
}