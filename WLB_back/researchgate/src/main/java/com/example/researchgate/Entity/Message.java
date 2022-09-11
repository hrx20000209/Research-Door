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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer msgId;
    /**
     * 消息内容
     */
    private String msgContent;
    /**
     * 消息发起人id
     */
    private String msgFrom;
    /**
     * 消息接收人id
     */
    private String msgTo;
    /**
     * 消息subject
     */
    private String msgSubject;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date msgDate;
}
