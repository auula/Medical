package com.ryan.sms.medical.utils;

import java.util.HashMap;
import java.util.Map;

public class JsonData {

    private Integer status;
    private String message;
    private Map<String, Object> result;

    public JsonData build(Integer s, String mes) {
        this.message = mes;
        this.status = s;
        this.result = new HashMap<>();
        return this;
    }

    public JsonData put(String key, Object obj) {
        this.result.put(key, obj);
        return this;
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

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
