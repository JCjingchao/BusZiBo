package com.szxb.buspay.task.scan;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.szxb.buspay.entity.QRCode;
import com.szxb.buspay.entity.QRScanMessage;
import com.szxb.buspay.manager.report.PosScanManager;
import com.szxb.buspay.task.ThreadScheduledExecutorUtil;
import com.szxb.buspay.util.rx.RxBus;
import com.szxb.jni.libszxb;

import java.util.concurrent.TimeUnit;


/**
 * 作者: Tangren on 2017/7/31
 * 包名：com.szxb.task
 * 邮箱：996489865@qq.com
 * TODO:轮训扫码
 */

public class LoopScanTask extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //临时变量存储上次刷卡记录,为了防止重复刷卡
    private String tem = "0";

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadScheduledExecutorUtil.getInstance().getService().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    //循环扫码
                    byte[] recv = new byte[1024];
                    int barcode = libszxb.getBarcode(recv);
                    if (barcode > 0) {
                        String result = new String(recv, 0, barcode);
                        if (PosScanManager.isTenQRcode(result)) {
                            if (TextUtils.equals(result, tem)) {
                                RxBus.getInstance().send(new QRScanMessage(null, QRCode.REFRESH_QR_CODE));
                                return;
                            }
                            PosScanManager.getInstance().txposScan(result);
                        } else {
                            RxBus.getInstance().send(new QRScanMessage(null, QRCode.QR_ERROR));
                        }
                        tem = result;
                    }
                } catch (Exception e) {
                            RxBus.getInstance().send(new QRScanMessage(null, QRCode.SOFTWARE_EXCEPTION));
                    e.printStackTrace();
                }

            }
        }, 500, 100, TimeUnit.MILLISECONDS);
    }

}
