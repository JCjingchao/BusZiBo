package com.szxb.buspay.module.Parameter;

import android.widget.ListView;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.base.BaseMVPActivity;
import com.szxb.buspay.db.dao.OnLineInfoDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.OnLineInfo;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.TaskHandler;
import com.szxb.buspay.util.Constant;
import com.szxb.xblog.XBLog;

import java.util.List;

/**
 * 作者：Evergarden on 2017-07-25 10:31
 * QQ：1941042402
 */

public class ParameterActivity extends BaseMVPActivity<ParameterView,ParameterPresenter> implements ParameterView,OnPushTask{
    @Override
    protected int rootView() {
        return R.layout.view_parameter;
    }

    private TaskHandler handler;


    @Override
    protected ParameterPresenter getChildPresenter() {
        return new ParameterPresenter(this);
    }

    private  List<OnLineInfo> lineInfos;
    private ListView listView;
    private OnlineAdapter onlineAdapter;
    @Override
    protected void initData() {
        handler=new TaskHandler(this);
        OnLineInfoDao dao= DBCore.getDaoSession().getOnLineInfoDao();

        lineInfos=dao.loadAll();
        XBLog.d(lineInfos.size()+"---"+lineInfos.get(0).toString()+"---"+lineInfos.get(1).toString());
        listView=(ListView)findViewById(R.id.list_view);
        onlineAdapter=new OnlineAdapter(BusApp.getInstance(),lineInfos);
        listView.setAdapter(onlineAdapter);
        mPresenter.ListEvent(this);
    }

    void UpItem(){
        onlineAdapter.UpKey();
    }

    void DownItem(){
        onlineAdapter.DownKey();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.CloseEvent();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mPresenter.CloseEvent();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.ListEvent(this);
    }

    @Override
    public void OneKey() {
        super.OneKey();
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
        mPresenter.CloseEvent();
        handler.sendMessage(handler.obtainMessage(Constant.SettingFail));
    }


    @Override
    public void FourKey() {
        super.FourKey();
        mPresenter.CloseEvent();
        int position=onlineAdapter.getPosition();
        OnLineInfo lineInfo=lineInfos.get(position);
        XBLog.d("FourKey(ParameterActivity.java:110)"+lineInfo.toString());
        XBLog.d("FourKey(ParameterActivity.java:110)"+position+"");
        CommonSharedPreferences.put("FristNo",position+"");
        CommonSharedPreferences.put("fixed_price",lineInfo.getFixed_price());
        CommonSharedPreferences.put("chinese_name",lineInfo.getChinese_name());

        CommonSharedPreferences.put("coefficient",lineInfo.getCoefficient());
        int money=Integer.parseInt(lineInfo.getFixed_price());
        BusApp.getPosManager().setPayMarPrice(money);
        BusApp.getPosManager().setLineName(FetchAppConfig.LineName());
        int coef=Integer.parseInt(FetchAppConfig.coefficient().substring(24,27));
        BusApp.getPosManager().setMarkedPrice(money*coef/100);
        handler.sendMessage(handler.obtainMessage(Constant.SettingSuccess));

    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    public void upLines() {

    }
    @Override
    public void DownLines() {

    }

    @Override
    public void task(Object entity) {

    }

    @Override
    public void task(int type, Object entity) {
        switch (type){
            case Constant.KeyMessage:
                XBLog.d("task(ParameterActivity.java:139)"+type);
                 KeyEvent((byte[])entity);
                 break;
        }

    }

    @Override
    public void message(String msg) {

    }
}
