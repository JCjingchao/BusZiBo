package com.szxb.buspay.module.Menu;

import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.base.BasePresenter;
import com.szxb.buspay.db.dao.BlackListCardDao;
import com.szxb.buspay.db.dao.OnLineInfoDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.entity.BlackListCard;
import com.szxb.buspay.entity.OnLineInfo;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.LoadingData;
import com.szxb.buspay.task.ParameterAndBlackList;
import com.szxb.buspay.util.Constant;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：Evergarden on 2017-07-25 10:34
 * QQ：1941042402
 */

public class MenuPresenter extends BasePresenter<MenuView> {
    private MenuView menuView;
    private MenuModel model=new MenuPresenterComl();
    public MenuPresenter(MenuView menuView){
        this.menuView=menuView;
    }

    void BlackListDao(List<String> list){
        BlackListCardDao dao= DBCore.getDaoSession().getBlackListCardDao();
        dao.deleteAll();
        for (String card_id:list){
            BlackListCard blackListCard=new BlackListCard();
            blackListCard.setCard_id(card_id);
            dao.insert(blackListCard);
        }

    }

    //保存参数
    void ParameterDao(List<JSONObject> list){
        OnLineInfoDao dao= DBCore.getDaoSession().getOnLineInfoDao();
        dao.deleteAll();
        for (JSONObject object:list){
            OnLineInfo onLineInfo=new OnLineInfo();
            onLineInfo.setLine((String)object.get("line"));
            onLineInfo.setVersion((String)object.get("version"));
            onLineInfo.setUp_station((String)object.get("up_station"));
            onLineInfo.setDwon_station((String)object.get("down_station"));
            onLineInfo.setChinese_name((String)object.get("chinese_name"));
            onLineInfo.setIs_fixed_price((String)object.get("is_fixed_price"));
            onLineInfo.setFixed_price((String)object.get("fixed_price"));
            onLineInfo.setCoefficient((String)object.get("coefficient"));
            onLineInfo.setShortcut_price((String)object.get("shortcut_price"));
            dao.insert(onLineInfo);
        }

    }


    ExecutorService ex= Executors.newCachedThreadPool();



    void  LoadingBlack(OnPushTask onPushTask){
        LoadingData load=new LoadingData();
        load.SetListenter(onPushTask);
        load.SetLoadType(Constant.BlackList);
        ex.submit(load);
    }

    //加载参数
    void LoadingParameter(OnPushTask onPushTask){
        LoadingData loadA=new LoadingData();
        loadA.SetListenter(onPushTask);
        loadA.SetLoadType(Constant.Parameter);
        ex.submit(loadA);
    }




    void DownParameter(OnPushTask onPushTask){
        ParameterAndBlackList parameterAndBlackList=ParameterAndBlackList.Intance();
        parameterAndBlackList.SetListener(onPushTask);
        parameterAndBlackList.SetTpye(Constant.Parameter);
        ex.submit(parameterAndBlackList);

    }

    //更新Card黑名单
    void DownBlack(OnPushTask onPushTask){
        ParameterAndBlackList parameterAndBlackList=ParameterAndBlackList.Intance();
        parameterAndBlackList.SetListener(onPushTask);
        parameterAndBlackList.SetTpye(Constant.BlackList);
        ex.submit(parameterAndBlackList);

    }
}
