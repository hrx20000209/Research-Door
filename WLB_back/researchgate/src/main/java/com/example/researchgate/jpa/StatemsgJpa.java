package com.example.researchgate.jpa;


import com.example.researchgate.Entity.Statemsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatemsgJpa extends JpaRepository<Statemsg, Integer> {
    List<Statemsg> findBySmgdescriptionContains(String smgdescription);
}
