package com.recommend.web.response;

import java.io.Serializable;

/**
 * Created by feir4 on 2018/5/13.
 */
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Integer SUCCESS = 111111;
    public static Integer FAIL = 999999;

    private Integer status;

    private String message;

    private Object data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private Boolean success;

    public void success(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.success = true;
    }

    public void success(Object data) {
        this.success(SUCCESS, "操作成功", data);
    }

    public void success(String message) {
        this.success(SUCCESS, message, null);
    }

    public void success() {
        this.success(SUCCESS, "操作成功", null);
    }

    public void fail(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.success = false;
    }

    public void fail(Object data) {
        this.fail(FAIL, "操作失败", data);
    }

    public void fail(String message) {
        this.fail(FAIL, message, null);
    }

    public void fail() {
        this.fail(FAIL, "操作失败", null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "status:" + String.valueOf(message) + " message:" + message;
    }
}
