package com.szxb.buspay.entity;

import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.util.CardType;

/**
 * Created by Administrator on 2017/9/23.
 */

//        0-2	消费金额，3字节HEX，例如1元为0x00 0x00 0x64(前后门多票制上车机的时候，为当前站到终点票价；下车机的时候，为上车站到当前站的票价)
//        3-5	普通卡金额，3字节HEX(前后门多票制上车机的时候，为当前站到终点的普通卡票价; 下车机的时候，为上车站到当前站的普通卡票价)
//        6 	是黑名单卡，是否执行锁卡1字节HEX，0x01表示是执行锁卡，其他表示不是黑名单卡，不锁卡。（预留）
//        7 	是否是白名单卡1字节HEX，0x01表示是白名单卡，其他表示不是。（预留）
//        8-10	车号3字节BCD，例如123456车号位0x12 0x34 0x56
//        11-12	线路号2字节HEX
//        13	司机上下班状态1字节hex，1表示有司机上班，0表示没有司机签到。
//        14-17	当班司机号4字节BCD
//        18	行驶方向1字节HEX，0x00表示上行，0x01表示下行
//        19	当前站点号1字节HEX,从0x00开始，0x00表示第一站
//        20	公司号1字节HEX
public class SendPayData{

    private String MoneyHex;
    private String RecodeMoenyHex;
    private String IsBlack;
    private String IsWrite;
    private String BusNo;
    private String LineNo;
    private String DriverStatus;
    private String DriverNo;
    private String exposure;
    private String station;
    private String CompanyNo;


    public static SendPayData GetData(String moneyhex,String recodehex,String isblack,String code){
        SendPayData sendPayData=new SendPayData();
        sendPayData.setMoneyHex(moneyhex).setRecodeMoenyHex(recodehex).
                setIsBlack(isblack).setIsWrite().setBusNo().setLineNo().
                setDriverStatus(code).setDriverNo().setExposure().setStation().
                setCompanyNo();
        return sendPayData;
    }

    @Override
    public String toString() {
        return MoneyHex+RecodeMoenyHex+IsBlack+IsWrite+BusNo+LineNo+DriverStatus+DriverNo+exposure+station+ CompanyNo;
    }

    public SendPayData setMoneyHex(String moneyHex) {
        MoneyHex = moneyHex;
        return this;
    }

    public SendPayData setRecodeMoenyHex(String recodeMoenyHex) {
        RecodeMoenyHex = recodeMoenyHex;
        return this;
    }

    public SendPayData setIsBlack(String isBlack) {
        IsBlack = isBlack;
        return this;
    }

    public SendPayData setIsWrite() {
        IsWrite = "01";
        return this;
    }

    public SendPayData setBusNo() {
        BusNo = FetchAppConfig.busNo();
        return this;
    }

    public SendPayData setLineNo() {
        LineNo = FetchAppConfig.LineName();
        return this;
    }

    public SendPayData setDriverStatus(String code) {
        DriverStatus = code;
        return this;
    }

    public SendPayData setDriverNo() {
        DriverNo = FetchAppConfig.saveDriver();
        return this;
    }

    public SendPayData setExposure() {
        this.exposure = "00";
        return this;
    }

    public SendPayData setStation() {
        this.station = "00";
        return this;
    }

    public SendPayData setCompanyNo() {
        CompanyNo = FetchAppConfig.LineName().substring(0,2);
        return this;
    }
}
