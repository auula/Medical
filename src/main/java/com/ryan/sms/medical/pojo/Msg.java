package com.ryan.sms.medical.pojo;

public class Msg {

    private Long mid;

    private String username;

    private String message;

    private String reply;

    private String createtime;


    public Msg() {

    }

    public Msg toMsg(String username, String message,String createtime) {
        this.mid = mid;
        this.username = username;
        this.message = message;
        this.reply = reply;
        this.createtime = createtime;
        return  this;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }
}