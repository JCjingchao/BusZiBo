package com.szxb.buspay.manager;

import android.util.Base64;
import android.util.Log;

import com.example.zhoukai.modemtooltest.ModemToolTest;
import com.szxb.buspay.db.manager.DBManager;
import com.szxb.buspay.db.sp.CommonSharedPreferences;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.PosRecord;
import com.szxb.buspay.interfaces.IPosManage;
import com.szxb.buspay.util.Config;
import com.szxb.buspay.util.DateUtil;
import com.szxb.buspay.util.Utils;


/**
 * 作者: Tangren on 2017-09-08
 * 包名：szxb.com.commonbus.manager
 * 邮箱：996489865@qq.com
 * TODO:POS数据初始化
 */

public class PosManager implements IPosManage {
    //路线名
    private String lineName;
    //起始站名
    private String startStationName;
    //终点站名
    private String endStationName;
    //设备号
    private String driverNo;
    //扣款金额
    private int markedPrice;
    //实际扣款金额
    private int paymarkedPrice;
    //城市编码
//    private String cityCode = "371200";//莱芜
    private String cityCode = "370300";//淄博
    //站点ID
    private int inStationId = 13;
    //备注
    private String orderDesc = "淄博公交";
    //key
    private byte[] key;
    //车牌号
    private String bus_no;
    //腾讯商户号
    private String app_id = "10000009";

    public PosManager() {

    }

    @Override
    public void loadFromPrefs() {
        try {
            lineName = FetchAppConfig.lineName();
            startStationName = FetchAppConfig.startStationName();
            endStationName = FetchAppConfig.endStationName();
            driverNo = ModemToolTest.getItem(7);

            markedPrice = Integer.valueOf(FetchAppConfig.fixed_price());
            int coef = Integer.parseInt(FetchAppConfig.coefficient().substring(24, 27));
            paymarkedPrice = markedPrice * coef / 100;

            orderDesc = FetchAppConfig.orderDesc();
            key = Base64.decode(Config.private_key, Base64.NO_WRAP);
            bus_no = FetchAppConfig.busNo();

            Log.d("PosManager",
                    "loadFromPrefs(PosManager.java:73)二维码应扣款=" + markedPrice + "");

            Log.d("PosManager",
                    "loadFromPrefs(PosManager.java:77)二维码实际扣款" + paymarkedPrice + "");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("PosManager",
                "loadFromPrefs(PosManager.java:80)"+e.toString());
        }
    }

    @Override
    public String getLineName() {
        return lineName;
    }

    @Override
    public void setLineName(String var1) {
        this.lineName = var1;
        CommonSharedPreferences.put("lineName", var1);
    }

    @Override
    public String getLineStart() {
        return startStationName;
    }

    @Override
    public void setLineStart(String var1) {
        this.startStationName = var1;
        CommonSharedPreferences.put("startStationName", var1);
    }

    @Override
    public String getLineEnd() {
        return endStationName;
    }

    @Override
    public void setLineEnd(String var1) {
        this.endStationName = var1;
        CommonSharedPreferences.put("endStationName", var1);
    }

    @Override
    public String getDriverNo() {
        return driverNo;
    }

    @Override
    public void setDriverNo(String var1) {
        this.driverNo = var1;
        CommonSharedPreferences.put("snNo", var1);
    }

    @Override
    public int getMarkedPrice() {
        return markedPrice;
    }

    @Override
    public void setMarkedPrice(int var1) {
        this.markedPrice = var1;
        CommonSharedPreferences.put("fixed_price", var1);
    }

    @Override
    public int getPayMarkPrice() {
        return paymarkedPrice;
    }

    @Override
    public void setPayMarPrice(int var1) {
        this.paymarkedPrice = var1;
        CommonSharedPreferences.put("payticketPrice", var1);
    }

    @Override
    public String getMac(String keyId) {
        return DBManager.getMac(keyId);
    }

    @Override
    public String getPublicKey(String keyId) {
        return DBManager.getPublicKey(keyId);
    }

    @Override
    public long getOrderTime() {
        return DateUtil.currentLong();
    }

    @Override
    public String getmchTrxId() {
        return Utils.Random(10);
    }

    @Override
    public String geCityCode() {
        return cityCode;
    }

    @Override
    public int getExpType() {
        return 0;
    }

    @Override
    public int getChargeType() {
        return 0;
    }

    @Override
    public int getInStationId() {
        return inStationId;
    }

    @Override
    public String getInStationName() {
        return startStationName;
    }

    @Override
    public String getOrderDesc() {
        return orderDesc;
    }

    @Override
    public void setOrderDesc(String var1) {
        this.orderDesc = var1;
        CommonSharedPreferences.put("orderDesc", var1);
    }

    @Override
    public byte[] getKey() {
        return key;
    }

    @Override
    public void setKey(String privateKey) {
        byte[] key = Base64.decode(privateKey, Base64.NO_WRAP);
        this.key = key;
    }

    @Override
    public String getBusNo() {
        return bus_no;
    }

    @Override
    public void setBusNo(String bus_no) {
        this.bus_no = bus_no;
        CommonSharedPreferences.put("busNo", bus_no);
    }

    @Override
    public String getAppId() {
        return app_id;
    }

    @Override
    public void setAppId(String app_id) {
        this.app_id = app_id;
    }

    @Override
    public void dowithPosRecordReply(PosRecord var1, int var2) {

    }
}
