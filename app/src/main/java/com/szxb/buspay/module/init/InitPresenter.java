package com.szxb.buspay.module.init;

import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.BusApp;
import com.szxb.buspay.base.BaseModel;
import com.szxb.buspay.base.BasePresenter;
import com.szxb.buspay.db.dao.LineEntityDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.LineEntity;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.FTPDownLoad;
import com.szxb.buspay.task.KeyListenerTask;
import com.szxb.buspay.task.LoadingData;
import com.szxb.buspay.task.ParameterAndBlackList;
import com.szxb.buspay.util.Config;
import com.szxb.buspay.util.Constant;
import com.szxb.jni.libszxb;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者: Tangren on 2017/7/18
 * 包名：com.szxb.buspay.module.init
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class InitPresenter extends BasePresenter<InitView> {
    // private Subscription initSubscrie;//初始化事件
    private InitView initView;
    private InitModel model = new InitPresenterComl();

    InitPresenter(InitView initView) {
        this.initView = initView;
    }

    void Sgin(OnPushTask onPushTask) {
         model.DriverSgin(onPushTask);
    }

    void Close() {
        model.CloseDriver();
    }

    ExecutorService ex = Executors.newCachedThreadPool();

    boolean LoadParameter, Loadblack;

    //固件和K21
    void intit() {
        if ("1".equals(FetchAppConfig.init())) {
            AssetManager ass = BusApp.getInstance().getAssets();
            libszxb.ymodemUpdate(ass, "Q6_K2120170924204720.bin");
            CommonSharedPreferences.put("init", "1");
        }
        SetK21Time();
    }

    public FTPDownLoad ftpPramVersion,ftpBlackVersion,ftpblacklist,ftppram;


    //下载黑名单版本
    void FtpBlackVersion(OnPushTask onPushTask){
        Log.d("FTP","balckVersion");
        ftpBlackVersion=FTPDownLoad.Intance().SetFTPDwonPath("black/blackversion.json")
                .SetFTPDownName("blackversion.json").SetListener(onPushTask)
                .SetFTPDownCall(Constant.BlackVersionSuccess,Constant.BlackVersionFail)
                .OnStart();
    }

    //下载黑名单列表
    void FtpBlackkList(OnPushTask onPushTask){
        Log.d("FTP","balck");
        ftpblacklist=FTPDownLoad.Intance().SetFTPDwonPath("black/blacklist.json")
                .SetFTPDownName("blacklist.json").SetListener(onPushTask)
                .SetFTPDownCall(Constant.blacklistdownSuccess,Constant.blacklistdownFail)
                .OnStart();
    }

    //判断黑名单版本
    void IsBlackVersion(OnPushTask onPushTask){
        LoadingData loadA = new LoadingData();
        loadA.SetListenter(onPushTask);
        loadA.SetLoadType(Constant.BlackVersion);
        ex.submit(loadA);
    }

    //加载黑名单
    void  Loadingblack(OnPushTask onPushTask){
        LoadingData load = new LoadingData();
        load.SetListenter(onPushTask);
        load.SetLoadType(Constant.BlackList);
        ex.submit(load);
    }

    //下载参数文件版本
    void FtpPramVersion(OnPushTask onPushTask){
        Log.d("FTP","pramVersion");
        ftpPramVersion=FTPDownLoad.Intance().SetFTPDwonPath("pram/allline.json")
                .SetFTPDownName("allline.json").SetListener(onPushTask)
                .SetFTPDownCall(Constant.ParamVersionSuccess,Constant.ParamVersionFail)
                .OnStart();

    }

    //下载多个文件数据
    void FtpPram(OnPushTask onPushTask, List<String> list){
        Log.d("FTP","pram" + list.toString());
        for (String name:list)
        ftpPramVersion=FTPDownLoad.Intance().SetFTPDwonPath("pram/"+name)
                .SetFTPDownName(name).SetListener(onPushTask)
                .SetFTPDownCall(Constant.ParameterdownSuccess,Constant.ParameterdownFail)
                .OnStart();

    }
    //下载参数失败文件重新下载
    void FtpSigePram(OnPushTask onPushTask, String name ){
        Log.d("FTP","SigePram"+name);
            ftpPramVersion=FTPDownLoad.Intance().SetFTPDwonPath("pram/"+name)
                    .SetFTPDownName(name).SetListener(onPushTask)
                    .SetFTPDownCall(Constant.ParameterdownSuccess,Constant.ParameterdownSuccess)
                    .OnStart();

    }

    //判断参数文件更新数量
    void IsPramVersion(OnPushTask onPushTask){

        LoadingData loadA = new LoadingData();
        loadA.SetListenter(onPushTask);
        loadA.SetLoadType(Constant.ParamVersion);
        ex.submit(loadA);
    }

  //加载参数版本信息 进入数据库
    void LoadPramVersion(OnPushTask onPushTask){
        LoadingData loadA = new LoadingData();
        loadA.SetListenter(onPushTask);
        loadA.SetLoadType(Constant.LoadParamVersion);
        ex.submit(loadA);
    }


    //加载所有参数信息
    void LoadingParameter(OnPushTask onPushTask){
        LineEntityDao dao= DBCore.getDaoSession().getLineEntityDao();
        List<LineEntity> lineEntities= dao.loadAll();
        DBCore.getDaoSession().getOnLineInfoDao().deleteAll();
        Log.d("FTP",lineEntities.size()+"----");
        for (LineEntity lineEntity:lineEntities) {
            Log.d("FTPName",lineEntity.getAcnt()+","+lineEntity.getRouteno()+".json");
            LoadingData loadA = new LoadingData();
            loadA.SetListenter(onPushTask);
            loadA.SetLoadType(Constant.Parameter);
            String name=lineEntity.getAcnt()+","+lineEntity.getRouteno()+".json";
            loadA.SetName(name);
            ex.submit(loadA);
        }
    }
    //加载单个参数信息
    void LoadingParameter(OnPushTask onPushTask,String name){
            LoadingData loadA = new LoadingData();
            loadA.SetListenter(onPushTask);
            loadA.SetLoadType(Constant.Parameter);
            loadA.SetName(name);
            ex.submit(loadA);

    }

    //初始化单片机时间
    void SetK21Time() {
        Calendar now = Calendar.getInstance();
        int Year = now.get(Calendar.YEAR);
        int Month = now.get(Calendar.MONTH) + 1;
        int Day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int Min = now.get(Calendar.MINUTE);
        int Sec = now.get(Calendar.SECOND);
        libszxb.deviceSettime(Year, Month, Day, hour, Min, Sec);
    }



}
