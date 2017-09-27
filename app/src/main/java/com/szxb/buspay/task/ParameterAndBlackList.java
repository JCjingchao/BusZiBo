package com.szxb.buspay.task;

import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.ftputil.FTP;
import com.szxb.xblog.XBLog;

import java.io.File;

/**
 * Created by Violet on 2017/8/24.
 */

public class ParameterAndBlackList extends Thread {

    private OnPushTask onPushTask;
    private static ParameterAndBlackList parameterAndBlackList;
    private int type;

    public static ParameterAndBlackList Intance() {

        if (parameterAndBlackList == null)
            parameterAndBlackList = new ParameterAndBlackList();
        return parameterAndBlackList;
    }


    public void SetTpye(int Type){
        this.type=Type;

    }


    public void SetListener(OnPushTask onPushTask){
        this.onPushTask=onPushTask;
    }



    void LoadingBlack(){
        FTP.ftpDown(FetchAppConfig.FTPBlackList(), FetchAppConfig.BlackListName(),
                new FTP.DownLoadProgressListener() {
                    @Override
                    public void onDownLoadProgress(String currentStep, long downProcess, File file) {
                        if (currentStep.equals(FTP.FTP_DOWN_SUCCESS)){
                            XBLog.d("onDownLoadProgress(ParameterAndBlackList.java:50)"+FTP.FTP_DOWN_SUCCESS);
                            onPushTask.task(Constant.blacklistdownSuccess,"");
                        }

                        if (currentStep.equals(FTP.FTP_DOWN_FAIL)){
                            onPushTask.task(Constant.blacklistdownFail,"");
                        }

                    }
                });


    }





    void LoadingParameter(){
        FTP.ftpDown(FetchAppConfig.FTPParameter(), FetchAppConfig.ParameterName(),
                new FTP.DownLoadProgressListener() {
                    @Override
                    public void onDownLoadProgress(String currentStep, long downProcess, File file) {
                        if (currentStep.equals(FTP.FTP_DOWN_SUCCESS)) {
                            XBLog.d("onDownLoadProgress(ParameterAndBlackList.java:74)"+FTP.FTP_DOWN_SUCCESS);
                            onPushTask.task(Constant.ParameterdownSuccess,"");
                        }

                        if (currentStep.equals(FTP.FTP_DOWN_FAIL)){
                            onPushTask.task(Constant.ParameterdownFail,"");
                        }
                    }
                });
    }


    @Override
    public void run() {

        switch (type){
            case Constant.BlackList:
                LoadingBlack();
                break;

            case Constant.Parameter:
                LoadingParameter();
                break;

            case Constant.ALLMessage:
                LoadingBlack();
                LoadingParameter();
                break;
        }

        super.run();

    }

}
