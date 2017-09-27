package com.szxb.buspay.module.SetBusNumber;

import android.util.Log;
import android.widget.TextView;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.base.BaseMVPActivity;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.KeyListenerTask;
import com.szxb.buspay.task.TaskHandler;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.Utils;
import com.szxb.buspay.util.tip.BusToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evergarden on 2017/9/24.
 */

public class SetBusActivity extends BaseMVPActivity<SetBusView,SetBusPresenter>  implements SetBusView,OnPushTask{

    private TaskHandler handler;
    private TextView number1,number2,number3,number4,number5,number6;
    private int index=0;

    @Override
    protected int rootView() {
        return R.layout.set_bus;
    }
    List<TextView> list;

    @Override
    protected void initView() {
        handler=new TaskHandler(this);
        number1=(TextView)findViewById(R.id.number1);
        number2=(TextView)findViewById(R.id.number2);
        number3=(TextView)findViewById(R.id.number3);
        number4=(TextView)findViewById(R.id.number4);
        number5=(TextView)findViewById(R.id.number5);
        number6=(TextView)findViewById(R.id.number6);
        list=new ArrayList<>();
        list.add(0,number1);
        list.add(1,number2);
        list.add(2,number3);
        list.add(3,number4);
        list.add(4,number5);
        list.add(5,number6);
        list.get(index).setTextSize(46);
        list.get(index).setTextColor(getResources().getColor(R.color.colorWhite));
        String bus=FetchAppConfig.busNo();
        if (bus.length()==6){
            int i=0;
            for (TextView tv :list){
                tv.setText(bus.substring(i,i+1));
                i++;
                Log.d("SetBus",bus);
            }
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        keyListenerTask.isExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private KeyListenerTask keyListenerTask;
    @Override
    protected void initData() {
        KeyListenerTask keyListenerTask=new KeyListenerTask();
        keyListenerTask.SetKeyLestener(this);
        keyListenerTask.start();
    }

    @Override
    public void OneKey() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("Recode ---", 1 + "");
                int data = Integer.parseInt(list.get(index).getText().toString());
                if (data == 9) {
                    list.get(index).setText("0");
                } else {
                    list.get(index).setText(data + 1 + "");
                }

            }
        });
    }
    @Override
    public void TwoKey() {
        super.TwoKey();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.d("Recode ---", 2 + "");
                int data = Integer.parseInt(list.get(index).getText().toString());
                if (data == 0) {
                    list.get(index).setText("9");
                } else {
                    list.get(index).setText(data - 1 + "");
                }

            }
        });
    }

    @Override
    public void ThreeKey() {
        Log.d("Recode ---", 3+"");
        super.FourKey();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (index==0){
                    index=5;
                    list.get(0).setTextSize(34);
                    list.get(0).setTextColor(getResources().getColor(R.color.colorBlack));
                }else{
                    index--;
                    list.get(index+1).setTextSize(34);
                    list.get(index+1).setTextColor(getResources().getColor(R.color.colorBlack));
                }
                list.get(index).setTextSize(46);
                list.get(index).setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });
    }

    @Override
    public void FourKey() {
        super.ThreeKey();
        Log.d("Recode ---", 4+"");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (index==5){
                    String room=number1.getText().toString()+number2.getText().toString()+number3.getText().toString()
                            +number4.getText().toString();
                    CommonSharedPreferences.put("busNo","00"+room.trim());
                    BusToast.showToast(BusApp.getInstance(),"车号设置成功",true);
                    handler.sendMessage(handler.obtainMessage(Constant.Sgin));
                }else{
                    index++;
                    list.get(index).setTextSize(46);
                    list.get(index).setTextColor(getResources().getColor(R.color.colorWhite));
                    list.get(index-1).setTextSize(34);
                    list.get(index-1).setTextColor(getResources().getColor(R.color.colorBlack));

                }
            }
        });


    }


    @Override
    public void task(Object entity) {

    }

    @Override
    public void task(int type, Object entity) {
        if (type==Constant.KeyMessage){

            byte[] b=(byte[])entity;

            KeyEvent(b);
        }
    }

    @Override
    public void message(String msg) {

    }
}
