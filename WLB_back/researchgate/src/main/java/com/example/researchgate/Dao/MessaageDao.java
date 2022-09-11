package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessaageDao extends BaseMapper<Message> {
    @Select("select * from message where msg_subject =#{msgSubject} and ((msg_from = #{msgFrom} and msg_to = #{msgTo}) or (msg_from = #{msgTo} and msg_to = #{msgFrom})) order by msg_date ")
    List<Message> getDialog(@Param("msgSubject") String msgSubject,
                            @Param("msgFrom") String msgFrom,
                            @Param("msgTo") String msgTo);
    @Select("select distinct msg_subject from message where msg_from = #{userId} or msg_to = #{userId}")
    List<String> getUniqueGroup(@Param("userId") String userId);
    @Select("select  * from message where msg_subject =#{msgSubject} order by msg_date desc LIMIT 1")
    List<Message> getLastMessage(@Param("msgSubject") String msgSubject);
    @Select("select  * from message where msg_subject =#{msgSubject} and msg_from = #{userId}")
    List<Message> getUserSent(@Param("msgSubject") String msgSubject,
                              @Param("userId") String userId);
}
