package com.szxb.buspay.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.szxb.buspay.module.MainActivity;
import com.szxb.buspay.module.init.InitActivity;

/**
 * 作者: Tangren on 2017/7/12
 * 包名：com.szxb.onlinbus.task
 * 邮箱：996489865@qq.com
 * TODO:开机自起
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION) ||  android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            Intent mainActivityIntent = new Intent(context, InitActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
        }
    }
}
