package com.szxb.buspay.module.Parameter;

import android.view.KeyEvent;

import com.szxb.buspay.base.BaseModel;
import com.szxb.buspay.entity.ParameterEntity;
import com.szxb.buspay.interfaces.OnPushTask;

/**
 * 作者：Evergarden on 2017-07-25 10:33
 * QQ：1941042402
 */

public interface ParameterModel extends BaseModel {

    ParameterEntity getParameter();//获取参数
    void OpenBlue();//打开蓝牙，开启连接服务

    void UpAndDwonKeyEvent(OnPushTask onPushTask);
    void CloseEvent();
}
