package com.szxb.buspay.module.Parameter;

import com.szxb.buspay.base.BasePresenter;
import com.szxb.buspay.interfaces.OnPushTask;

/**
 * 作者：Evergarden on 2017-07-25 10:34
 * QQ：1941042402
 */

public class ParameterPresenter extends BasePresenter<ParameterView> {
    private ParameterView parameterView;
    private ParameterModel model=new ParameterPresenterComl();
    public ParameterPresenter(ParameterView parameterView){
        this.parameterView=parameterView;
    }

    void ListEvent(OnPushTask onPushTask){
     model.UpAndDwonKeyEvent(onPushTask);
    }
    void CloseEvent(){
        model.CloseEvent();
    }
}
