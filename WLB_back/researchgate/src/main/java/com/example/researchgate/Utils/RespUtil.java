package com.example.researchgate.Utils;

import com.example.researchgate.enums.CodeEnum;
import com.example.researchgate.model.Resp;

public class RespUtil {
    public static <T> Resp<T> ok(T t) {
        Resp<T> resp = new Resp<T>();
        resp.setCode(CodeEnum.OK.getCode());
        resp.setMsg(CodeEnum.OK.getMsg());
        resp.setData(t);
        return resp;
    }

    public static <T> Resp<T> ok() {
        Resp<T> resp = new Resp<T>();
        resp.setCode(CodeEnum.OK.getCode());
        resp.setMsg(CodeEnum.OK.getMsg());
        resp.setData(null);
        return resp;
    }

    public static Resp fail(int code, String msg) {
        Resp resp = new Resp();
        resp.setCode(code);
        resp.setMsg(msg);
        return resp;
    }


    public static <T> Resp<T> fail(int code, String msg, T t) {
        Resp<T> resp = new Resp<T>();
        resp.setCode(code);
        resp.setMsg(msg);
        resp.setData(t);
        return resp;
    }

    public static Resp fail(CodeEnum c) {
        int code = c.getCode();
        String msg = c.getMsg();
        return fail(code, msg);
    }

}
