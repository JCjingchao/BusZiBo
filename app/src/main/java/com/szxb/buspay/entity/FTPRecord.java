package com.szxb.buspay.entity;

import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.xblog.XBLog;

/**
 * Created by Evergarden on 2017/8/24.
 */

//         string.Empty;//卡类型标志    1   HEX
//         string vTradeType = string.Empty;//交易类型     1   HEX 2
//         string vPSAMTradeNo = string.Empty;//PSAM交易序号   3   HEX 5
//         string vCardNo = string.Empty;//卡号            8   BCD 13
//         string vCardAmt = string.Empty;//交易后余额     3   HEX 16
//         string vTradeAmt = string.Empty;//交易金额      3   HEX 19
//         string vTradeDate = string.Empty;//交易日期     4   BCD 23
//         string vTradeTime = string.Empty;//交易时间     3   BCD 26
//         string vCardTradeCount = string.Empty;//卡内交易号  2   HEX 28
//         string vTac = string.Empty;//操作员号(此处TAC码) 4   HEX 32
//         string vAcntId = string.Empty;//公司号          1   HEX 33
//         string vLineNo = string.Empty;//线路号          1   HEX 34
//         string vCarNo = string.Empty;//车号             3   HEX 37
//         string vDriverNo = string.Empty;//驾驶员号      4   HEX 41
//         string vPsam = string.Empty;//PSAM卡号          6   HEX 47
//         string vUpOrDown = string.Empty;//行驶方向      1   HEX 48
//         string vNowStationNo = string.Empty;//当前站点  1   HEX 49
//         string vIsBuTicket = string.Empty;//补上次票标志1   HEX 50    1补票，0不是
//         string vAcntIdNow = string.Empty;//当前线路公司 1   HEX 51
//         string vLineNoNow = string.Empty;//当前车线路号 1   HEX 52
//         string vCarNoNow = string.Empty;//当前车号      3   BCD 55
//         string vDriverNoNow = string.Empty;//当前司机   4   BCD 59
//         string vLast = string.Empty;//预留              5   BCD 64



public class FTPRecord {

    private String fCardType;                   //卡类型                  1HEX
    private String fTradeType;                  //交易类型                2HEX
    private String fPSAMTradeCount;             //PSAM序列号          3HEX
    private String fCardNo;                     //卡号                    8BCD
    private String fCardAmt;                    //交易后余额              3HEX
    private String fFareAmt;                    //交易金额                3HEX
    private String fTradeDate;                  //交易日期                4BCD
    private String fTradeTime;                  //交易时间                3BCD
    private String fCardTradeCount;             //用户卡内交易序号            2HEX
    private String fTACCode;                    //交易TAC码               4HEX
    private String CompanyNo;                   //公司号
    private String fLineNo;                     //线路号                  2HEX
    private String fBusNo;                      //车号                    3HEX
    private String fDriverNo;                   //司机号                  4HEX
    private String PSAMNumber;                  //PSAM卡号
    private String fDirection;                  //行驶方向                1HEX
    private String fCurrenStation;              //当前站点号              1HEX
    private String fTicketMore;//是否补票                1HEX

    private String fCurrentLineNo;              //当前线路号              2HEX
    private String fCurrentBusNo;               //当前汽车号              3BCD
    private String fCurrentDriverNo;            //当前司机号              4BCD
    private String fBackup;                     //备用                    5BCD



    public FTPRecord bulider(CardRecord cardRecord){
        FTPRecord ftpRecord=new FTPRecord();
        ftpRecord.setfCardType(cardRecord.getCardType());
        ftpRecord.setfTradeType(cardRecord.getPayType());
        ftpRecord.setfPSAMTradeCount(cardRecord.getDeviceNo());
        ftpRecord.setfCardNo(cardRecord.getCardNumber());
        ftpRecord.setfCardAmt(cardRecord.getCardMoney());
        ftpRecord.setfFareAmt(cardRecord.getPayMoney());
        ftpRecord.setfTradeDate(cardRecord.getDateTime());
        ftpRecord.setfCardTradeCount(cardRecord.getTrantScationNo());
        ftpRecord.setfTACCode(cardRecord.getTACNo()); //交易认证码，
        ftpRecord.setfLineNo(cardRecord.getLineNo());
        ftpRecord.setfBusNo(cardRecord.getBusNo());
        ftpRecord.setfDriverNo(cardRecord.getDriverNo());
        ftpRecord.setPSAMNumber(cardRecord.getPSAMNo());
        ftpRecord.setfDirection(cardRecord.getExposure());
        ftpRecord.setfCurrenStation(cardRecord.getUp_station());
        ftpRecord.setfTicketMore(cardRecord.getTicket());
        ftpRecord.setfCurrentLineNo(cardRecord.getLineNo());
        ftpRecord.setfCurrentBusNo(cardRecord.getBusNo());
        ftpRecord.setfCurrentDriverNo(cardRecord.getDriverNo());
        ftpRecord.setfBackup("0000000000");
        return ftpRecord;
        }


