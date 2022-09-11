package com.example.researchgate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resp<T> {
    private int code;
    private String requestId;
    private String msg;
    private T data;
}
