package com.szxb.buspay.module.home;

import android.content.Loader;
import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.BusApp;
import com.szxb.buspay.base.BaseModel;
import com.szxb.buspay.base.BasePresenter;
import com.szxb.buspay.db.dao.ScanInfoEntityDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.ScanInfoEntity;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.CardPay;
import com.szxb.buspay.task.KeyListenerTask;
import com.szxb.buspay.task.LoadingData;
import com.szxb.buspay.task.TimeTask;
import com.szxb.jni.libszxb;
import com.szxb.xblog.XBLog;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 作者：Evergarden on 2017/7/21 10:45
 * QQ：1941042402
 */

public class HomePresenter extends BasePresenter<HomeView> {
    private HomeView homeView;
    private HomeModel homeModel = new HomePresenterComl();

    HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    private KeyListenerTask keyListenerTask;

    private CardPay cardPay;


    //加载参数 有开始消费 无进入线路选择
    void init(OnPushTask onPushTask) {

       // CommonSharedPreferences.put("init", libszxb.getVersion());
        String Line = FetchAppConfig.FristNo();
        XBLog.d("init(HomePresenter.java:41)" + Line);
        if (Line != null && !Line.equals("")) {
            XBLog.d("init(HomePresenter.java:43)" + "Pay");
            homeModel.UpdataConfig(Line);
            homeView.SetText(FetchAppConfig.LineName() + "        " + FetchAppConfig.chinese_name());
            int money = Integer.parseInt(FetchAppConfig.fixed_price());
            BusApp.getPosManager().setPayMarPrice(money);
            BusApp.getPosManager().setLineName(FetchAppConfig.LineName());
            int coef=Integer.parseInt(FetchAppConfig.coefficient().substring(24,27));
            BusApp.getPosManager().setMarkedPrice(money*coef/100);
            String coedd= FetchAppConfig.coefficient();
            Log.d("init",coedd+"---");
            int coeff=Integer.parseInt(coedd.substring(0,3));
            DecimalFormat df= new DecimalFormat("######0.00");
            double moneyf = money / 100.00*coeff/100;
            String code=df.format(moneyf);
            homeView.SetPrice(code + "");
            OnlineEvent(onPushTask);
            CardEvent(onPushTask);
            OnTime(onPushTask);
        } else {
            XBLog.d("init(HomePresenter.java:49)" + "Change");
            homeView.MenuGo();
        }
    }


    void OnlineEvent(OnPushTask onPushTask) {
        keyListenerTask = new KeyListenerTask();
        keyListenerTask.start();
        keyListenerTask.SetKeyLestener(onPushTask);

    }


    void OnTime(OnPushTask onPushTask) {
        TimeTask timeTask = new TimeTask();
        timeTask.SetListener(onPushTask);
        timeTask.start();
    }

    void CloseSkipEvent() {
        if (keyListenerTask != null)
            keyListenerTask.isExit();
        if (cardPay != null)
            cardPay.IsClose();
    }


    void CardEvent(OnPushTask onPushTask) {


        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
        cardPay = new CardPay();
        cardPay.start();
        cardPay.SetOnLisetener(onPushTask);
    }


    void requestTX(int what, String url, Map<String, Object> map) {
        if (homeModel != null) {
            homeModel.requestJSONObjectData(what, url, map, new BaseModel.RequestListener() {
                @Override
                public void onAllSuccess(int what, JSONObject result) {
                    if (homeView != null) {
                        Log.d("Map",result.toJSONString());
                        String retcode = result.getString("retcode");
                        if (retcode.equals("0")) {
                            String retmsg = result.getString("retmsg");
                            if (retmsg.equals("ok")) {
                                JSONArray result_list = result.getJSONArray("result_list");
                                JSONObject resultObject = result_list.getJSONObject(0);
                                if (resultObject.getString("result").equals("0")) {
                                    if (resultObject.getString("status").equals("00") || resultObject.getString("status").equals("91")) {
                                        String mch_trx_id = resultObject.getString("mch_trx_id");
                                        ScanInfoEntityDao dao = DBCore.getDaoSession().getScanInfoEntityDao();
                                        ScanInfoEntity entity = dao.queryBuilder().where(ScanInfoEntityDao.Properties.Mch_trx_id.eq(mch_trx_id)).build().unique();
                                        if (entity != null) {
                                            entity.setStatus(1);
                                            dao.update(entity);
                                        }
                                        homeView.onSuccess(what, "实时扣款成功");
                                    }
                                } else {
                                    //准实时扣款失败
                                    homeView.onFail(what, "实时扣款失败");
                                }
                            } else {
                                //准实时扣款失败
                                homeView.onFail(what, "实时扣款失败");
                            }

                        }

                    }
                }

                @Override
                public void onFail(int what,String msg) {
                    //准实时扣款失败
                    homeView.onFail(what, "实时扣款失败");
                }
            });
        }
    }
}
