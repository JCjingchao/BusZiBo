package com.szxb.buspay.task;


import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.db.dao.BlackListCardDao;
import com.szxb.buspay.db.dao.CardRecordDao;

import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.BlackListCard;
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
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.lang.Integer.parseInt;

/**
 * Created by Evergarden on 2017/8/20.
 */

public class CardPay extends Thread {
    private static SoundPool soundPool;
    private static int student,oldman,free,siji,yuangong,
    guanli,qingtoubi,fiefaka,downStart,downSuccess,qingchongzhi,
            erro,chongxingshuaka,sgin,xiaban,qingmaipiao,dwondata,dang,
            bupiao,qingshangche,qingxiache,yongjunka,aixinka,youhuika,
            dang2,qingnianjian,carduseless,rongjunka,dangdang,wuchanxianxue;

    private String CARD_NO="";
    private boolean CardTask=true;

//01----------学生卡
//02----------老年卡
//03----------免费卡
//04----------司机卡
//05----------员工卡
//06----------管理卡
//07----------请投币
//08----------非法卡
//09----------程序下载
//10----------下载完毕
//11----------请充值
//12----------错误
//13----------重新刷卡
//14----------上班
//15----------下班
//16----------请买票
//17----------下载数据
//18----------当
//19----------补上次票
//20----------请上车
//21----------请下车
//22----------拥军卡
//23----------爱心卡
//24----------优惠卡
//25----------当(新版)
//26----------请年检
//27----------卡失效
// 28----------荣军卡
// 29----------当两声
// 30----------无偿献血卡
 //   private byte[] money = {0x00, 0x00, 0x03, 0x00};
    private OnPushTask onPushTask;
    public CardPay(){

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        student = soundPool.load(BusApp.getInstance(), R.raw.w01, 1);
        oldman = soundPool.load(BusApp.getInstance(), R.raw.w02, 1);
        free = soundPool.load(BusApp.getInstance(), R.raw.w03, 1);
        siji = soundPool.load(BusApp.getInstance(), R.raw.w04, 1);
        //yuangong = soundPool.load(BusApp.getInstance(), R.raw.w05, 1);
        yuangong = soundPool.load(BusApp.getInstance(), R.raw.w29, 1);
        guanli = soundPool.load(BusApp.getInstance(), R.raw.w06, 1);
        qingtoubi = soundPool.load(BusApp.getInstance(), R.raw.w07, 1);
        fiefaka = soundPool.load(BusApp.getInstance(), R.raw.w08, 1);
        downStart = soundPool.load(BusApp.getInstance(), R.raw.w09, 1);
        downSuccess = soundPool.load(BusApp.getInstance(), R.raw.w10, 1);
        qingchongzhi = soundPool.load(BusApp.getInstance(), R.raw.w11, 1);
        erro = soundPool.load(BusApp.getInstance(), R.raw.w12, 1);
        chongxingshuaka = soundPool.load(BusApp.getInstance(), R.raw.w13, 1);
        sgin = soundPool.load(BusApp.getInstance(), R.raw.w14, 1);
        xiaban = soundPool.load(BusApp.getInstance(), R.raw.w15, 1);
        qingmaipiao = soundPool.load(BusApp.getInstance(), R.raw.w16, 1);
        dwondata = soundPool.load(BusApp.getInstance(), R.raw.w17, 1);
        dang = soundPool.load(BusApp.getInstance(), R.raw.w18, 1);
        bupiao = soundPool.load(BusApp.getInstance(), R.raw.w19, 1);
        qingshangche = soundPool.load(BusApp.getInstance(), R.raw.w20, 1);
        qingxiache = soundPool.load(BusApp.getInstance(), R.raw.w21, 1);
        yongjunka = soundPool.load(BusApp.getInstance(), R.raw.w22, 1);
        aixinka = soundPool.load(BusApp.getInstance(), R.raw.w23, 1);
        youhuika = soundPool.load(BusApp.getInstance(), R.raw.w24, 1);
        dang2 = soundPool.load(BusApp.getInstance(), R.raw.w25, 1);
        qingnianjian = soundPool.load(BusApp.getInstance(), R.raw.w26, 1);
        carduseless = soundPool.load(BusApp.getInstance(), R.raw.w27, 1);
        rongjunka = soundPool.load(BusApp.getInstance(), R.raw.w28, 1);
        dangdang = soundPool.load(BusApp.getInstance(), R.raw.w29, 1);
        wuchanxianxue = soundPool.load(BusApp.getInstance(), R.raw.w30, 1);

    }

