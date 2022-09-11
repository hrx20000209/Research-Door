package com.example.researchgate.Service.Impl;


import com.example.researchgate.Dao.NoticeDao;
import com.example.researchgate.Entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoticeService {
    @Autowired
    NoticeDao noticeDao;

    public List<Notice> getNoticeByUserId(Integer userId){
        List<Notice> noticeList = noticeDao.getNoticeByUser(userId);
        return noticeList;
    }
}
