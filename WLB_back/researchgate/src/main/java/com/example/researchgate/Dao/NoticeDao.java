package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.Notice;
import com.example.researchgate.Entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeDao extends BaseMapper<Notice> {
    @Select("select * from notice where not_user_id=#{userId} order by not_date desc")
    List<Notice> getNoticeByUser(Integer userId);
}
