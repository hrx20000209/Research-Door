package com.example.researchgate.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "ach_id", type = IdType.INPUT)
    private Integer achId;
    // 1:论文 2:专利 3:技术报告
    private Integer type;
    private Integer userId;
    private String title;
    private String authors;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    private String doi;
    //1
    private String journal;
    private String volume;
    private String issue;
    private String page;
    //2 3
    private String number;
    //3
    private String institution;

    private String achFollows;
    private Integer followedCnt;
    private String filePath;
    private Integer isAvailable;
    private Integer citeCnt;
    private Integer readCnt;
    private Integer recommendCnt;
}
