package com.szxb.buspay.module.init;

import android.content.res.AssetManager;
import android.util.Log;
import android.widget.TextView;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.base.BaseMVPActivity;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.OnLineInfo;
import com.szxb.buspay.interfaces.InitOnListener;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.KeyListenerTask;
import com.szxb.buspay.task.TaskHandler;
import com.szxb.buspay.util.tip.BusToast;
import com.szxb.buspay.util.Constant;
import com.szxb.jni.libszxb;
import com.szxb.xblog.XBLog;
import com.yanzhenjie.nohttp.Logger;

import java.util.List;

/**
 * 作者: Tangren on 2017/7/18
 * 包名：com.szxb.buspay.module.init
 * 邮箱：996489865@qq.com
 * TODO:初始化Activity:检测更新、更新黑名单、更新公钥、网络自检……
 */

public class InitActivity extends BaseMVPActivity<InitView, InitPresenter> implements InitView, OnPushTask, InitOnListener {




    private TextView sgin,busno;

    private TaskHandler handler;

    private PosScanInit posScanInit;

    private KeyListenerTask keyListenerTask;

    private List<String> pramlist;
    private  int PramIndex,LoadIndex;
    @Override
    protected InitPresenter getChildPresenter() {
        return new InitPresenter(this);
    }


    @Override
    protected int rootView() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {

        sgin = (TextView) findViewById(R.id.sgin);
        busno=(TextView)findViewById(R.id.busno);
        busno.setText(FetchAppConfig.busNo());
        handler = new TaskHandler(this);
        super.initView();
    }


    @Override
    protected void initData() {
        mPresenter.intit();
        XBLog.d("initData(InitActivity.java:74)" + FetchAppConfig.fixed_price());
        Logger.d("初始化数据");
      //  BusToast.showToast(getApplicationContext(), "开始初始化", true);
        mPresenter.FtpBlackVersion(this);
        mPresenter.FtpPramVersion(this);
        //mPresenter.FtpPramVersion(this);
        //mPresenter.DownParameterAndBlack(this);
        //腾讯更新公钥、mac、黑名单
        posScanInit = new PosScanInit();
        posScanInit.setOnCallBack(this);
        posScanInit.init(getApplicationContext());
        keyListenerTask=new KeyListenerTask();
        keyListenerTask.SetKeyLestener(this);
        keyListenerTask.start();
    }




    @Override
    public void OneKey() {
        super.OneKey();
        handler.sendMessage(handler.obtainMessage(Constant.MenuG0));
    }

    @Override
    public void TwoKey() {

        super.TwoKey();
    }


    @Override
    public void task(Object entity) {

    }

    @Override
    public void message(String msg) {

    }



