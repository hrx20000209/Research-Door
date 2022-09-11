package com.example.researchgate.Service.Impl;


import com.example.researchgate.Dao.UserDao;
import com.example.researchgate.Entity.User;
import com.example.researchgate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User getUserById(Integer userId) { return userDao.selectByUserId(userId.toString()); }
}
