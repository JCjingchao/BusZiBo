package com.szxb.buspay.module.QueryRecord;

import android.util.Log;
import android.widget.ListView;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.base.BaseMVPActivity;
import com.szxb.buspay.db.dao.CardRecordDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.entity.CardRecord;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.KeyListenerTask;
import com.szxb.buspay.task.LoadingData;
import com.szxb.buspay.task.TaskHandler;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Evergarden on 2017-07-25 10:31
 * QQ：1941042402
 */

public class RecordActivity extends BaseMVPActivity<RecordView,RecordPresenter> implements RecordView,OnPushTask{
    @Override
    protected int rootView() {
        return R.layout.view_record;
    }

    private TaskHandler handler;
    private  List<CardRecord> cardRecords;
    private ListView listView;
    private RecordAdapter recordAdapter;

    @Override
    protected RecordPresenter getChildPresenter() {
        return new RecordPresenter(this);
    }

    @Override
    protected void initView() {
        listView=(ListView)findViewById(R.id.list_record);
        Log.d("Record","1");
        super.initView();
    }
    private KeyListenerTask keyListenerTask;
    @Override
    protected void initData() {
        Log.d("Record","2");
        handler=new TaskHandler(this);
        CardRecordDao dao=DBCore.getDaoSession().getCardRecordDao();
        cardRecords=dao.loadAll();
        Log.d("Record",cardRecords.get(cardRecords.size()-1).toString());
        recordAdapter=new RecordAdapter(BusApp.getInstance(),Setlist(cardRecords,index));
        getIndex(cardRecords);
        listView.setAdapter(recordAdapter);
        keyListenerTask =new KeyListenerTask();
        keyListenerTask.SetKeyLestener(this);
        keyListenerTask.start();
        Log.d("Record","3");

    }
    int index=0,len;
    public void getIndex(List<CardRecord> list){
        double i=list.size()/13.00;
         len=(int)Math.ceil(i);
    }




    void UpItem(){

        if (index==0){
            index=len-1;
        }else {
            index--;
        }
        recordAdapter=new RecordAdapter(BusApp.getInstance(),Setlist(cardRecords,index));
        listView.setAdapter(recordAdapter);
        recordAdapter.notifyDataSetInvalidated();
    }

    void DownItem(){
        if (index==len-1){
            index=0;
        }else {
            index++;
        }
        recordAdapter=new RecordAdapter(BusApp.getInstance(),Setlist(cardRecords,index));
        listView.setAdapter(recordAdapter);
        recordAdapter.notifyDataSetInvalidated();
    }

    @Override
    protected void onStop() {
        super.onStop();
        keyListenerTask.isExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyListenerTask.isExit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        keyListenerTask =new KeyListenerTask();
        keyListenerTask.SetKeyLestener(this);
        keyListenerTask.start();
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
        handler.sendMessage(handler.obtainMessage(Constant.RecordBack));
        super.ThreeKey();

    }

    @Override
    public void FourKey() {
        super.FourKey();
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
                Log.d("Record", Utils.printHexBinary(b));
                KeyEvent(b);
                break;
        }

    }

    public List<CardRecord> Setlist(List<CardRecord> list,int index){
            List<CardRecord> list1=new ArrayList<>();
          Log.d("Record",index+len+"");
            for (int i=list.size()-1-index*13;i>=list.size()-14-index*13&& i>0 ;i--){
                   list1.add(list.get(i));
            }
                 return list1;

    }

}
