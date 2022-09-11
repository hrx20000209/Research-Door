package com.example.researchgate.model.Vo;

import com.example.researchgate.Entity.User;
import lombok.Data;

@Data
public class UserIfFollowVo extends User {
    private boolean isFollow;
}
