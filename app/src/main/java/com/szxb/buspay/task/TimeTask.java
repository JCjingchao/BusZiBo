package com.szxb.buspay.task;

import com.szxb.buspay.interfaces.OnPushTask;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：Evergarden on 2017-09-01 17:14
 * QQ：1941042402
 */

public class TimeTask extends Thread {

    private OnPushTask onPushTask;

    public void SetListener(OnPushTask onPushTask){
        this.onPushTask=onPushTask;
    }
    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH :mm :ss");//设置日期格式
            String time = df.format(new Date());
            onPushTask.task(time);

            super.run();
        }
    }
}