    @Override
    public String toString() {
        return fCardType+fTradeType+fPSAMTradeCount+fCardNo+
                fCardAmt+fFareAmt+fTradeDate+fCardTradeCount+
                fTACCode+fLineNo+fBusNo+fDriverNo+PSAMNumber+
                fDirection+fCurrenStation+fTicketMore+fCurrentLineNo+
                fCurrentBusNo+fCurrentDriverNo+fBackup;

    }

    public String getfCardType() {
        return fCardType;
    }

    public void setfCardType(String fCardType) {
        this.fCardType = fCardType;
    }

    public String getfTradeType() {
        return fTradeType;
    }

    public void setfTradeType(String fTradeType) {
        this.fTradeType = fTradeType;
    }

    public String getfPSAMTradeCount() {
        return fPSAMTradeCount;
    }

    public void setfPSAMTradeCount(String fPSAMTradeCount) {
        this.fPSAMTradeCount = fPSAMTradeCount;
    }

    public String getfCardNo() {
        return fCardNo;
    }

    public void setfCardNo(String fCardNo) {
        this.fCardNo = fCardNo;
    }

    public String getfCardAmt() {
        return fCardAmt;
    }

    public void setfCardAmt(String fCardAmt) {
        this.fCardAmt = fCardAmt;
    }

    public String getfFareAmt() {
        return fFareAmt;
    }

    public void setfFareAmt(String fFareAmt) {
        this.fFareAmt = fFareAmt;
    }

    public String getfTradeDate() {
        return fTradeDate;
    }

    public void setfTradeDate(String fTradeDate) {
        this.fTradeDate = fTradeDate;
    }

    public String getfTradeTime() {
        return fTradeTime;
    }

    public void setfTradeTime(String fTradeTime) {
        this.fTradeTime = fTradeTime;
    }

    public String getfCardTradeCount() {
        return fCardTradeCount;
    }

    public void setfCardTradeCount(String fCardTradeCount) {
        this.fCardTradeCount = fCardTradeCount;
    }

    public String getfTACCode() {
        return fTACCode;
    }

    public void setfTACCode(String fTACCode) {
        this.fTACCode = fTACCode;
    }

    public String getCompanyNo() {
        return CompanyNo;
    }

    public void setCompanyNo(String companyNo) {
        CompanyNo = companyNo;
    }

    public String getfLineNo() {
        return fLineNo;
    }

    public void setfLineNo(String fLineNo) {
        this.fLineNo = fLineNo;
    }

    public String getfBusNo() {
        return fBusNo;
    }

    public void setfBusNo(String fBusNo) {
        this.fBusNo = fBusNo;
    }

    public String getfDriverNo() {
        return fDriverNo;
    }

    public void setfDriverNo(String fDriverNo) {
        this.fDriverNo = fDriverNo;
    }

    public String getPSAMNumber() {
        return PSAMNumber;
    }

    public void setPSAMNumber(String PSAMNumber) {
        this.PSAMNumber = PSAMNumber;
    }

    public String getfDirection() {
        return fDirection;
    }

    public void setfDirection(String fDirection) {
        this.fDirection = fDirection;
    }

    public String getfCurrenStation() {
        return fCurrenStation;
    }

    public void setfCurrenStation(String fCurrenStation) {
        this.fCurrenStation = fCurrenStation;
    }

    public String getfTicketMore() {
        return fTicketMore;
    }

    public void setfTicketMore(String fTicketMore) {
        this.fTicketMore = fTicketMore;
    }

    public String getfCurrentLineNo() {
        return fCurrentLineNo;
    }

    public void setfCurrentLineNo(String fCurrentLineNo) {
        this.fCurrentLineNo = fCurrentLineNo;
    }

    public String getfCurrentBusNo() {
        return fCurrentBusNo;
    }

    public void setfCurrentBusNo(String fCurrentBusNo) {
        this.fCurrentBusNo = fCurrentBusNo;
    }

    public String getfCurrentDriverNo() {
        return fCurrentDriverNo;
    }

    public void setfCurrentDriverNo(String fCurrentDriverNo) {
        this.fCurrentDriverNo = fCurrentDriverNo;
    }

    public String getfBackup() {
        return fBackup;
    }

    public void setfBackup(String fBackup) {
        this.fBackup = fBackup;
    }
}
