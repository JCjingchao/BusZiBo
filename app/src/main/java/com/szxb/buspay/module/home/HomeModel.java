package com.szxb.buspay.module.home;

import com.szxb.buspay.base.BaseModel;
import com.szxb.buspay.interfaces.OnPushTask;

/**
 * 作者：Evergarden on 2017/7/21 10:53
 * QQ：1941042402
 */

public interface HomeModel extends BaseModel {

    void OpenCard(OnPushTask onPushTask);

    void OpenCardAndKey(OnPushTask onPushTask);//开启寻卡和按键

    void CloseCard();//关闭寻卡

    void OpenPayCard(OnPushTask onPushTask);//开启扣费

    float Calculation(Object Card_id);//卡号是否有误 卡类型判断 金额计算

    float CardMoney(Object card);//  读取结果判断 计算余额

    void KeyEvent(Object entity, OnPushTask onPushTask);

    void UpdataConfig(String code);

}
