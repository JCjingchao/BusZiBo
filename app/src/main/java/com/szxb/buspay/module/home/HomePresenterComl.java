package com.szxb.buspay.module.home;

import com.szxb.buspay.base.BasePresenterCompl;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.util.Constant;

/**
 * 作者：Evergarden on 2017/7/21 10:54
 * QQ：1941042402
 */

public class HomePresenterComl extends BasePresenterCompl implements HomeModel{
    private String Card_id="XXXXXXXXX";



    @Override
    public void OpenCardAndKey(OnPushTask onPushTask) {



    }

    @Override
    public void UpdataConfig(String Line) {


    }

    @Override
    public void CloseCard() {


    }

    @Override
    public void OpenPayCard(OnPushTask onPushTask) {

    }

    @Override
    public float Calculation(Object entity) {
        String Card_id=entity+"";
        if(Card_id.equals(this.Card_id)){

        }
        //根据卡类型，计算应扣费的金额

        return (float)2.00;
    }

    @Override
    public float CardMoney(Object entity) {
        byte[] card=(byte[] )entity;

        //根据返回类型计算余额
        return (float)100.00;
    }

    @Override
    public void OpenCard(OnPushTask onPushTask) {

    }

    @Override
    public void KeyEvent(Object entity,OnPushTask onPushTask) {
        byte[] recv=(byte[])entity;
        for (int i:recv){
            if(recv[i]>0){
               onPushTask.task(i);
            }
        }

    }



}
