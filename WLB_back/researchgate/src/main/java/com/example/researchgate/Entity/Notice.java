package com.example.researchgate.Entity;


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
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notId;
    /**
     * 消息内容
     */
    private String notContent;
    /**
     * 被通知人
     */
    private Integer notUserId;
    /**
     * 消息类型(1:follow 2:like)
     */
    private Integer notContentType;
    /**
     * 消息创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date notDate;
}
