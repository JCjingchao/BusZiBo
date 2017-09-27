package com.szxb.buspay.task.post;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.BusApp;
import com.szxb.buspay.base.BaseView;
import com.szxb.buspay.db.manager.DBManager;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.entity.ScanInfoEntity;
import com.szxb.buspay.task.ThreadScheduledExecutorUtil;
import com.szxb.buspay.util.Config;
import com.szxb.buspay.util.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 作者: Tangren on 2017/9/1
 * 包名：szxb.com.commonbus.task
 * 邮箱：996489865@qq.com
 * TODO:定时上送流水，10分钟如果有数据就上传一次
 */

public class TimePostBillTask extends Service implements BaseView {

    private String app_id;
    private PostBIllPresenter presenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app_id = BusApp.getPosManager().getAppId();
        presenter = new PostBIllPresenter(this);
        ThreadScheduledExecutorUtil.getInstance().getService().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<ScanInfoEntity> swipeList = DBManager.getScanEntityList();
                Log.d("TimePostBillTask",
                        "run(TimePostBillTask.java:55)" + swipeList.size());
                if (swipeList.size() == 0) {
                    Log.d("TimePostBillTask",
                            "run(TimePostBillTask.java:58)" + swipeList.size());
                    return;
                }
                JSONObject order_list = new JSONObject();
                JSONArray array = new JSONArray();
                for (int i = 0; i < swipeList.size(); i++) {
                    array.add(JSON.parse(swipeList.get(i).getBiz_data_single()));
                }
                order_list.put("order_list", array);
                Map<String, Object> map = commMap();
                map.put("order_data", order_list.toJSONString());
                presenter.requestPost(Config.POST_BILL_WHAT, map, Config.POST_BILL);

            }
        }, 10, 10, TimeUnit.MINUTES);
    }

    private Map<String, Object> commMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("app_id", BusApp.getPosManager().getAppId());
        map.put("sn_no", BusApp.getPosManager().getDriverNo());
        map.put("bus_no", BusApp.getPosManager().getBusNo());
        map.put("bus_time", DateUtil.getCurrentDate());
        return map;
    }

    @Override
    public void onSuccess(int what, String str) {
        //上传成功
        CommonSharedPreferences.put("lastTimePushFile", DateUtil.getCurrentDate());
    }

    @Override
    public void onFail(int what, String str) {
        //上传失败
    }
}
