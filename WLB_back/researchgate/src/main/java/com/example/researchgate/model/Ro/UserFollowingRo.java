package com.example.researchgate.model.Ro;

import lombok.Data;

@Data
public class UserFollowingRo {
    private String userId;
    /**
     * 被关注者
     */
    private String userToFollowId;
    /**
     * 被删除者
     */
    private String userToUnfollowId;
}
