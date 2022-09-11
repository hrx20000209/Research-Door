package com.example.researchgate.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userFollowId;
    /**
     * 关注者
     */
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
