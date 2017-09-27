package com.szxb.buspay.module.init;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.base.BasePresenterCompl;
import com.szxb.buspay.entity.MachineStatus;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.SginTask;
import com.szxb.buspay.util.Utils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: Tangren on 2017/7/18
 * 包名：com.szxb.buspay.module.init
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class InitPresenterComl extends BasePresenterCompl implements InitModel {

    Subscription subscribe;
    SginTask sginTask;

    @Override
    public void DriverSgin(OnPushTask task) {
        sginTask=new SginTask();
        sginTask.start();
        sginTask.SetListener(task);
    }

    @Override
    public void CloseDriver() {
        sginTask.IsExit();
    }

    @Override
    public void requestNet(RequestInitListener listener) {

    }



    @Override
    public void checkInit(final RequestInitListener listener) {
        subscribe = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        MachineStatus machineStatus = new MachineStatus.Bulider()
                                .ConnectNet(Utils.checkNetStatus())
                                .firmware(true)
                                .PSAM(s)
                                .SN("112313")
                                .builder();

                        BusApp.getInstance().post(machineStatus);
                        listener.onResult(subscribe);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        subscribe.unsubscribe();
                    }
                });

    }


}