    public void IsClose(){
        CardTask=false;
    }

    @Override
    public void run() {
        while (CardTask) {
            byte[] record = new byte[128];
            byte[] CardAndType = new byte[16];
            String Card = libszxb.MifareGetSNR(CardAndType);
            MifareGetCard mifareGetCard=new MifareGetCard(CardAndType);
            String Type=mifareGetCard.getCardType();
            if (Card!=null && !mifareGetCard.getCardNumber().equals(CARD_NO) &&  mifareGetCard.getStatus().equals("00")) {

                  CARD_NO=mifareGetCard.getCardNumber();
                  //黑名单卡
                  if (isBlack(mifareGetCard.getCardNumber())){
                      SendPayData sendPayData=SendPayData.GetData(Moenyhex(),Moenyhex(),"01","00");
                      Log.d("CardPayblack",sendPayData.toString());
                      libszxb.qxcardprocess(Utils.hexStringToByte(sendPayData.toString()), record);
                      Log.d("CardPayblack",Utils.printHexBinary(record));
                      CardRecord cardRecord = new CardRecord();
                      cardRecord = cardRecord.bulider(Utils.printHexBinary(record));
                      soundPool.play(fiefaka, 1, 1, 0, 0, 1);
                      CardRecodUp(cardRecord);
                      onPushTask.task(Constant.CardPayMessage, cardRecord);
                  }
                  else {

                      Log.d("CardPay:当前卡类型",Type);
                      SendPayData sendPayData=SendPayData.GetData(MoenyShex(Type),MoenyShex(CardType.CARDT_NORMAL),"00","01");
                      Log.d("Cardpay",sendPayData.toString());
                      int ret = libszxb.qxcardprocess(Utils.hexStringToByte(sendPayData.toString()), record);
                      String recordHEX = Utils.printHexBinary(record);
                      CardRecord cardRecord = new CardRecord();
                      cardRecord = cardRecord.bulider(recordHEX);
                      Log.d("Cardpay",recordHEX);
                      CardRecodUp(cardRecord);
                      Log.d("Cardpay",cardRecord.getStatus()+cardRecord.getCardType());
                      if (recordHEX != null && ret == 0) {
                          Log.d("Cardpay", "Money：" + Integer.valueOf(cardRecord.getCardMoney(), 16) +
                                  "-----" + (parseInt(cardRecord.getPayMoney().substring(2), 16)));
                          if ( cardRecord.getCardType().equals("06")&& cardRecord.getPayType().equals("13")) {
                              Log.d("CardPay",FetchAppConfig.saveDriver());
                              soundPool.play(xiaban, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.Sgin, "");
                          }

                          if (cardRecord.getCardType().equals("10")&& cardRecord.getPayType().equals("13")) {
                              soundPool.play(xiaban, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.Sgin, "");
                          }

                          if (cardRecord.getCardType().equals("11")&& cardRecord.getPayType().equals("13")) {
                              soundPool.play(xiaban, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.Sgin, "");
                          }

                          //M1卡交易
                          if (cardRecord.getStatus().equals("00") && cardRecord.getPayType().equals("07")) {
                              CardTypeMusic(cardRecord.getCardType());
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }


                          //M1卡交易
                          if (cardRecord.getStatus().equals("00") && cardRecord.getPayType().equals("06")) {

                              if (parseInt(cardRecord.getCardMoney(),16)<500){
                                  soundPool.play(qingchongzhi, 1, 1, 0, 0, 1);
                                  onPushTask.task(Constant.CardPayMessage, cardRecord);
                              } else{
                                  CardTypeMusic(cardRecord.getCardType());
                                  onPushTask.task(Constant.CardPayMessage, cardRecord);
                              }
                          }


                            //cpu卡交易
                          if (cardRecord.getStatus().equals("00") &&cardRecord.getPayType().equals("05") ){
                              if (cardRecord.getCardType().equals("00") || cardRecord.getCardType().equals("08") ){
                                  if (parseInt(cardRecord.getCardMoney(),16)<500){
                                      soundPool.play(qingchongzhi, 1, 1, 0, 0, 1);
                                      onPushTask.task(Constant.CardPayMessage, cardRecord);
                                  } else{
                                      soundPool.play(dang, 1, 1, 0, 0, 1);
                                      onPushTask.task(Constant.CardPayMessage, cardRecord);
                                  }
                              }
                          }

                          if (cardRecord.getStatus().equals("FE")){
                              soundPool.play(chongxingshuaka, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }

                          if (cardRecord.getStatus().equals("F1")){
                              soundPool.play(erro, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }

                          if (cardRecord.getStatus().equals("F2")){
                              soundPool.play(erro, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }

                          if (cardRecord.getStatus().equals("F3")){
                              soundPool.play(qingmaipiao, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }

                          if (cardRecord.getStatus().equals("F4")){
                              soundPool.play(fiefaka, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }

                          if (cardRecord.getStatus().equals("FF")){
                              soundPool.play(erro, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }

                          if (cardRecord.getStatus().equals("11")){
                              soundPool.play(aixinka, 1, 1, 0, 0, 1);
                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }

                          if (cardRecord.getStatus().equals("10")){
                                  if (parseInt(cardRecord.getCardMoney(),16)<500){
                                      soundPool.play(qingchongzhi, 1, 1, 0, 0, 1);
                                  }else {
                                      soundPool.play(wuchanxianxue, 1, 1, 0, 0, 1);
                                  }

                              onPushTask.task(Constant.CardPayMessage, cardRecord);
                          }
                      }
                  }
            }
            if (Card==null)
            {
                CARD_NO="2233";
            }
        }
    }



    public boolean isBlack(String Card){
        BlackListCardDao dao=DBCore.getDaoSession().getBlackListCardDao();
        List<BlackListCard> l =dao.queryBuilder().where(BlackListCardDao.Properties.Card_id.eq(Card)).list();
        if (l.size()>0){
            return true;
        }
        return false;
    }


//    public static final String CARDT_NORMAL="01";
//    public static final String CARDT_STUDENT_A="02";
//    public static final String CARDT_OLDMAN="03";
//    public static final String CARDT_FREE="04";
//    public static final String CARDT_MEMORY="05";
//    public static final String CARDT_DRIVER="06";


//    public static final String CARDT_FAVOR1="05";
//    public static final String CARDT_FAVOR2="07";
//    public static final String CARDT_FAVOR3="08";
//    public static final String CARDT_FAVOR="07";
//    public static final String CARDT_STUDENT_B="08";
//    public static final String CARDT_SET="10";
//    public static final String CARDT_GATHER="11";
//    public static final String CARDT_SIGNED="12";
//    public static final String CARDT_CHECKED="13";
//    public static final String CARDT_CHECK="18";


    public void CardTypeMusic(String type){
        switch (type) {
            case CardType.CARDT_NORMAL:
                soundPool.play(dang, 1, 1, 0, 0, 1);
                break;

            case CardType.CARDT_STUDENT_A:
                soundPool.play(student, 1, 1, 0, 0, 1);
                break;

            case CardType.CARDT_OLDMAN:
                soundPool.play(oldman, 1, 1, 0, 0, 1);
                break;


            case CardType.CARDT_FREE:
                soundPool.play(rongjunka, 1, 1, 0, 0, 1);
                break;

            case CardType.CARDT_MEMORY:
                soundPool.play(dang2, 1, 1, 0, 0, 1);
                break;
            case CardType.CARDT_DRIVER:
                soundPool.play(yuangong, 1, 1, 0, 0, 1);
                break;
            case CardType.CARDT_FAVOR:
                soundPool.play(youhuika, 1, 1, 0, 0, 1);
                break;
            case CardType.CARDT_STUDENT_B:
                soundPool.play(student, 1, 1, 0, 0, 1);
                break;
            case CardType.CARDT_SET:
                soundPool.play(xiaban, 1, 1, 0, 0, 1);
                break;

            case CardType.CARDT_GATHER:

                soundPool.play(dang2, 1, 1, 0, 0, 1);
                break;
            case CardType.CARDT_SIGNED:
                soundPool.play(dang2, 1, 1, 0, 0, 1);
                break;

            case CardType.CARDT_CHECKED:

                soundPool.play(dang2, 1, 1, 0, 0, 1);
                break;
            case CardType.CARDT_CHECK:
                soundPool.play(dang2, 1, 1, 0, 0, 1);
                break;


        }
    }




    public  void Erro(String typeErro){
        switch (typeErro){
            case CardType.NO_ERROR:

                break;
            case  CardType.CARD_DISABLE_FLAG:

                break;
            case  CardType.CARD_TIME_OVER:

                break;
            case  CardType.CARD_NO_MONEY:

                break;
            case CardType.CARD_ERROR:

                break;
            case CardType.CONSUME_ERROR:

                break;
        }
    }


    String Moenyhex(){
        String money=FetchAppConfig.fixed_price();
        int money_int= parseInt(money);
        byte[] money_b=Utils.int2Bytes(money_int,3);
        return Utils.printHexBinary(money_b);

    }
    String MoenyShex(String Type){
        String coeff=FetchAppConfig.coefficient();
        int price=Integer.parseInt(FetchAppConfig.fixed_price());
        int money_int;
        switch (Type){
            case CardType.CARDT_STUDENT_A:
                money_int=  Integer.parseInt(coeff.substring(3,6)) * price/100;
                Log.d("CardPay Money:",money_int+"");
                break;
            case CardType.CARDT_OLDMAN:
                money_int=  Integer.parseInt(coeff.substring(6,9)) * price/100;
                Log.d("CardPay Money:",money_int+"");
                break;
            case CardType.CARDT_FREE:
                money_int=  Integer.parseInt(coeff.substring(9,12)) * price/100;
                Log.d("CardPay Money:",money_int+"");
                break;
            case CardType.CARDT_DRIVER:
                money_int=  Integer.parseInt(coeff.substring(15,18)) * price/100;
                Log.d("CardPay Money:",money_int+"");
                break;
            case CardType.CARDT_NORMAL:
                money_int=  Integer.parseInt(coeff.substring(0,3)) * price/100;
                Log.d("CardPay Money:",money_int+"");
                break;
            default:
                money_int=  Integer.parseInt(coeff.substring(0,3)) * price/100;
                Log.d("CardPay Money:",money_int+"");
                break;

        }

        byte[] money_b=Utils.int2Bytes(money_int,3);
        Log.d("CardPay Money:",Utils.printHexBinary(money_b));
        ;
        return Utils.printHexBinary(money_b);

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

    //单位分 扣钱
    byte[] setMoney(int Money){
        byte[] money=Utils.int2Bytes(Money,3);
        byte[] LaoNain={0x00,0x00,0x00};
        byte[] data=new byte[6];

        System.arraycopy(money,0,data,0,3);
        System.arraycopy(LaoNain,0,data,3,3);
        XBLog.d("setMoney(CardPay.java:98)"+Utils.printHexBinary(data));
          return data;
    }

    //单位分 扣实际钱
    byte[] setAMoney(int Money){
       String code= FetchAppConfig.coefficient();
        byte[] money=Utils.int2Bytes(Money,3);
        byte[] LaoNain={0x00,0x00,0x00};
        byte[] data=new byte[6];
        System.arraycopy(money,0,data,0,3);
        System.arraycopy(LaoNain,0,data,3,3);
        XBLog.d("setMoney(CardPay.java:98)"+Utils.printHexBinary(data));
        return data;
    }


    //单位分 不扣钱
    byte[] SetRecode(int Money){
        byte[] money=Utils.int2Bytes(Money,3);
        byte[] LaoNain={0x00,0x00,0x00};
        byte[] data=new byte[6];

        System.arraycopy(LaoNain,0,data,0,3);
        System.arraycopy(money,0,data,3,3);
        XBLog.d("setMoney(CardPay.java:98)"+Utils.printHexBinary(data));

        return data;
    }


    public void SetOnLisetener(OnPushTask onPushTask){
        this.onPushTask=onPushTask;
    }
}
