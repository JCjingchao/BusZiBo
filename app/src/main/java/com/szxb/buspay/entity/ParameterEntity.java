package com.szxb.buspay.entity;

/**
 * 作者：Evergarden on 2017-07-25 10:45
 * QQ：1941042402
 */

//参数类
public class ParameterEntity {
    private int RidBusNumber;//当前以乘车总人数
    private float RidBusMoney;//当前乘车总金额
    private int CodeBusNumber;//当前扫码总人数
    private float CodeBusMoney;//当前扫码总金额
    private int CardBusNumber;//当前刷卡总人数
    private float CardBusMoney;//当前刷卡总金额
    private String Driver;//司机卡号
    private String Line;//线路号

    public int getRidBusNumber() {
        return RidBusNumber;
    }

    public void setRidBusNumber(int ridBusNumber) {
        RidBusNumber = ridBusNumber;
    }

    public float getRidBusMoney() {
        return RidBusMoney;
    }

    public void setRidBusMoney(float ridBusMoney) {
        RidBusMoney = ridBusMoney;
    }

    public int getCodeBusNumber() {
        return CodeBusNumber;
    }

    public void setCodeBusNumber(int codeBusNumber) {
        CodeBusNumber = codeBusNumber;
    }

    public float getCodeBusMoney() {
        return CodeBusMoney;
    }

    public void setCodeBusMoney(float codeBusMoney) {
        CodeBusMoney = codeBusMoney;
    }

    public int getCardBusNumber() {
        return CardBusNumber;
    }

    public void setCardBusNumber(int cardBusNumber) {
        CardBusNumber = cardBusNumber;
    }

    public float getCardBusMoney() {
        return CardBusMoney;
    }

    public void setCardBusMoney(float cardBusMoney) {
        CardBusMoney = cardBusMoney;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }

    public ParameterEntity(int ridBusNumber, float ridBusMoney, int codeBusNumber, float codeBusMoney, int cardBusNumber, float cardBusMoney, String driver, String line) {
        RidBusNumber = ridBusNumber;
        RidBusMoney = ridBusMoney;
        CodeBusNumber = codeBusNumber;
        CodeBusMoney = codeBusMoney;
        CardBusNumber = cardBusNumber;
        CardBusMoney = cardBusMoney;
        Driver = driver;
        Line = line;
    }

    public ParameterEntity() {
    }
}
