package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.UserFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowDao extends BaseMapper<UserFollow> {
    @Delete("delete from user_follow where user_id = #{userId} and user_to_follow_id = #{userToFollowId}")
    public int delUserFollow(@Param("userId") String userId,
                             @Param("userToFollowId") String userToFollowId);
    @Select("select * from user_follow where user_id = #{userId}")
    public List<UserFollow> getFollowList(@Param("userId") String userId);
    @Select("select * from user_follow where user_to_follow_id = #{userId}")
    public List<UserFollow> getFollowerList(@Param("userId") String userId);
    @Select("select * from user_follow where user_id = #{userId} and user_to_follow_id = #{userToFollowId}")
    public List<UserFollow> getFollow(@Param("userId") String userId,
                                      @Param("userToFollowId") String userToFollowId);
    @Select("select count(*) from user_follow where user_to_follow_id = #{userId}")
    public Integer countFollow(@Param("userId") String userId);

}
