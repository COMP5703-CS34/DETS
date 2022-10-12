package com.backend.chain.entity;

public class Request {
    private Integer id;
    private String from;
    private String to;
    private String bargainingUser;
    private Boolean type;
    private String amount;
    private Double price;
    private Integer createTime;
    private Integer updateTime;
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreateTime(Integer createTime){
        this.createTime = createTime;
    }

    public Integer getCreateTime(){
        return createTime;
    }

    public void setUpdateTime(Integer updateTime){
        this.updateTime = updateTime;
    }

    public Integer getUpdateTime(){
        return updateTime;
    }

    public String getBargainingUser() {
        return bargainingUser;
    }

    public void setBargainingUser(String bargainingUser) {
        this.bargainingUser = bargainingUser;
    }
}