package com.szxb.buspay.task.post;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.szxb.buspay.base.BaseRequestPresenter;

import java.lang.ref.WeakReference;

/**
 * 作者: Tangren on 2017/9/1
 * 包名：szxb.com.commonbus.task
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class PostBIllPresenter extends BaseRequestPresenter {

    private WeakReference<TimePostBillTask> weakReference;

    public PostBIllPresenter(TimePostBillTask task) {
        weakReference = new WeakReference<TimePostBillTask>(task);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        TimePostBillTask task = weakReference.get();
        if (task != null) {
            String retcode = result.getString("retcode");
            if (TextUtils.equals(retcode, "0")) {
                task.onSuccess(what, "流水上传成功");
            } else task.onFail(what, "流水上传失败");
        }
    }

    @Override
    protected void onFail(int what, String failStr) {
        TimePostBillTask task = weakReference.get();
        if (task != null) {
            task.onFail(what, "流水上传失败");
        }
    }
}
