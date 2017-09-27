package com.szxb.buspay.task;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.db.dao.CardRecordDao;
import com.szxb.buspay.db.manager.DBCore;
import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.entity.CardRecord;
import com.szxb.buspay.entity.FTPRecord;
import com.szxb.buspay.util.Utils;
import com.szxb.buspay.util.ftputil.FTP;
import com.szxb.buspay.util.tip.BusToast;
import com.szxb.xblog.XBLog;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.szxb.buspay.util.tip.MainLooper.runOnUiThread;

/**
 * Created by Administrator on 2017/8/26.
 */

public class UpLoadRecord extends Thread{

    public UpLoadRecord upLoadRecord;
    public FTP ftp;
    public UpLoadRecord Intance(){
        if (upLoadRecord==null){
             upLoadRecord=new UpLoadRecord();
        }

        return upLoadRecord;
    }

    @Override
    public void run() {
       // XBLog.d("1");
        CardRecordDao dao= DBCore.getDaoSession().getCardRecordDao();
        List<CardRecord> list= dao.queryBuilder().where(CardRecordDao.Properties.UpLoad.eq("0")).list();
        if (list.size()>0) {
            final List<String> list1 = new ArrayList<String>();
            int i = 0;
            XBLog.d(list.size());
            for (CardRecord cardRecord : list) {
                FTPRecord ftpRecord = new FTPRecord();
                ftpRecord = ftpRecord.bulider(list.get(i));
                list1.add(ftpRecord.toString());
                XBLog.d(list.get(i).toString());
                XBLog.d(list1.get(i).toString());
                i++;
            }
            list.clear();
            String name = "record" + Utils.getStringDate()+".txt";
            String code = "";
            int K = 0;
            for (String s : list1) {
                code += list1.get(K) + "\r\n";
                K++;
            }
            Utils.byte2File(code.getBytes(), FetchAppConfig.FTPLocalPath(), name);
            LinkedList<File> linkedList = new LinkedList<>();
            linkedList.add(new File(FetchAppConfig.FTPLocalPath() + name));
            try {
                ftp = new FTP();
                ftp.uploadMultiFile(linkedList, "", new FTP.UploadProgressListener() {
                    @Override
                    public void onUploadProgress(String currentStep, long uploadSize, File file) {
                        if (currentStep.equals(FTP.FTP_UPLOAD_SUCCESS)) {
                            XBLog.d(currentStep);
                            SetUpLoad();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    BusToast.showToast(BusApp.getInstance(),"上传成功",true);
                                }
                            });


                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   // BusToast.showToast(BusApp.getInstance(),"上传失败",false);
                                }
                            });
                        }
                    }
                });

            } catch (Exception e) {
                XBLog.d(e.getMessage());
            }
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BusToast.showToast(BusApp.getInstance(),"无需要上传的记录",true);
                }
            });
        }
        super.run();
    }


    public void SetUpLoad(){
        CardRecordDao dao= DBCore.getDaoSession().getCardRecordDao();
        List<CardRecord> list= dao.queryBuilder().where(CardRecordDao.Properties.UpLoad.eq("0")).list();
        int i=0;
        for (CardRecord cardRecord:list){
             cardRecord.setUpLoad("1");
             dao.update(cardRecord);
             i++;
        }
        list.clear();
    }
}
