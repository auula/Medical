package com.ryan.sms.medical.pojo;

public class Bill {
    private Long bid;

    private String username;

    private Integer money;

    private String time;

    private Byte up;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Byte getUp() {
        return up;
    }

    public void setUp(Byte up) {
        this.up = up;
    }
}