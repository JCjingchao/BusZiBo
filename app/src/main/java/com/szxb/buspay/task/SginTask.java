package com.szxb.buspay.task;

import android.media.AudioManager;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.Log;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.db.dao.CardRecordDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.CardRecord;
import com.szxb.buspay.entity.MifareGetCard;
import com.szxb.buspay.entity.SendPayData;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.util.CardType;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.Utils;
import com.szxb.jni.libszxb;
import com.szxb.xblog.XBLog;

import java.util.List;

/**
 * Created by Administrator on 2017/8/20.
 */

public class SginTask extends Thread {
    private String CardDIFF="1234";
    private boolean TaskKey=true;
    private OnPushTask onPushTask;
    private static SoundPool soundPool;
    private static int sgin;
    public SginTask(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sgin= soundPool.load(BusApp.getInstance(), R.raw.sgin, 1);
    }
    @Override
    public void run() {
        while (TaskKey) {
            Log.d("Cardpay", FetchAppConfig.saveDriver());
            byte[] CardByte = new byte[16];
            libszxb.MifareGetSNR(CardByte);
            String CardHex =Utils.printHexBinary(CardByte);
            Log.i("card", CardHex);
            MifareGetCard mifareGetCard=new MifareGetCard(CardByte);
            String cardnumber=mifareGetCard.getCardNumber();

            if (!TextUtils.isEmpty(CardHex) &&mifareGetCard.getStatus().equals("00")) {
                if (!mifareGetCard.getCardNumber().equals(CardDIFF)) {
                    CardDIFF=mifareGetCard.getCardNumber();
                    if (mifareGetCard.getCardType().equals(CardType.Driver)){
                        byte[] b={0x00,0x00,0x00};
                        byte[] record = new byte[128];
                        SendPayData sendPayData=SendPayData.GetData(Utils.printHexBinary(b),Utils.printHexBinary(b),"00","00");
                        Log.d("Cardpay",sendPayData.toString());
                        int ret = libszxb.qxcardprocess(Utils.hexStringToByte(sendPayData.toString()), record);
                        CardRecord cardRecord = new CardRecord();
                        cardRecord = cardRecord.bulider(Utils.printHexBinary(record));
                        CardRecodUp(cardRecord);
                        Log.d("Cardpay",Utils.printHexBinary(record));
                        Log.d("Cardpay",cardRecord.toString());
                        if (cardRecord.getStatus().equals("00") && cardRecord.getCardNumber()!=null && cardRecord.getPayType().equals("12")){
                            soundPool.play(sgin, 1, 1, 0, 0, 1);
                            Log.d("Cardpay",cardRecord.getDriverNo());
                            CommonSharedPreferences.put("DriverCard",cardRecord.getDriverNo());
                            Log.d("Cardpay", FetchAppConfig.saveDriver());
                            onPushTask.task(Constant.DriverSgin,cardRecord.getDriverNo());
                            TaskKey=false;
                        }

                    }
                }
            }
        }
        super.run();
    }


    public void IsExit(){
        TaskKey=false;
    }
    //255000018000699706
    public void IsDriver(String CardHex){
        String DriverNo=CardHex.substring(8,10);
        XBLog.d("IsDriver(SginTask.java:57)"+CardHex  + DriverNo);

        if (DriverNo.equals(CardType.Driver)){

        }
    }


    public void SetListener(OnPushTask onPushTask){
        this.onPushTask=onPushTask;
    }

    void CardRecodUp(CardRecord cardRecord){
        CardRecordDao dao= DBCore.getDaoSession().getCardRecordDao();
        Log.d("Record",cardRecord.toString());
        List<CardRecord> list=dao.loadAll();
        if (list.size()>0) {
            Log.d("Record", list.get(list.size() - 1).toString());
        }
        dao.insert(cardRecord);

    }
}