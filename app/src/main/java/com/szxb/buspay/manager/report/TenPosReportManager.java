package com.szxb.buspay.manager.report;

import android.text.TextUtils;
import android.util.Log;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.db.manager.DBManager;
import com.szxb.buspay.entity.PosRecord;
import com.szxb.buspay.entity.QRCode;
import com.szxb.buspay.entity.QRScanMessage;
import com.szxb.buspay.util.rx.RxBus;
import com.tencent.wlxsdk.WlxSdk;


/**
 * 作者: Tangren on 2017-09-08
 * 包名：szxb.com.commonbus.util.report
 * 邮箱：996489865@qq.com
 * TODO:腾讯
 */

public class TenPosReportManager {

    private static TenPosReportManager instance = null;
    private WlxSdk wxSdk;

    private TenPosReportManager() {
        wxSdk = new WlxSdk();
    }

    public static TenPosReportManager getInstance() {
        synchronized (TenPosReportManager.class) {
            if (instance == null) {
                instance = new TenPosReportManager();
            }
        }
        return instance;
    }

    public void posScan(String qrcode) {
        if (wxSdk == null) wxSdk = new WlxSdk();
        int init = wxSdk.init(qrcode);
        int key_id = wxSdk.get_key_id();
        String open_id = wxSdk.get_open_id();
        String mac_root_id = wxSdk.get_mac_root_id();
        Log.d("TenPosReportManager",
                "posScan(TenPosReportManager.java:37)init=" + init + "key_id=" + key_id + "open_id=" + open_id + "mac_root_id=" + mac_root_id);
        int verify = 0;
        if (!TextUtils.isEmpty(open_id)) {
            if (DBManager.filterBlackName(open_id)) {
                //是黑名单里面的成员
                RxBus.getInstance().send(new QRScanMessage(null, QRCode.QR_ERROR));
            } else {
                if (init == 0 && key_id > 0) {
                    //String open_id, String pub_key, int payfee, byte scene, byte scantype, String pos_id, String pos_trx_id, String aes_mac_root
                    verify = wxSdk.verify(open_id
                            , BusApp.getPosManager().getPublicKey(String.valueOf(key_id))
                            , 1//金额,上线修改为BusApp.getPosManager().getMarkedPrice()
                            , (byte) 1
                            , (byte) 1
                            , BusApp.getPosManager().getDriverNo()
                            , BusApp.getPosManager().getmchTrxId()
                            , BusApp.getPosManager().getMac(mac_root_id));

                    String record = wxSdk.get_record();
                    PosRecord posRecord = new PosRecord();
                    posRecord.setOpen_id(open_id);
                    posRecord.setMch_trx_id(BusApp.getPosManager().getmchTrxId());
                    posRecord.setOrder_time(BusApp.getPosManager().getOrderTime());
                    posRecord.setTotal_fee(1);//金额，上线修改为posRecord.setTotal_fee(BusApp.getPosManager().getMarkedPrice());
                    posRecord.setPay_fee(1);//实际扣款金额，上线修改为posRecord.setTotal_fee(BusApp.getPosManager().getPayMarkedPrice());
                    posRecord.setCity_code(BusApp.getPosManager().geCityCode());
                    posRecord.setOrder_desc(BusApp.getPosManager().getOrderDesc());
                    posRecord.setIn_station_id(BusApp.getPosManager().getInStationId());
                    posRecord.setIn_station_name(BusApp.getPosManager().getInStationName());
                    posRecord.setRecord(record);
                    posRecord.setBus_no(BusApp.getPosManager().getBusNo());
                    posRecord.setBus_line_name(BusApp.getPosManager().getLineName());
                    posRecord.setPos_no(BusApp.getPosManager().getDriverNo());

                    RxBus.getInstance().send(new QRScanMessage(posRecord, verify));
                }

            }
        }
    }


}
