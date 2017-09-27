package com.szxb.buspay.module.Parameter;

import android.text.style.UpdateAppearance;

import com.szxb.buspay.base.BasePresenterCompl;
import com.szxb.buspay.entity.ParameterEntity;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.KeyListenerTask;

/**
 * 作者：Evergarden on 2017-07-25 10:33
 * QQ：1941042402
 */

public class ParameterPresenterComl extends BasePresenterCompl implements ParameterModel {
    @Override
    public void OpenBlue() {

    }

    KeyListenerTask keyListenerTask;
    @Override
    public void UpAndDwonKeyEvent(OnPushTask onPushTask) {
        keyListenerTask=new KeyListenerTask();
        keyListenerTask.start();
        keyListenerTask.SetKeyLestener(onPushTask);
    }

    @Override
    public void CloseEvent() {
        if (keyListenerTask!=null)
            keyListenerTask.isExit();
    }

    @Override
    public ParameterEntity getParameter() {
        return null;
    }

}
