package com.szxb.buspay.module.init;

import com.szxb.buspay.base.BaseModel;
import com.szxb.buspay.interfaces.OnPushTask;

import rx.Subscription;

/**
 * 作者: Tangren on 2017/7/18
 * 包名：com.szxb.buspay.module.init
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface InitModel extends BaseModel {




    void checkInit(RequestInitListener listener);
    void requestNet(RequestInitListener listener);
    void DriverSgin(OnPushTask task);
    void CloseDriver();


    interface RequestInitListener<T> {

        void onResult( Subscription subscription);
    }

}
