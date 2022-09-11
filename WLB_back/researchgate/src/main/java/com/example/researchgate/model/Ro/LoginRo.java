package com.example.researchgate.model.Ro;

import lombok.Data;

@Data
public class LoginRo {
    /**
     * 用户名邮箱
     */
    String userEmail;

    /**
     * 用户Id
     */
    String userId;

    /**
     * 用户密码
     */
    String password;
}