    private boolean blackVersion=false,PramVersion=false;
    @Override
    public void task(int type, Object entity) {
        XBLog.d("task(InitActivity.java:196)" + type);
        switch (type) {
            //黑名单版本下载成功
            case Constant.BlackVersionSuccess:
                Log.d("FTP1","黑名单版本下载成功");
                //加载黑名单版本
                mPresenter.IsBlackVersion(this);
                break;

            //黑名单版本下载失败
            case Constant.BlackVersionFail:
                //重新下载
                Log.d("FTP1","黑名单版本下载失败");
                mPresenter.FtpBlackVersion(this);
                //  mPresenter.IsBlackVersion(this);
                break;

            //黑名单版本加载成功不需要更新
            case Constant.BlackLoadVersionSuccess:
                Log.d("FTP1","黑名单版本加载成功且不需要更新");
                 blackVersion=true;
                if (PramVersion) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sgin.setText("加载成功，请签到...");
                        }
                    });
                    mPresenter.Sgin(this);
                }
                 break;

            //黑名单版本加载成功需要更新
            case Constant.BlackLoadVersionMust:
                Log.d("FTP1","黑名单版本加载成功需要更新");
                mPresenter.FtpBlackkList(this);
                break;

            //黑名单版本加载失败
            case Constant.BlackLoadVersionFail:
                //重新加载黑名单
                Log.d("FTP1","黑名单版本加载失败");
                mPresenter.IsBlackVersion(this);
                break;

            //黑名单下载失败
            case Constant.blacklistdownFail:
                Log.d("FTP1","黑名单下载失败");
                mPresenter.FtpBlackkList(this);
                break;

            case Constant.blacklistdownSuccess:
                Log.d("FTP1","黑名单下载成功");
                mPresenter.Loadingblack(this);
                break;

            //加载黑名单失败
            case Constant.LoadblacklistFail:
                Log.d("FTP1","加载黑名单失败");
                blackVersion = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sgin.setText("黑名单加载失败，正在重新加载...");
                    }
                });
                mPresenter.Loadingblack(this);
                break;


            //加载黑名单成功
            case Constant.LoadblacklistSuccess:
                Log.d("FTP1","加载黑名单成功");
                blackVersion = true;
                if (PramVersion) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sgin.setText("加载成功，请签到...");
                        }
                    });
                    mPresenter.Sgin(this);
                }
                break;

            //参数版本下载成功
            case Constant.ParamVersionSuccess:
                Log.d("FTP1","参数版本下载成功");
                Log.d("FTP","ParamOk");
                mPresenter.IsPramVersion(this);
                break;

            //参数版本下载失败
            case Constant.ParamVersionFail:
                Log.d("FTP1","参数版本下载失败");
                mPresenter.FtpPramVersion(this);
                break;

            //参数版本加载成功且不需要更新
            case Constant.ParamLoadVersionSuccess:
                Log.d("FTP1","参数版本加载成功且不需要更新");
                PramVersion=true;
                if (blackVersion) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sgin.setText("加载成功，请签到...");
                        }
                    });
                    mPresenter.Sgin(this);
                }

                break;

            //参数版本加载成功 需要更新
            case Constant.ParamLoadVersionMust:
                Log.d("FTP1","参数版本加载成功 需要更新");
                pramlist=(List<String>)entity;
                Log.d("FTP1","Load"+pramlist.toString());
                mPresenter.FtpPram(this,pramlist);
                PramIndex=0;
                break;

            //参数版本加载失败 需要重新加载
            case Constant.ParamLoadVersionFail:
                Log.d("FTP1","参数版本加载失败 需要重新加载");
                mPresenter.IsPramVersion(this);
                break;


            //参数下载成功
            case Constant.ParameterdownSuccess:
                Log.d("FTP1","参数下载成功");
                ++PramIndex;
                if (PramIndex==pramlist.size()){
                Log.d("FTP1",pramlist.size()+"");
                    mPresenter.LoadPramVersion(this);
                }

                break;

             //参数下载失败
            case Constant.ParameterdownFail:
                String name=(String)entity;
                Log.d("FTP1","参数下载失败"+name);
                mPresenter.FtpSigePram(this,name);
                break;


            //参数版本加载成功
            case Constant.LoadParamVersionSuccess:
                Log.d("FTP1","参数版本加载成功");
                mPresenter.LoadingParameter(this);
                LoadIndex=0;

                break;
            //参数版本加载失败
            case Constant.LoadParamVersionFail:
                Log.d("FTP1","参数版本加载失败");
                mPresenter.LoadPramVersion(this);
                break;

            //加载参数成功
            case Constant.LoadParameterdownSuccess:
                Log.d("FTP1","加载参数成功");
                PramVersion = true;
                LoadIndex++;
                int len= DBCore.getDaoSession().getLineEntityDao().loadAll().size();
                Log.d("FTP",len+""+LoadIndex+""+PramVersion+blackVersion+"");
                if (LoadIndex==len) {
                    if (blackVersion) {
                        List<OnLineInfo> dao=DBCore.getDaoSession().getOnLineInfoDao().loadAll();
                        for (OnLineInfo onLineInfo : dao){
                            Log.d("FTP",onLineInfo.toString());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sgin.setText("加载成功，请签到...");
                            }
                        });
                        mPresenter.Sgin(this);
                    }
                }
                break;

            //加载参数失败
            case Constant.LoadParameterdownFail:
                PramVersion = false;
                String paramname=(String)entity;
                Log.d("FTP1","失败"+paramname);
                mPresenter.LoadingParameter(this,paramname);
                break;


            case Constant.KeyMessage:
                KeyEvent((byte[]) entity);
                break;

            //签到
            case Constant.DriverSgin:
                handler.sendMessage(handler.obtainMessage(type, entity));
                break;

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.Close();
        keyListenerTask.isExit();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        keyListenerTask=new KeyListenerTask();
        keyListenerTask.SetKeyLestener(this);
        keyListenerTask.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        keyListenerTask.isExit();
    }

    @Override
    public void onCallBack(boolean isOk) {
        if (isOk) {
            XBLog.d("扫码更新成功");
            //腾讯信息更新成功
            //posScanInit.init(getApplicationContext());

        }
    }
}
