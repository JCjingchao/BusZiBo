package com.szxb.buspay.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/8/25.
 */
@Entity
public class BlackListCard {
    @Id(autoincrement = true)
    private Long id;

    private String card_id;

    @Generated(hash = 900961236)
    public BlackListCard(Long id, String card_id) {
        this.id = id;
        this.card_id = card_id;
    }

    @Override
    public String toString() {
        return "BlackListCard{" +
                "id=" + id +
                ", card_id='" + card_id + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }
    @Generated(hash = 1656671282)
    public BlackListCard() {
}
}
