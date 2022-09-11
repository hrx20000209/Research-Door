package com.example.researchgate.Utils;

import lombok.Data;

@Data
public class Result {
    private Integer status;
    private String msg;
    private Object data;

    public static Result succ() {
        Result m = new Result();
        m.setStatus(200);
        m.setData(null);
        m.setMsg("操作成功");
        return m;
    }
    public static Result succ(Object data) {
        Result m = new Result();
        m.setStatus(200);
        m.setData(data);
        m.setMsg("操作成功");
        return m;
    }
    public static Result succ(String msg, Object data) {
        Result m = new Result();
        m.setStatus(200);
        m.setData(data);
        m.setMsg(msg);
        return m;
    }
    public static Result fail(String mess) {
        Result m = new Result();
        m.setStatus(400);
        m.setData(null);
        m.setMsg(mess);
        return m;
    }
    public static Result fail(String mess, Object data) {
        Result m = new Result();
        m.setStatus(400);
        m.setData(data);
        m.setMsg(mess);
        return m;
    }
}
