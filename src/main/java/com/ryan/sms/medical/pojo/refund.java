package com.ryan.sms.medical.pojo;

public class refund {
    private Long uid;

    private String username;

    private String requesttime;

    private Byte status;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime == null ? null : requesttime.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}