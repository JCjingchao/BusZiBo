package com.szxb.buspay.entity;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Evergarden on 2017/7/21 11:13
 * QQ：1941042402
 */

@Entity
public class CardPayEntity {
    @Id(autoincrement = true)
    private Long id;//id
    private String card_id;//卡号
    private Long order_time;//交易时间
    private float balance;//余额（本次扣费前）
    private float amount;//支付金额
    private float discount;//折扣
    private String mch_trx_id;
    public String time;//2017-02-02-12:40:20

    @Generated(hash = 1036587920)
    public CardPayEntity(Long id, String card_id, Long order_time, float balance,
            float amount, float discount, String mch_trx_id, String time) {
        this.id = id;
        this.card_id = card_id;
        this.order_time = order_time;
        this.balance = balance;
        this.amount = amount;
        this.discount = discount;
        this.mch_trx_id = mch_trx_id;
        this.time = time;
    }

    @Generated(hash = 860708902)
    public CardPayEntity() {
    }

    public static  JSONObject toJSon(CardPayEntity cardPayEntity){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",cardPayEntity.id);
        jsonObject.put("mch_trx_id",cardPayEntity.getMch_trx_id());
        jsonObject.put("card_id",cardPayEntity.getCard_id());
        jsonObject.put("order_time",cardPayEntity.getOrder_time()+"");
        jsonObject.put("balance",cardPayEntity.getBalance()+"");
        jsonObject.put("amount",cardPayEntity.getAmount()+"");
        jsonObject.put("discount",cardPayEntity.getDiscount()+"");
        jsonObject.put("time",cardPayEntity.getTime());
        return  jsonObject;
    }

    @Override
    public String toString() {
        return "CardPayEntity{" +
                "id=" + id +
                ", card_id='" + card_id + '\'' +
                ", order_time=" + order_time +
                ", balance=" + balance +
                ", amount=" + amount +
                ", discount=" + discount +
                ", time='" + time + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCard_id() {
        return this.card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public Long getOrder_time() {
        return this.order_time;
    }

    public void setOrder_time(Long order_time) {
        this.order_time = order_time;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getDiscount() {
        return this.discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getMch_trx_id() {
        return this.mch_trx_id;
    }

    public void setMch_trx_id(String mch_trx_id) {
        this.mch_trx_id = mch_trx_id;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
