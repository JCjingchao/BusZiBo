package com.szxb.buspay.entity;

/**
 * 作者：Evergarden on 2017-07-22 10:18
 * QQ：1941042402
 */

public class ByCardEntity {




    private float PayMoney;
    private float CardMoney;

    public float getPayMoney() {
        return PayMoney;
    }

    public void setPayMoney(float payMoney) {
        PayMoney = payMoney;
    }

    public float getCardMoney() {
        return CardMoney;
    }

    public void setCardMoney(float cardMoney) {
        CardMoney = cardMoney;
    }

    public ByCardEntity(float payMoney, float cardMoney) {
        PayMoney = payMoney;
        CardMoney = cardMoney;
    }

    public ByCardEntity(){}



}
