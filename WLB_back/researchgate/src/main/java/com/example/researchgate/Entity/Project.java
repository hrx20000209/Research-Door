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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projid;
    private String projname;
    private Date projdate;
    private String projdescription;
    private String projectjoiner;

    private int projuserid;

    @Transient
    private int projectflag;
}