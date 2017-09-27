package com.szxb.buspay.task;

import android.util.Log;

import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.Utils;
import com.szxb.jni.libszxb;

/**
 * Created by Administrator on 2017/8/21.
 */

public class KeyListenerTask extends Thread {
    private boolean KeyTask=true;

    private OnPushTask onPushTask;
    @Override
    public void run() {
        while (KeyTask){
            try {
                Thread.sleep(1000);

            }catch (Exception e){

            }
            byte[] b=new byte[5];
            //libszxb.mytestkey(b);
            libszxb.devicekey(b);

            String code=Utils.printHexBinary(b);
            Log.d("Recode",code);
            if ( !code.equals("0000000000")) {
                Log.d("Recode",code);
                onPushTask.task(Constant.KeyMessage, b);
                try {

                }catch (Exception e){

                }

            }
        }
        super.run();
    }
    public void isExit(){
        KeyTask=false;
    }



    public void SetKeyLestener(OnPushTask onPushTask){
             this.onPushTask=onPushTask;
    }
}
