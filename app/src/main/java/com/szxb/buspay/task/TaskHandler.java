package com.szxb.buspay.task;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.entity.CardRecord;
import com.szxb.buspay.module.Menu.MenuActivity;
import com.szxb.buspay.module.Parameter.ParameterActivity;
import com.szxb.buspay.module.QueryRecord.RecordActivity;
import com.szxb.buspay.module.SetBusNumber.SetBusActivity;
import com.szxb.buspay.module.home.HomeActivity;
import com.szxb.buspay.module.init.InitActivity;
import com.szxb.buspay.util.tip.BusToast;
import com.szxb.buspay.util.Constant;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

/**
 * 作者: Tangren on 2017/7/13
 * 包名：com.szxb.onlinbus.task
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class TaskHandler extends Handler {

    private WeakReference<AppCompatActivity> weakReference;

    public TaskHandler(AppCompatActivity activity) {
        weakReference = new WeakReference<AppCompatActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        AppCompatActivity activity = weakReference.get();
        if (activity != null) {
            switch (msg.what) {
                case Constant.blacklistdownFail:
                    ((TextView)activity.findViewById(R.id.sgin)).setText("文件参数下载失败,正在重新下载");
                    break;
                case Constant.ParameterdownFail:
                   ((TextView)activity.findViewById(R.id.sgin)).setText("文件参数下载失败，正在重新下载");
                    break;
                case Constant.blacklistdownSuccess:
                   ((TextView)activity.findViewById(R.id.sgin)).setText("司机签到");
                    break;
                case Constant.ParameterdownSuccess:
                    break;

                case Constant.DriverSgin:
                    String DriverNO=msg.obj+"";
                    CommonSharedPreferences.put("DriverCard",DriverNO);
                    activity.startActivity(new Intent().setClass(activity.getApplicationContext(),HomeActivity.class));
                    break;

                case Constant.FindCardMessage:
                    break;

                case Constant.SkipPar:
                   activity.startActivity(new Intent().setClass(BusApp.getInstance(),ParameterActivity.class));
                    break;

                case Constant.CardPayMessage:
                    CardRecord cardRecord=(CardRecord) msg.obj;
                    if (cardRecord.getStatus().equals("00")) {
                        double mon=Integer.valueOf(cardRecord.getCardMoney(), 16) / 100.00;
                        DecimalFormat df= new DecimalFormat("######0.00");
                        df.format(mon);
                        BusToast.showToast(BusApp.getInstance(), "余额" + mon+ "元", true);
                    } else if (cardRecord.getStatus().equals("FE")){

                        BusToast.showToast(BusApp.getInstance(),"请重刷",false);
                    }
                    else if (cardRecord.getStatus().equals("F3")){
                        double mon=Integer.valueOf(cardRecord.getCardMoney(), 16) / 100.00;
                        DecimalFormat df= new DecimalFormat("######0.00");
                        df.format(mon);
                        BusToast.showToast(BusApp.getInstance(),"余额不足: "+mon + "元",false);
                    }else if (cardRecord.getStatus().equals("F1")){

                        BusToast.showToast(BusApp.getInstance(),"卡片未启用",false);

                    }else if (cardRecord.getStatus().equals("F2")){

                        BusToast.showToast(BusApp.getInstance(),"卡片过期",false);

                    } else if (cardRecord.getStatus().equals("F4")){

                        BusToast.showToast(BusApp.getInstance(),"黑名单卡",false);
                    }else if (cardRecord.getStatus().equals("10")){
                        double mon=Integer.valueOf(cardRecord.getCardMoney(), 16) / 100.00;
                        DecimalFormat df= new DecimalFormat("######0.00");
                        df.format(mon);
                        BusToast.showToast(BusApp.getInstance(),"余额" + mon + "元",true);
                      }else if (cardRecord.getStatus().equals("11")){
                        double mon=Integer.valueOf(cardRecord.getCardMoney(), 16) / 100.00;
                        DecimalFormat df= new DecimalFormat("######0.00");
                        df.format(mon);
                        BusToast.showToast(BusApp.getInstance(),"余额" + mon + "元",true);
                    }
                    else{
                        double mon=Integer.valueOf(cardRecord.getCardMoney(), 16) / 100.00;
                        DecimalFormat df= new DecimalFormat("######0.00");
                        df.format(mon);
                        BusToast.showToast(BusApp.getInstance(),"错误: "+mon+ "元",false);
                    }
                        break;


                case Constant.ALLMessage:

                    break;
                case Constant.SettingFail:
                    activity.startActivity(new Intent().setClass(BusApp.getInstance(),MenuActivity.class));
                    break;
                case Constant.SettingSuccess:
                    BusToast.showToast(BusApp.getInstance(),"线路设置成功",true);
                    activity.startActivity(new Intent(activity.getApplicationContext(),InitActivity.class));
                    break;

                case Constant.RecordGo:
                    activity.startActivity(new Intent().setClass(BusApp.getInstance(),RecordActivity.class));

                    break;

                case Constant.RecordBack:

                    activity.startActivity(new Intent().setClass(BusApp.getInstance(),InitActivity.class));
                    break;
                case Constant.MenuG0:

                    activity.startActivity(new Intent().setClass(BusApp.getInstance(),MenuActivity.class));
                    break;

                case Constant.Sgin:
                    activity.startActivity(new Intent().setClass(BusApp.getInstance(),InitActivity.class));
                    break;

                case Constant.SetBus:
                    activity.startActivity(new Intent().setClass(BusApp.getInstance(),SetBusActivity.class));
                    break;
                default:

                    break;
            }
        }

    }

    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
