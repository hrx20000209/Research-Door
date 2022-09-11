package com.example.researchgate.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.researchgate.Entity.Achievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AchievementDao extends BaseMapper<Achievement> {
    int saveAchInfo(@Param("achievement")Achievement achievement);
}
