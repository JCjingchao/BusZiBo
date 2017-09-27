package com.szxb.buspay.entity;

import com.szxb.buspay.util.Utils;

/**
 * Created by Administrator on 2017/9/23.
 */

public class MifareGetCard {
    public MifareGetCard(byte[] data){
        byte[] status=new byte[1];
        byte[] driverC=new byte[4];
        byte[] driverB=new byte[4];
        byte[] cardType=new byte[1];
        byte[] mtype=new byte[1];
        System.arraycopy(data,0,status,0,1);
        System.arraycopy(data,1,driverC,0,4);
        System.arraycopy(data,5,driverB,0,4);
        System.arraycopy(data,9,cardType,0,1);
        System.arraycopy(data,10,mtype,0,1);
        Status= Utils.printHexBinary(status);
        CardNumber= Utils.printHexBinary(driverB);
        CardType= Utils.printHexBinary(cardType);
        MType= Utils.printHexBinary(mtype);
    }

    private String Status;//状态位 00成功
    private String CardNumber;//卡号
    private String CardType;//卡类型
    private String MType;//M1 CPU卡

    public String getStatus() {
        return Status;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public String getCardType() {
        return CardType;
    }

    public String getMType() {
        return MType;
    }
}
