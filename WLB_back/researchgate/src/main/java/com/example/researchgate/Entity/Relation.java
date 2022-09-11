package com.example.researchgate.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "relation_id", type = IdType.INPUT)
    private Integer relationId;
    private Integer userId;
    private Integer achieveId;
    private String name;
}
