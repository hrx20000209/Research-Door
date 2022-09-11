package com.example.researchgate.model.Ro;

import lombok.Data;

@Data
public class UserFollowRo {
    /**
     * 被关注者Id
     */
    private String userId;
    /**
     * 被关注者姓名
     */
    private String userName;
    /**
     * 被关注者研究所
     */
    private String userInstitution;
    /**
     * 被关注者邮箱
     */
    private String userEmail;
    /**
     * 是否被关注，默认是true
     */
    private boolean isFollow = true;
}
