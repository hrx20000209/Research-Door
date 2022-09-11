package com.example.researchgate.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer knowledgeId;
    /**
     * 关系发起人userId
     */
    private String relationFirst;
    /**
     * 关系接收者userId
     */
    private String relationSecond;
}
