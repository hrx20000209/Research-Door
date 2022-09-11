package com.example.researchgate.model.Vo;

import lombok.Data;

import java.util.List;

@Data
public class GraphVo {
    private List<Node> nodes;
    private List<Links> links;
}
