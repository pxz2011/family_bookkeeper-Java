//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.result;

public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public Result(boolean b, int i, String success, Object data) {
    }

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, (Object)null);
    }
}
