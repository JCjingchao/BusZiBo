package com.szxb.buspay.base;

import java.lang.ref.WeakReference;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public abstract class BasePresenter<T> {

    protected WeakReference<T> viewRef;

    //绑定
    public void attachView(T view) {
        viewRef = new WeakReference<T>(view);
    }

    //解除视图绑定
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
