package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserDao extends BaseMapper<User> {
    @Select("select * from user where user_email=#{email}")
    User getUserByEmail(String email);
    @Update("update user set introduction=#{introduction} where user_id=#{userId}")
    int updateIntroduction(@Param("introduction") String introduction,
                           @Param("userId") String userId);
    @Select("select * from user where user_id=#{userId}")
    User selectByUserId(String userId);
    @Select("select * from user where user_name=#{userName}")
    List<User> getUserListByUserName(String userName);
    @Select("select * from user where user_name like \"%\"#{userName}\"%\"")
    List<User> getMaxUserListByUserName(String userName);
    @Select("select * from user where user_institution = #{institution}")
    List<User> getSameInstitution(String institution);
 }
