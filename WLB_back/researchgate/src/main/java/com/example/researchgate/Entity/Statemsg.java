package com.example.researchgate.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Statemsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smgid", nullable = false)
    private Integer smgid;
    private String smgowners; // ids
    private int smgtype;// 1 成果 2 项目 3 其他
    private int smgrelationid; // achid
    private String smglike;
    private int smglikenum;
    private String smgdescription;
    private Date smgdate;

    @Transient
    private int smgflag;
}
