package com.szxb.buspay.module.QueryRecord;

import com.szxb.buspay.base.BasePresenter;
import com.szxb.buspay.interfaces.OnPushTask;

/**
 * 作者：Evergarden on 2017-07-25 10:34
 * QQ：1941042402
 */

public class RecordPresenter extends BasePresenter<RecordView> {
    private RecordView recordView;
    private RecordModel model=new RecordPresenterComl();
    public RecordPresenter(RecordView recordView){
        this.recordView=recordView;
    }


}
