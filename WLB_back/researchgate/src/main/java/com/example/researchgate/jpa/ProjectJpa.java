package com.example.researchgate.jpa;


import com.example.researchgate.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectJpa extends JpaRepository<Project, Integer> {
    List<Project> findByProjdescriptionContains(String projdescription);
}
