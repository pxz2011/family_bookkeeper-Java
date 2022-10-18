//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.entity;

public class AccountBook {
    private Integer id;
    private String remitter;
    private String payee;
    private String Remittance_Time;
    private String money;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private String remarks;


    public String toString() {
        return "AccountBook{id=" + this.id + ", remitter=" + this.remitter + ", payee=" + this.payee + ", Remittance_Time=" + this.Remittance_Time + ", money=" + this.money + ", remarks=" + this.remarks + '}';
    }

    public Integer getId() {
        return this.id;
    }

    public String getRemitter() {
        return this.remitter;
    }

    public String getPayee() {
        return this.payee;
    }

    public String getRemittance_Time() {
        return this.Remittance_Time;
    }

    public String getMoney() {
        return this.money;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRemitter(String remitter) {
        this.remitter = remitter;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public void setRemittance_Time(String Remittance_Time) {
        this.Remittance_Time = Remittance_Time;
    }

    public void setMoney(String money) {
        this.money = money;
    }

}
