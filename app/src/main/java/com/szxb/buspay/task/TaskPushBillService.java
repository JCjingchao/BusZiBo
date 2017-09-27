package com.szxb.buspay.task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * 作者: Tangren on 2017/7/27
 * 包名：com.szxb.buspay.task
 * 邮箱：996489865@qq.com
 * TODO:上传乘车记录任务
 */

public class TaskPushBillService extends Service {

    private String snNo;//机具的SN号,长度必须是5位
    private int count = 0;//批次

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        snNo = FetchAppConfig.snNo();
//        fileUtil = new WriteFileUtil();

        UpLoadRecord upLoadRecord=new UpLoadRecord();
        ThreadScheduledExecutorUtil.getInstance().getService().scheduleAtFixedRate(upLoadRecord,0, 30, TimeUnit.MINUTES);
//        ThreadScheduledExecutorUtil.getInstance().getService().scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                //如果snNo为null等待下次再发送
//                if (TextUtils.isEmpty(snNo))
//                    return;
//                //如果不存在外置SD卡
//                if (!BusApp.getInstance().isExistSD()) {
//                    //保存数据到文件然后上传至服务器
//                    fileUtil.saveFileAndUploadNet("scan" + snNo + DateUtil.getCurrentDate("yyyMMdd") + count,
//                            "card" + snNo + DateUtil.getCurrentDate("yyyMMdd") + count);
//                } else {
//                    //保存到外置SD卡
//                    fileUtil.saveFileToOutSD("scan" + snNo + DateUtil.getCurrentDate("yyyMMdd") + count,
//                            "card" + snNo + DateUtil.getCurrentDate("yyyMMdd") + count);
//                }
//
//            }
//        }, 30, 30, TimeUnit.MINUTES);//30分钟上传一次
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (fileUtil != null)
//            fileUtil.cancelSubScribe();
    }
}
