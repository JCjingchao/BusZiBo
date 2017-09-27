package com.szxb.buspay.module.Menu;

import android.content.Intent;
import android.widget.ListView;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.base.BaseMVPActivity;
import com.szxb.buspay.entity.CardRecord;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.module.home.HomeActivity;
import com.szxb.buspay.module.init.InitActivity;
import com.szxb.buspay.task.KeyListenerTask;
import com.szxb.buspay.task.TaskHandler;
import com.szxb.buspay.task.UpLoadRecord;
import com.szxb.buspay.util.tip.BusToast;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.Utils;
import com.szxb.xblog.XBLog;

import java.util.ArrayList;
import java.util.List;

import static com.szxb.buspay.util.tip.MainLooper.runOnUiThread;

/**
 * 作者：Evergarden on 2017-07-25 10:31
 * QQ：1941042402
 */

public class MenuActivity extends BaseMVPActivity<MenuView,MenuPresenter> implements MenuView,OnPushTask{
    @Override
    protected int rootView() {
        return R.layout.menu_list;
    }

    private TaskHandler handler;
    private  List<CardRecord> cardRecords;
    private ListView listView;
    private MenuAdapter menuAdapter;

    private static  String BlackListUp="黑名单下载";
    private static  String ParameterUp="参数下载";
    private static  String LineChange="线路选择";
    private static  String Record="上传记录";
    private static  String busNo="车号设置";
    @Override
    protected MenuPresenter getChildPresenter() {
        return new MenuPresenter(this);
    }


    @Override
    protected void onRestart() {
            super.onRestart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        keyListenerTask.isExit();

    }

    KeyListenerTask keyListenerTask;
    @Override
    protected void initView() {
        listView=(ListView)findViewById(R.id.list_menu);
        super.initView();
    }


    @Override
    protected void onPause() {
        super.onPause();
        keyListenerTask.isExit();
    }

    @Override
    protected void initData() {
        handler=new TaskHandler(this);
        String[] mString={BlackListUp,ParameterUp,LineChange,Record,busNo};
        List<String>  Menu=new ArrayList<String>();
        int index=0;
        for (String string : mString){
            Menu.add(index, mString[index]);
            index++;
        }
        menuAdapter=new MenuAdapter(BusApp.getInstance(),Menu);
        listView.setAdapter(menuAdapter);
        keyListenerTask =new KeyListenerTask();
        keyListenerTask.SetKeyLestener(this);
        keyListenerTask.start();

    }


    @Override
    protected void onDestroy() {
        keyListenerTask.isExit();
        super.onDestroy();

    }

    void UpItem(){
        menuAdapter.UpKey();
    }

    void DownItem(){
        menuAdapter.DownKey();
    }

    @Override
    public void OneKey() {
        super.OneKey();
        XBLog.d("OneKey(MenuActivity.java:84)"+"ONE");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UpItem();

            }
        });
    }

    @Override
    public void TwoKey() {
        super.TwoKey();
        XBLog.d("TwoKey(MenuActivity.java:95)"+"TWO");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DownItem();
            }
        });
    }

    @Override
    public void ThreeKey() {
        super.ThreeKey();
        XBLog.d("ThreeKey(MenuActivity.java:106)"+"Loading");
        handler.sendMessage(handler.obtainMessage(Constant.RecordBack));


    }

    @Override
    public void FourKey() {
        super.FourKey();
        int position=menuAdapter.getPosition();
        XBLog.d("FourKey(MenuActivity.java:113)"+position);
        switch (position){
            case 0:

                mPresenter.DownBlack(this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BusToast.showToast(BusApp.getInstance(),"正在下载请稍后...",true);
                    }
                });
                break;
            case 1:
                  mPresenter.DownParameter(this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BusToast.showToast(BusApp.getInstance(),"正在下载请稍后...",true);
                    }
                });
                break;
            case 2:
                handler.sendMessage(handler.obtainMessage(Constant.SkipPar));
                //线路选择
                break;
            case 3:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BusToast.showToast(BusApp.getInstance(),"正在上传请稍后...",true);
                    }
                });
                //记录上传
                UpLoadRecord upLoadRecord=new UpLoadRecord();
                upLoadRecord.start();

                XBLog.d("UpLoad");
                break;
            case 4:
                handler.sendMessage(handler.obtainMessage(Constant.SetBus));
                break;

        }

    }

    @Override
    public void message(String msg) {

    }

    @Override
    public void task(Object entity) {

    }

    @Override
    public void task(int type, Object entity) {

        switch (type){
            case Constant.KeyMessage:

                byte[] b=(byte[])entity;
                XBLog.d("task(MenuActivity.java:159)"+Utils.printHexBinary(b));
                KeyEvent(b);

                break;

            case Constant.blacklistdownSuccess:

                mPresenter.LoadingBlack(this);

                break;
            case Constant.blacklistdownFail:

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BusToast.showToast(BusApp.getInstance(),"黑名单下载失败",false);

                    }
                });

                break;
            case Constant.ParameterdownSuccess:

                mPresenter.LoadingParameter(this);
                break;

            case Constant.ParameterdownFail:

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BusToast.showToast(BusApp.getInstance(),"参数下载失败",false);

                    }
                });

                break;



            case Constant.LoadParameterdownSuccess:

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        BusToast.showToast(BusApp.getInstance(),"参数更新成功",true);
                        startActivity(new Intent(BusApp.getInstance(),InitActivity.class));

                    }
                });

                break;
            case Constant.LoadParameterdownFail:

                BusToast.showToast(BusApp.getInstance(),"参数更新失败",false);

                break;

            case Constant.LoadblacklistSuccess:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BusToast.showToast(BusApp.getInstance(),"黑名单更新成功",true);
                        startActivity(new Intent(BusApp.getInstance(),InitActivity.class));

                    }
                });
                break;
            case Constant.LoadblacklistFail:
                break;
        }

    }
}
