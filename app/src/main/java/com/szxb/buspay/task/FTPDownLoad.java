package com.szxb.buspay.task;

import android.util.Log;

import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.ftputil.FTP;
import com.szxb.xblog.XBLog;

import java.io.File;
import java.util.List;

/**
 * Created by Violet on 2017/8/24.
 */

public class FTPDownLoad extends Thread {

    private OnPushTask onPushTask;
    private static FTPDownLoad ftpDownLoad;


    private String FTPDownPath;
    private String FTPDowmName;
    private int Success,Fail;
    public static FTPDownLoad Intance() {
        ftpDownLoad = new FTPDownLoad();
        return ftpDownLoad;
    }

    public FTPDownLoad SetListener(OnPushTask onPushTask){
        this.onPushTask=onPushTask;
        return this;
    }

    public FTPDownLoad SetFTPDwonPath(String FTPDownPath){
        this.FTPDownPath=FTPDownPath;
        return this;
    }

    public FTPDownLoad SetFTPDownName(String FTPDowmName){
        this.FTPDowmName=FTPDowmName;
        return this;
    }

    public FTPDownLoad SetFTPDownCall(int Success,int Fail){
        this.Success=Success;
        this.Fail=Fail;
        return this;
    }



    public FTPDownLoad OnStart(){
        ftpDownLoad.start();
        return this;
    }

    void Loading(){
        FTP.ftpDown(FTPDownPath, FTPDowmName,
                new FTP.DownLoadProgressListener() {
                    @Override
                    public void onDownLoadProgress(String currentStep, long downProcess, File file) {
                        if (currentStep.equals(FTP.FTP_DOWN_SUCCESS)){
                            Log.d("FTP","Success");
                            onPushTask.task(Success,FTPDowmName);

                        }

                        if (currentStep.equals(FTP.FTP_DOWN_FAIL)){
                            Log.d("FTP","Fail");
                            onPushTask.task(Fail,FTPDowmName);

                        }

                    }
                });


    }




    @Override
    public void run() {
        Loading();
        super.run();
    }

}
